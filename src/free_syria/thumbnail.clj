(ns free-syria.files
  (:import [com.rackspacecloud.client.cloudfiles FilesClient]))

(def api-username "")
(def api-key "")
(def videos-container-name "syria-new-m4vs")

(defn files-client [username password]
  (doto (com.rackspacecloud.client.cloudfiles.FilesClient. username password)
    (.login)))

(defn video-urls [client container-name]
  (let [objects (.. client (listObjects container-name))
        videos (filter #(.endsWith (.getName %) ".m4v") objects)]
    (map #(.getCDNURL %) videos)))

(video-urls (files-client api-username api-key) videos-container-name)