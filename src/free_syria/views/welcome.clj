(ns free-syria.views.welcome
  (:require [free-syria.views.common :as common] [clojure.data.json :as json])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpartial photo-grid [] [:div#photo-grid])

(defpage "/" []
         (common/layout
           [:div#video-screen ]
           (photo-grid)))

(def image1 "/img/thumbnails/Sequence_10.jpg")
(def image2 "/img/thumbnails/Sequence_4.jpg")
(def image3 "/img/thumbnails/Sequence_5.jpg")
(def image4 "/img/thumbnails/Sequence_6.jpg")
(def image5 "/img/thumbnails/Sequence_52.jpg")

(def dim1 [90 90])
(def dim2 [45 90])
(def dim3 [45 45])

(def images [image1 image2 image3 image4 image5])
(def dimesnsions [dim1 dim2 dim3])

(defn random-image [] {:url (rand-nth images) :dimensions (rand-nth dimesnsions)})
(defn random-image-with-dimension [dim] {:url (rand-nth images) :dimensions dim})

(defn images-json [] (concat (take 150 (repeatedly random-image))))

(defpage "/images.json" [] (json/json-str {:images (images-json)}))

(defpage "/about" []
         (common/layout
           [:div#about
            [:iframe {:width "560" :height "315" :src "http://www.youtube.com/embed/uYCC6YP6ato"
                      :frameborder "0" :allowfullscreen "true"}]]))

(defpage "/contact" []
         (common/layout
           [:p "Contact info here."]))

