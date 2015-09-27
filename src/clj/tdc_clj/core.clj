(ns tdc-clj.core
  (:require [org.httpkit.server :as httpkit]
            [compojure.core :refer [defroutes GET]]
            [clojure.repl :as repl]
            [clojure.tools.namespace.repl :as ns-repl]))

(defonce app (atom nil))

(defroutes application
           (GET "/test" [] "12"))

(defn stop! [{:keys [server socket-ch]}]
  (server)
  (reset! app nil))


(defn start! []
  (reset! app {:server    (httpkit/run-server #'application {:port 8080})
               :input-ch  nil
               :socket-ch nil}))

(defn restart! [app]
  (stop! @app)
  (ns-repl/refresh :after 'tdc-clj.core/start!))