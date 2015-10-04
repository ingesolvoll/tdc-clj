(ns tdc-clj.data)

(def matches
  [{:id 1 :home "Rosenborg" :away "Molde" :league 1}
   {:id 2 :home "Bodø/Glimt" :away "Tromsø" :league 1}
   {:id 3 :home "Chelsea" :away "Manchester United" :league 2}
   {:id 4 :home "Borussia Dortmund" :away "Bayern Munchen" :league 3}])

(def types
  [:default :yellow :red :penalty :goal :injury :drama :chance :sub])

(def match-data
  [{:league 1 :match 1 :type :chance :message "Søderlund går i duell med Berg Hestad høyt i banen, og får frispark!"}
   {:league 1 :match 2 :type :yellow :message "Trond Olsen blir tatt uten å få frispark. Blir så sint at han får gult kort av dommeren"}
   {:league 3 :match 4 :type :sub :message "Marco Reus får stoppet Arjen Robben i siste liten, like før han var alene med keeper der"}])