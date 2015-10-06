(ns tdc-clj.data)

(defonce key-counter (atom 0))

(defn generate-key []
  (let [key (swap! key-counter inc)]
    (keyword (str "key-" key))))

(def matches
  [{:id 1 :home "Rosenborg" :away "Molde" :league 1}
   {:id 2 :home "Bodø/Glimt" :away "Tromsø" :league 1}
   {:id 3 :home "Chelsea" :away "Manchester United" :league 2}
   {:id 4 :home "Borussia Dortmund" :away "Bayern Munchen" :league 3}])

(def types
  [:default :yellow :red :penalty :goal :injury :drama :chance :sub])

(defn match-data []
  [{:id (generate-key) :league 1 :match 1 :type :chance :message "Søderlund går i duell med Berg Hestad høyt i banen, og får frispark!"}
   {:id (generate-key) :league 1 :match 2 :type :yellow :message "Trond Olsen blir tatt uten å få frispark. Blir så sint at han får gult kort av dommeren"}
   {:id (generate-key) :league 3 :match 4 :type :sub :message "Marco Reus får stoppet Arjen Robben i siste liten, like før han var alene med keeper der"}])

(defn get-random-data []
  (rand-nth (match-data)))