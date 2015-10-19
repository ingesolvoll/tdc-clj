(ns tdc-clj.data
  (:require [clojure.core.async :refer [go timeout <! >! chan]]))

(def matches
  {:BARREA {:home "Barcelona" :away "Real Madrid" :league "Premier League"}
   :MANARS {:home "Manchester United" :away "Arsenal" :league "La Liga"}})

(def players
  {"Real Madrid" ["Benzema" "Ronaldo" "Ramos" "Modric" "Bale"]
   "Barcelona" ["Messi" "Neymar" "Suarez" "Iniesta" "Alves"]
   "Manchester United" ["Rooney" "Memphis" "Herrera" "Smalling" "Blind"]
   "Arsenal" ["Sanchez" "Ramsay" "Giroud" "Cazorla" "Bellerin"]})

(def events
  [{:type :chance :message "Rough tackle by %s, free kick awarded to %s"}
   {:type :penalty :message "%s uses his hand to stop the ball. %s to take the penalty"}
   {:type :chance :message "Free kick in a very good position as %s brings %s to the ground"}
   {:type :yellow :message "%s is booked for a poor challenge on %s."}
   {:type :sub :message "%s is replaced by %s."}
   {:type :chance :message "Strong shout for a penalty as %s's goal-bound attempt is blocked by %s, but the referee says it was not with his hands. The home supporters go mad!"}
   {:type :chance :message "Half-chance as %s drives down the left before delivering a cross towards %s"}])

(defn random-player [team-id]
  (-> players (get team-id) rand-nth))

(defn random-event [match-id]
  (let [{:keys [home away]} (match-id matches)
        home-player (random-player home)
        away-player (random-player away)
        message (rand-nth events)]
    (-> message
        (update-in [:message] format home-player away-player)
        (assoc :matchId match-id))))

(defn get-random-data []
  (random-event (rand-nth (keys matches))))

(def event-sequence
  [1 (random-event :BARREA)
   5 (random-event :MANARS)
   5 (get-random-data)
   2 {:type :goal :message "Rooney scores for Manchester United! A wonderful shot, Checzh is nowhere near it!"}])
