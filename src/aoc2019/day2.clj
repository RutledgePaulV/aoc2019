(ns aoc2019.day2
  (:require [aoc2019.common :refer [defproblem run]]
            [clojure.java.io :as io]
            [clojure.string :as strings]))

(set! *warn-on-reflection* true)

(defn process [ticker]
  (loop [ticker ticker offset 0]
    (let [limit (+ 4 offset)
          [op reg1 reg2 ret] (subvec ticker offset limit)]
      (case (int op)
        1 (recur (assoc ticker ret (+ (ticker reg1) (ticker reg2))) limit)
        2 (recur (assoc ticker ret (* (ticker reg1) (ticker reg2))) limit)
        99 ticker))))

(defn get-tape []
  (->> (strings/split (slurp (io/resource "input2.txt")) #",")
       (mapv #(Long/parseLong %))))

(defn attempt [tape in1 in2]
  (-> tape
      (assoc 1 in1 2 in2)
      (process)
      (get 0)))

(defn combos [xs ys]
  (for [x xs y ys] [x y]))

(defproblem d02-p1
  (attempt (get-tape) 12 2))

(defproblem d02-p2
  (let [tape   (get-tape)
        search (into [] (range 100))]
    (loop [[[x y] & remainder] (combos search search)]
      (if (= 19690720 (attempt tape x y))
        (+ (* 100 x) y)
        (recur remainder)))))