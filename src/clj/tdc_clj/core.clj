(ns tdc-clj.core
  (:require [org.httpkit.server :as httpkit :refer [send! with-channel on-close on-receive]]
            [compojure.core :refer [defroutes GET]]
            [clojure.repl :as repl]
            [clojure.tools.namespace.repl :as ns-repl]
            [tdc-clj.sockets :as sockets]
            [clojure.core.async :refer [close! go-loop >! <! chan timeout]]))

(defonce app (atom nil))


(defroutes application
           (GET "/socket" request (sockets/ws-handler request)))


(defn stop! []
  (let [{:keys [server input-ch]} @app]
    (server)
    (close! input-ch)
    (reset! app nil)))

(defn start! []
  (let [input-ch (chan)]
    (go-loop []
      (>! input-ch "Hey hey")
      (<! (timeout 1000))
      (recur))

    (go-loop []
      (sockets/notify-clients (<! input-ch))
      (recur))

    (reset! app {:server    (httpkit/run-server #'application {:port 8080})
                 :input-ch  input-ch
                 :socket-ch nil})))

(defn restart! []
  (stop!)
  (ns-repl/refresh :after 'tdc-clj.core/start!))