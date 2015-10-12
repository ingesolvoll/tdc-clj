(ns tdc-clj.interactive
  (:require [tdc-clj.data :refer :all]
            [tdc-clj.core :refer [run-steps!]]))

; Infinite sequence of random events with random pauses in between
(def seq-1 (repeatedly
            (fn []
              [(rand-int 10) (get-random-data)])))


; Infinite sequence of the numbers 1 2 3 repeated
(cycle [1 2 3])


; Infinite sequence of random events from every second match
(def cycling-matches (map
                      random-event
                      (cycle [:BARREA :MANARS])))
