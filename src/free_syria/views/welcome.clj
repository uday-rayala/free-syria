(ns free-syria.views.welcome
  (:require [free-syria.views.common :as common])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpage "/" []
         (common/layout
           [:div#stage
            [:p "I Support The Syrian Revolution"]]))
