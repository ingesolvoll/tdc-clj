(ns tdc-clj.interactive
  (:require [tdc-clj.data :refer :all]
            [tdc-clj.core :refer [run-steps!]]))









(map inc [1 2 3])











(filter pos? [-100 54 -3 0 100])











(reduce + 15 [1 2 3 4 5])










; Make an infinite list containing the results of running the supplied function

(repeatedly #(rand-int 10))













; Infinite sequence of the numbers 1 2 3 repeated
(cycle [1 2 3])














; Infinite sequence of random events with random pauses in between
(def seq-1 (repeatedly
            (fn []
              [(rand-int 10) (get-random-data)])))

















; Infinite sequence of random events from every second match
(def cycling-matches (map
                      random-event
                      (cycle [:BARREA :MANARS])))
