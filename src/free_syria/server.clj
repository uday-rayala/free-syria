(ns free-syria.server
  (:require [noir.server :as server])
  (:import [java.util.concurrent Executors])

(server/load-views "src/free_syria/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'free-syria})))

;(let [executor (Executors/newSingleThreadScheduledExecutor)]   (.scheduleAtFixedRate executor your-func 0 3 TimeUnit/SECONDS))
