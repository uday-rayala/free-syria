(defproject free-syria "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [org.clojure/data.json "0.1.3"]
                           [noir "1.3.0-alpha10"]
                           [org.clojure/tools.logging "0.2.3"]]
            :main free-syria.server
            :ring {:handler project.server/handler}
            )

