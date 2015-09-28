(ns tdc-clj.core
  (:require [org.httpkit.server :as httpkit :refer [send! with-channel on-close on-receive]]
            [compojure.core :refer [defroutes GET]]
            [clojure.repl :as repl]
            [clojure.tools.namespace.repl :as ns-repl]))

(defonce app (atom nil))

(defonce channels (atom #{}))

(defn connect! [channel]
  (println "channel open")
  (swap! channels conj channel))

(defn disconnect! [channel status]
  (println "channel closed:" status)
  (swap! channels #(remove #{channel} %)))

(defn ws-handler [request]
  (with-channel request channel
                (connect! channel)
                (on-close channel (partial disconnect! channel))))

(defroutes application
           (GET "/socket" request (ws-handler request)))

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