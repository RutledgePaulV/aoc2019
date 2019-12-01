(ns aoc2019.day1
  (:require [clojure.java.io :as io]))

(defn calculate-fuel1 [mass]
  (Math/max (int 0) (int (- (Math/floor (/ mass 3.0)) 2))))

(defn calculate-fuel2 [mass]
  (let [fuel (calculate-fuel1 mass)]
    (if (pos? fuel)
      (+ fuel (calculate-fuel2 fuel))
      fuel)))

(defn line-by-line [f]
  (with-open [reader (io/reader f)]
    (doall (line-seq reader))))

(defn problem1 []
  (->> (io/resource "input1.txt")
       (line-by-line)
       (map #(Long/parseLong %))
       (map calculate-fuel1)
       (reduce +)
       (int)))

(defn problem2 []
  (->> (io/resource "input1.txt")
       (line-by-line)
       (map #(Long/parseLong %))
       (map calculate-fuel2)
       (reduce +)
       (int)))