(ns free-syria.server
  (:require [noir.server :as server])
  (:use [free-syria thumbnails])
  (:import [java.util.concurrent Executors TimeUnit]))

(server/load-views "src/free_syria/views/")

(defn -main [& m]
  (let [executor (Executors/newSingleThreadScheduledExecutor)
        mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (.scheduleAtFixedRate executor update-files 30 600 TimeUnit/SECONDS)
    (server/start port {:mode mode
                        :ns 'free-syria})))

