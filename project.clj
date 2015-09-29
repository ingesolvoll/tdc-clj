(defproject tdc-clj "1.0.0"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.58"]
                 [compojure "1.4.0"]
                 [ring/ring-core "1.4.0"]
                 [http-kit "2.1.18"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [reagent "0.5.0"]
                 ]
  :resource-paths ["resources"]
  :source-paths ["src/clj"]
  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-figwheel "0.3.1"]]
  :test-paths ["test/clj"]
  :cljsbuild {
              :builds [{:id           "dev"
                        :source-paths ["src/main/cljs"]

                        :figwheel     {}

                        :compiler     {:main                 tdc-clj.client
                                       :asset-path           "js/compiled"
                                       :output-to            "resources/public/js/compiled/app.js"
                                       :output-dir           "resources/public/js/compiled"
                                       :source-map-timestamp true}}]}
  :repl-options {:init-ns tdc-clj.core})