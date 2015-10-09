(ns tdc-clj.data
  (:require [clojure.core.async :refer [go timeout <! >! chan]]))

(def matches
  {1 {:home 1 :away 2 :league 2}
   2 {:home 3 :away 4 :league 1}})

(def leagues
  [{:id 1 :name "Premier League"}
   {:id 2 :name "La Liga"}
   {:id 3 :name "Tippeligaen"}])

(def players
  {1 ["Benzema" "Ronaldo" "Ramos" "Modric" "Bale"]
   2 ["Messi" "Neymar" "Suarez" "Iniesta" "Alves"]
   3 ["Rooney" "Memphis" "Herrera" "Smalling" "Blind"]
   4 ["Sanchez" "Ramsay" "Giroud" "Cazorla" "Bellerin"]})

(def teams
  [{:id 1 :name "Real Madrid"}
   {:id 2 :name "Barcelona"}
   {:id 3 :name "Manchester United"}
   {:id 4 :name "Arsenal"}])

(def events
  [{:type :chance :message "Rough tackle by %s, free kick awarded to %s"}
   {:type :penalty :message "%s uses his hand to stop the ball. %s to take the penalty"}])

(defn random-player [team-id]
  (-> players (get team-id) rand-nth))

(defn random-event [match-id]
  (let [{:keys [home away]} (get matches match-id)
        home-player (random-player home)
        away-player (random-player away)
        message (rand-nth events)]
    (-> message
        (update-in [:message] format home-player away-player)
        (assoc :matchId match-id))))

(defn get-random-data []
  (random-event (inc (rand-int 2))))

(def sequence
  [10000 (random-event 1) 5000 (random-event 2) 5000 (get-random-data) 2000
   {:type :goal :message "Rooney scores for Manchester United! A wonderful shot, Checzh is nowhere near it!"}])