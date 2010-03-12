(ns jelly.rss
  (:use (clojure.contrib seq))
  (:use [clojure.xml :only (parse)]))

(defn get-feed [url] (parse url))

(def feed-urls ["http://feeds.feedburner.com/SauceLabs?format=xml"
                "http://news.ycombinator.com/rss"])

(defn get-feeds [] (map get-feed feed-urls))

(def feeds (map get-feed feed-urls))

(defn find-tags-helper [node tag]
  "Follow node down, retrieving :content from all matching tags"
  (if (nil? node)
    nil
    (if (or (nil? (:tag node)) (not= (str (:tag node)) (str tag)))
      (map (fn [x] (find-tags-helper x tag)) (:content node))
      (:content node))))

(defn find-tags [node tags]
  "Follow node down, retrieving :content from all matching tags"
  (flatten (find-tags-helper node tags)))

(defn find-titles [node]
  (find-tags node :title))

(defn retrieve-all-feed-titles []
  (flatten (map find-titles (map get-feed feed-urls))))
  
