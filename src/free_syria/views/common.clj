(ns free-syria.views.common
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpartial header-menu []
  [:div#header-menu
   [:ul
    [:li [:a {:href "/"}    "home"]]
    [:li [:a {:href "/about"}   "about"]]
    [:li [:a {:href "/contact"} "contact"]]]])

(defpartial header []
  [:div#header
    [:img {:src "/img/logo.png"}]
    (header-menu)])

(defpartial layout [& content]
  (html5
    [:head
     [:title "Free Syria"]
     (include-css "/css/reset.css")
     (include-css "/css/style.css")
     (include-js "/js/jquery-1.7.1.min.js")
     (include-js "/js/flowplayer-3.2.6.min.js")
     (include-js "/js/video-grid.js")
     ]
    [:body
     [:div#wrapper
     (header)
      [:div#stage content]]]))
