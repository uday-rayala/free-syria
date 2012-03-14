(ns free-syria.views.welcome
  (:require [free-syria.views.common :as common])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpartial photo-grid []
            [:div#photo-grid
             [:img.thumbnail.left {:src "/img/thumbnails/Sequence_10.jpg"
                                   :video "http://c307100.r0.cf1.rackcdn.com/Sequence 10.m4v"}]
             [:img.thumbnail.right {:src "/img/thumbnails/Sequence_4.jpg"
                                    :video "http://c307100.r0.cf1.rackcdn.com/Sequence 4.m4v"}]
             [:img.thumbnail.left {:src "/img/thumbnails/Sequence_5.jpg"
                                   :video "http://c307100.r0.cf1.rackcdn.com/Sequence 5.m4v"}]
             [:img.thumbnail.left {:src "/img/thumbnails/Sequence_5.jpg"
                                   :video "http://c307100.r0.cf1.rackcdn.com/Sequence 5.m4v"}]
             [:img.thumbnail.right {:src "/img/thumbnails/Sequence_6.jpg"
                                    :video "http://c307100.r0.cf1.rackcdn.com/Sequence 6(iphone format).m4v"}]
             [:img.thumbnail.left {:src "/img/thumbnails/Sequence_52.jpg"
                                   :video "http://c307100.r0.cf1.rackcdn.com/Sequence 52.m4v"}]])

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
      [:img {:src "/img/landing.png"}]
      [:p "Under Construction"]]]))

(defpage "/live" []
         (common/layout
           [:div#video-screen ]
           (photo-grid)))

(defpage "/about" []
         (common/layout
           [:div#about
            [:iframe {:width "560" :height "315" :src "http://www.youtube.com/embed/uYCC6YP6ato"
                      :frameborder "0" :allowfullscreen "true"}]]))

(defpage "/contact" []
         (common/layout
           [:p "Contact info here."]))

