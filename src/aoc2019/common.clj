(ns aoc2019.common
  (:require [missing.core :as miss]
            [clojure.string :as strings]
            [clojure.java.io :as io])
  (:import (java.time Duration)))

(defonce problems (atom #{}))

(defmacro defproblem [symbol & body]
  (let [root (.getAbsolutePath (io/file ""))
        link (strings/replace *file* root ".")
        line (str link "#L" (:line (meta &form)))]
    `(let [v# (defn ~symbol []
                (let [[time# result#]
                      (miss/timing ~@body)
                      explain#
                      (miss/duration-explain
                        (Duration/ofNanos (* 1000 time#)))]
                  (println (format "### [%s](%s)" (name '~symbol) ~line))
                  (println "```clojure")
                  (println "{:t" (str \" explain# \"))
                  (println " :a" (str (pr-str result#) "}"))
                  (println "```")
                  (println "---")
                  (println)))]
       (swap! problems conj v#)
       v#)))

(defn run []
  (doseq [prob (sort-by (comp name #(symbol %)) @problems)
          :when (strings/starts-with?
                  (namespace (symbol prob))
                  (name (.getName *ns*)))]
    ((var-get prob))))

(defn run-all []
  (doseq [prob (sort-by (comp name #(symbol %)) @problems)]
    ((var-get prob))))

(defn readme []
  (spit "readme.md" (with-out-str (run-all))))