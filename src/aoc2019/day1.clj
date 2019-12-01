(ns aoc2019.day1
  (:require [clojure.java.io :as io]))

(defn calculate-fuel1 [mass]
  (Math/max (int 0) (int (- (Math/floor (/ mass 3.0)) 2))))

(defn calculate-fuel2 [mass]
  (loop [fuel (calculate-fuel1 mass) sum 0]
    (if (pos? fuel)
      (recur (calculate-fuel1 fuel) (+ sum fuel))
      sum)))

(defn problem1 []
  (with-open [reader (io/reader (io/resource "input1.txt"))]
    (->> (line-seq reader)
         (map #(Long/parseLong %))
         (map calculate-fuel1)
         (reduce +)
         (int))))

(defn problem2 []
  (with-open [reader (io/reader (io/resource "input1.txt"))]
    (->> (line-seq reader)
         (map #(Long/parseLong %))
         (map calculate-fuel2)
         (reduce +)
         (int))))