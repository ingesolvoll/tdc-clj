(defproject tdc-clj "1.0.0"
            :min-lein-version "2.0.0"
            :dependencies [[org.clojure/clojure "1.7.0"]
                           [compojure "1.4.0"]
                           [javax.servlet/servlet-api "2.5"]
                           [ring/ring-core "1.4.0"]
                           [http-kit "2.1.18"]
                           [org.clojure/tools.namespace "0.2.11"]
                           [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                           ]
            :resource-paths ["resources"]
            :source-paths ["src/clj"]
            :test-paths ["test/clj"]
            :repl-options {:init-ns tdc-clj.core})