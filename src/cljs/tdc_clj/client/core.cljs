(ns tdc-clj.client.core
  (:require [reagent.core :as reagent]))

(enable-console-print!)

(defonce socket (atom nil))
(defonce messages (reagent/atom []))

(defn layout []
  [:ul
   (for [message (take-last 20 @messages)]
     ^{:key message}
     [:li message])])

(defn make-websocket [url handler]
  (when-let [chan (js/WebSocket. url)]
    (set! (.-onmessage chan) handler)
    (reset! socket chan)))

(defn start! []
  (make-websocket "ws://localhost:8085/socket" #(swap! messages conj (.-data %))))

(reagent/render [layout]
                (. js/document (getElementById "app")) start!)