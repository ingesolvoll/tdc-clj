(ns tdc-clj.core
  (:require [org.httpkit.server :refer [send! with-channel on-close on-receive run-server]]
            [compojure.core :refer [defroutes GET]]
            [clojure.repl :as repl]
            [clojure.tools.namespace.repl :as ns-repl]
            [tdc-clj.sockets :as sockets]
            [tdc-clj.page :as page]
            [tdc-clj.data :as data]
            [clojure.data.json :as json]
            [clojure.core.async :refer [close! go-loop go >! <! chan timeout]]))

(defonce app (atom nil))

(defonce loop-forever (atom true))

(defroutes application
           (GET "/socket" request (sockets/ws-handler request))
           (GET "/client" [] (page/client)))

(defn stop! []
  (println "Stopping " @app)
  (if @app (@app))
  (reset! app nil))

(defn stop-random-forever []
  (reset! loop-forever false))

(defn start-random-forever []
  (reset! loop-forever true)
  (go
    (while @loop-forever
      (-> (data/get-random-data) json/write-str sockets/notify-clients)
      (<! (timeout 1000)))))

(defn run-steps! [steps limit]
  (let [steps (take limit (partition 2 steps))]
    (go
      (loop [steps (take limit steps)]
        (when-let [[delay data] (first steps)]
          (<! (timeout (* 1000 delay)))
          (sockets/notify-clients (json/write-str data))
          (recur (rest steps)))))))

(run-steps! [1 "hey" 5 "ho"] 2)

(defn start! []
  (reset! app (run-server #'application {:port 8080}))
  (start-random-forever))

(defn reload! []
  (ns-repl/refresh))
