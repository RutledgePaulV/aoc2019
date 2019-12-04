(ns aoc2019.day4
  (:require [aoc2019.common :refer [defproblem run]]
            [missing.core :as miss]
            [clojure.string :as strings])
  (:import (java.util.regex Pattern)))

(def regex
  (Pattern/compile
    (str "(" (->> (for [i (range 10)]
                    (format "(^[^%d]*%d{2}[^%d]*$)" i i i))
                  (strings/join "|")) ")")))

(defn one-dupe? [s]
  (re-find regex s))

(defn some-dupe? [s]
  (let [uniq (count (set s))]
    (< uniq (.length s))))

(defn ascending? [s]
  (apply miss/lte s))

(defproblem d04-p1
  (->> (range 248345 746315)
       (map str)
       (filter (every-pred ascending? some-dupe?))
       (count)))

(defproblem d04-p2
  (->> (range 248345 746315)
       (map str)
       (filter (every-pred ascending? one-dupe?))
       (count)))