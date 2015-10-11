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

(defn run-sequence []
  (go-loop [[current & rest] data/sequence]
    (if (integer? current)
      (<! (timeout current))
      (sockets/notify-clients (json/write-str current)))
    (if (seq rest)
      (recur rest))))

(defn start! []
  (reset! app (run-server #'application {:port 8080}))
  (start-random-forever))

(defn reload! []
  (ns-repl/refresh))
