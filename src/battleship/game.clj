(ns battleship.game)

(def ships
  {:submarine #{:A1}})

(defn- ship-contains-coord? [guess [ship-name coordinates]]
  (when (coordinates guess) ship-name))

(defn- find-ship [guess ships]
  (some (partial ship-contains-coord? guess) ships))

(defn to-hit [guess ships]
  (if-let [shipname (find-ship guess ships)]
    [guess :hit shipname]
    [guess :miss]))

(defn- no-hits? [hits coordinates]
 (empty? (clojure.set/intersection (set (map first hits)) coordinates)))

; map each submarine to either sunk, hit or untouched
(defn collate-ship [hits [ship-name coordinates]]
 (if (no-hits? hits coordinates)
  {ship-name :untouched}
  {ship-name :sunk})
  )

(defn to-collate [hits ships]
  (apply merge (map (partial collate-ship hits) ships)))
