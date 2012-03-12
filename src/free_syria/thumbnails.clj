(ns free-syria.files
  (:use [clojure.tools.logging :only (info error warn)])
  (:use [clojure.java.shell :only [sh]])
  (:import [com.rackspacecloud.client.cloudfiles FilesClient])
  (:require [clojure.string :as str]) )

(def videos-container-name "syria-new-m4vs")



(defn- has-any-suffix? [str suffixes]
  (some #(.endsWith str %) suffixes))



(defn- stem [name]
  (first (str/split name #"\.")))



(defn unprocessed-videos [video-and-image-objects]
  "Return collection of video objects that don't have a corresponding jpg"
  (let [video-objects (filter #(.endsWith (:name %) ".m4v") video-and-image-objects)
        image-stems (map #(stem (:name %))
                          (filter
                            (fn [o]
                              (and
                                (.endsWith (:name o) ".jpg")
                                (> (int (:size o)) 0)))
                            video-and-image-objects))]
    (remove (fn [o] (some (set image-stems) (list (stem (:name o))))) video-objects)))



(defn mime-type-for-suffix [suffix]
  (com.rackspacecloud.client.cloudfiles.FilesConstants/getMimetype suffix))



; Don't include getCDNURL here because it is very slow ... instead keep the object ref for when we need it
(defn- prop-map [o]
  (let [prop-fns {:name #(.getName %)
                   :last-modified #(.getLastModified %)
                   :mime-type #(.getMimeType %)
                   :size #(.getSize %)
                   :directory #(.isDirectory %)
                   :obj-ref identity}]
    (apply merge (map (fn [k] { k ((prop-fns k) o) }) (keys prop-fns)))))



(defn files-client []
  (let [client (com.rackspacecloud.client.cloudfiles.FilesClient.)]
    (if (.login client) client (error "API login failed"))))



(defn container-objects [client container-name]
  "Return collection of maps containing selected object attributes"
  (let [objects (.. client (listObjects container-name))]
    (map prop-map objects)))



(defn upload-file [client container-name file-path mime-type]
  (if file-path (.. client (storeObject container-name (java.io.File. file-path) mime-type))))



(defn generate-thumbnail [video-path video-name]
  (let [image-path (str/replace video-name ".m4v" ".jpg")
        ffmpeg ["ffmpeg" "-i" video-path "-ss" "0" "-vframes" "1" "-vcodec" "mjpeg" "-f" "image2" image-path]
        command-result (apply sh ffmpeg)]
    (if (> (command-result :exit) 0)
      (do (error "ffmpeg returned exit status" (command-result :exit) ", output: " (command-result :err))
          nil)
      image-path)))



(defn update-video-thumbnails [container-name]
  (let [client (files-client)
        objects (container-objects client container-name)
        video-and-image-objects (filter #(has-any-suffix? (:name %) [".jpg" ".m4v"]) objects)
        unprocessed (unprocessed-videos video-and-image-objects)
        generate-thumbnail-from-object (fn [o] (generate-thumbnail (.getCDNURL (:obj-ref o)) (:name o)))
        upload (fn [path] (upload-file client container-name path (mime-type-for-suffix "jpg")))
        _ (info "Found " (count unprocessed) " unprocessed videos ")]
    (map (comp upload generate-thumbnail-from-object) unprocessed)))



(comment

; REPL stuff


(def names [{:name "foo.m4v" :size 10} {:name "bar.m4v" :size 10} {:name "foo.jpg" :size 10}])
(unprocessed-videos names)

(def c (files-client))
(def o (.. c (listObjects videos-container-name)))
(def co (container-objects c videos-container-name))
(map (juxt :name :size) o)

(update-video-thumbnails videos-container-name)

)