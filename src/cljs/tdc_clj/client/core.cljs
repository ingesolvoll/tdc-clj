(ns tdc-clj.client.core
  (:require [reagent.core :as reagent]))

(enable-console-print!)

(defonce socket (atom nil))
(defonce messages (reagent/atom []))

(defn layout []
  [:ul
   (for [message @messages]
     ^{:key message}
     [:li message])])

(defn make-websocket [url handler]
  (println "Connectin' " url)
  (when-let [chan (js/WebSocket. url)]
    (set! (.-onmessage chan) handler)
    (reset! socket chan)
    (println "Good to go!")))

(defn start! []
  (make-websocket "ws://localhost:8085/socket" #(swap! messages conj (.-data %))))

(reagent/render [layout]
                (. js/document (getElementById "app")) start!)