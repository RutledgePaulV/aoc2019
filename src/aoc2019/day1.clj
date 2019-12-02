(ns aoc2019.day1
  (:require [aoc2019.common :refer [defproblem run]]
            [clojure.java.io :as io]))

(set! *warn-on-reflection* true)

(defn calculate-fuel1 [mass]
  (int (Math/max (int 0) (int (- (quot mass 3) 2)))))

(defn calculate-fuel2 [mass]
  (loop [mass mass sum 0]
    (let [increment (calculate-fuel1 mass)]
      (if (pos? increment)
        (recur increment (long (+ sum increment)))
        sum))))

(defproblem d01-p1
  (with-open [reader (io/reader (io/resource "input1.txt"))]
    (->> (line-seq reader)
         (map #(Long/parseLong %))
         (map calculate-fuel1)
         (reduce +))))

(defproblem d01-p2
  (with-open [reader (io/reader (io/resource "input1.txt"))]
    (->> (line-seq reader)
         (map #(Long/parseLong %))
         (map calculate-fuel2)
         (reduce +))))