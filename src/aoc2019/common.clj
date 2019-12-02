(ns aoc2019.common
  (:require [missing.core :as miss]
            [clojure.string :as strings]
            [clojure.java.io :as io])
  (:import (java.time Duration)))

(defonce problems (atom #{}))

(defn filepath->link
  ([filepath]
   (str (.relativize
          (.toAbsolutePath (.toPath (io/file "")))
          (.toAbsolutePath (.toPath (io/file filepath))))))
  ([filepath line-number]
   (str (filepath->link filepath) "#L" line-number)))

(defmacro defproblem [symbol & body]
  (let [link (filepath->link *file* (:line (meta &form)))]
    `(let [v# (defn ~symbol []
                (let [[time# result#]
                      (miss/timing ~@body)
                      explain#
                      (miss/duration-explain
                        (Duration/ofNanos (* 1000000 time#)))]
                  (println (format "### [%s](%s)" (name '~symbol) ~link))
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
  (->> ["## [Advent Of Code 2019](https://adventofcode.com/2019)"
        \newline
        "This readme is auto-generated from the execution of my solutions."
        \newline
        (let [{:keys [name file line]} (meta #'defproblem)]
          (format "See [%s](%s)" (clojure.core/name name) (filepath->link file line)))
        \newline
        "---"
        \newline
        (do
          ;hotspot
          (dotimes [_ 50]
            (with-out-str
              (run-all)))
          (with-out-str (run-all)))]
       (strings/join \newline)
       (spit "readme.md")))