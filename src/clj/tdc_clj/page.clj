(ns tdc-clj.page
  (:require [hiccup.page :as page]))

(defn client []
  (page/html5
    [:head [:title "Football stream client receiver"]]
    [:body [:div#app "Loading..."]
     (page/include-js "/js/compiled/app.js")]))