(ns aoc2019.day2
  (:require [aoc2019.common :refer [defproblem run]]
            [clojure.java.io :as io]
            [clojure.string :as strings]
            [missing.core :as miss]))



(defn process [numbers]
  (loop [ticker numbers offset 0]
    (let [[op reg1 reg2 ret] (drop offset ticker)]
      (case op
        1 (recur (assoc ticker ret
                   (+ (get ticker reg1)
                      (get ticker reg2)))
                 (+ offset 4))
        2 (recur (assoc ticker ret
                   (* (get ticker reg1)
                      (get ticker reg2)))
                 (+ offset 4))
        99 ticker))))

(defn get-tape []
  (->> (strings/split (slurp (io/resource "input2.txt")) #",")
       (mapv #(Long/parseLong %))))

(defn attempt [tape in1 in2]
  (-> tape
      (assoc 1 in1)
      (assoc 2 in2)
      (process)
      (get 0)))

(defproblem d02-p1
  (attempt (get-tape) 12 2))

(defproblem d02-p2
  (let [tape (get-tape)]
    (miss/preemptable
      (doseq [x (range 100) y (range 100)]
        (when (= 19690720 (attempt tape x y))
          (miss/preempt (+ (* 100 x) y)))))))