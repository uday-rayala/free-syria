(ns free-syria.views.welcome
  (:require [free-syria.views.common :as common]
            [clojure.data.json :as json]
            clojure.string)
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        free-syria.thumbnails
        [clojure.java.io :only (file)]))

(def THUMBNAIL-PATH "resources/public/img/thumbnails/")
(def LOCAL-PREFIX "/img/thumbnails")
(def CDN-PREFIX "http://c309575.r75.cf1.rackcdn.com/")


(defn get-thumbnails
  [path]
  (map #(str CDN-PREFIX %)
       (filter #(re-find #".*\.jpg" %)
               (map #(.getName %)
                    (file-seq (file THUMBNAIL-PATH))))))

(def images (get-thumbnails THUMBNAIL-PATH))

(defn images-json
  []
  (map (fn [url]
         {:url url
          :video (clojure.string/replace url #"\.jpg" ".m4v")
          :dimensions [240 180]})
         images))

(defpage "/images.json" []
         (json/json-str {:images (thumbnail-maps)}))

(defpage "/about" []
         (common/layout
           [:div#about
            [:iframe {:width "560" :height "315" :src "http://www.youtube.com/embed/uYCC6YP6ato"
                      :frameborder "0" :allowfullscreen "true"}]]))

(defpage "/contact" []
         (common/layout
           [:p "Contact info here."]))

(defpartial photo-grid [] [:div#photo-grid])

(defpage "/" []
  (html5
    [:head
     [:title "Free Syria"]
     (include-css "/css/reset.css")
     (include-css "/css/style.css")
     (include-js "/js/jquery-1.7.1.min.js")
     ]
    [:body
     [:div#wrapper
      [:div#landing
       [:img {:src "/img/landing.png"}]
       [:h3 "Please email your video declarations to:"]
       [:p "&nbsp; voices@isupportthesyrianrevolution.com"]]]]))

(defpage "/live" []
         (common/layout
           [:div#video-container] [:div#video-screen]
           (photo-grid)))

