(defproject free-syria "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.2.0"]]
            :main free-syria.server
            :ring {:handler project.server/handler}
            )

