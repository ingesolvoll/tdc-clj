(ns tdc-clj.core
  (:require [org.httpkit.server :as httpkit :refer [send! with-channel on-close on-receive]]
            [compojure.core :refer [defroutes GET]]
            [clojure.repl :as repl]
            [clojure.tools.namespace.repl :as ns-repl]
            [tdc-clj.sockets :as sockets]))

(defonce app (atom nil))


(defroutes application
           (GET "/socket" request (sockets/ws-handler request)))


(defn stop! []
  (let [{:keys [server socket-ch]} @app]
    (server)
    (reset! app nil)))

(defn start! []
  (reset! app {:server    (httpkit/run-server #'application {:port 8080})
               :input-ch  nil
               :socket-ch nil}))

(defn restart! []
  (stop!)
  (ns-repl/refresh :after 'tdc-clj.core/start!))