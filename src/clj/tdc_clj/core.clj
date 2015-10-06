(ns tdc-clj.core
  (:require [org.httpkit.server :refer [send! with-channel on-close on-receive run-server]]
            [compojure.core :refer [defroutes GET]]
            [clojure.repl :as repl]
            [clojure.tools.namespace.repl :as ns-repl]
            [tdc-clj.sockets :as sockets]
            [tdc-clj.page :as page]
            [tdc-clj.data :as data]
            [clojure.data.json :as json]
            [clojure.core.async :refer [close! go-loop >! <! chan timeout]]))

(defonce app (atom nil))

(defroutes application
           (GET "/socket" request (sockets/ws-handler request))
           (GET "/client" [] (page/client)))

(defn stop! []
  (println "Stopping " @app)
  (if @app (@app))
  (reset! app nil))

(defn start! []
  (reset! app (run-server #'application {:port 8080}))
    (go-loop [val 1000]
      (sockets/notify-clients (json/write-str (data/get-random-data)))
      (when @app
        (<! (timeout 1000))
        (recur (inc val)))))

(defn reload! []
  (ns-repl/refresh :after 'tdc-clj.core/start!))