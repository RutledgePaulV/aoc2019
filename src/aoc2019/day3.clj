(ns aoc2019.day3
  (:require [aoc2019.common :refer [defproblem run]]
            [clojure.java.io :as io]
            [clojure.string :as strings]
            [clojure.set :as sets]))

(def origin [0 0])

(defn instruction [[d & others]]
  (let [dist (Long/parseLong (apply str others))]
    (for [_ (range dist)]
      [(get {\L -1 \R 1} d 0)
       (get {\D -1 \U 1} d 0)])))

(def add (partial mapv +))

(defn distance [[x1 y1] [x2 y2]]
  (+ (Math/abs (int (- x2 x1))) (Math/abs (int (- y2 y1)))))

(defn positions [path]
  (vec (reductions add origin path)))

(defn get-wires []
  (with-open [reader (io/reader (io/resource "input3.txt"))]
    (->> reader
         (line-seq)
         (map #(strings/split % #","))
         (mapv #(vec (mapcat instruction %))))))

(defn nearest [points]
  (->> (disj (set points) origin)
       (apply (partial min-key (partial distance origin)))))

(defproblem d03-p1
  (->> (map (comp set positions) (get-wires))
       (apply sets/intersection)
       (nearest)
       (distance origin)))

(defproblem d03-p2
  (let [paths (mapv positions (get-wires))]
    (->> (for [intersection (apply sets/intersection (map set paths))
               :when (not= intersection origin)
               :let [cost (reduce + 0 (mapv #(.indexOf % intersection) paths))]]
           {:cost cost :intersection intersection})
         (sort-by :cost)
         (map :cost)
         (first))))