(ns tdc-clj.sockets
  (:require [org.httpkit.server :refer [send! with-channel on-close on-receive]]))

(defonce channels (atom #{}))

(defn notify-clients [msg]
  (doseq [channel @channels]
    (send! channel msg)))

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
