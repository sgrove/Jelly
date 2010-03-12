(defproject jelly "1.0.0-SNAPSHOT"
  :description "FIXME: write"
  :dependencies [[org.clojure/clojure "1.1.0"]
                 [org.clojure/clojure-contrib "1.1.0"]
                 [net.philh.clogge]]
  :main jelly.core)

; HTF do I handle loading files? Jesus christ...
(let [source-files ["/Users/sgrove/code/clojure/jelly/src/jelly/core.clj"
                   "/Users/sgrove/code/clojure/jelly/src/jelly/rss.clj"
                   "/Users/sgrove/code/clojure/jelly/src/jelly/graphics.clj"]]
  (map load-file source-files))
