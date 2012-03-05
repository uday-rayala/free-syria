(ns free-syria.views.common
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpartial header-menu []
  [:div#header-menu
   [:ul
    [:li "home"]
    [:li "about"]
    [:li "contact"]]])

(defpartial header []
  [:div#header
    [:img {:src "/img/logo.png"}]
    (header-menu)])


(defpartial layout [& content]
  (html5
    [:head
     [:title "Free Syria"]
     (include-css "/css/reset.css")
     (include-css "/css/style.css")]
    [:body
     [:div#wrapper
     (header)
      content]
     ]))
