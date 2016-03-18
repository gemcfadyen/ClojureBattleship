(ns battleship.game
  (:require [clojure.set :as sets])
 (:gen-class) )

(defn- ship-contains-coord? [guess [ship-name coordinates]]
  (when (coordinates guess) ship-name))

(defn- find-ship [guess ships]
  (some (partial ship-contains-coord? guess) ships))

(defn to-hit [guess ships]
  (if-let [shipname (find-ship guess ships)]
    [guess :hit shipname]
    [guess :miss]))

(defn- no-hits? [coordinates hit-set]
  (empty? (sets/intersection hit-set coordinates)))

(def all-hits? sets/subset?)

(defn collate-ship [hit-set [ship-name coordinates]]
  (cond
    (no-hits? coordinates hit-set) {ship-name :untouched}
    (all-hits? coordinates hit-set) {ship-name :sunk}
    :else {ship-name :hit}))

(defn statuses [hits ships]
  (apply merge (map (partial collate-ship hits) ships)))

(defn play-guess [guess previous-guesses ships]
  (let [guesses (conj previous-guesses guess)]
    [(statuses guesses ships) guesses]))

(defn game-over? [statuses]
  (= '(:sunk) (distinct (map val statuses))))

(defn format-ship [[ship-name status]]
  (str (clojure.string/capitalize (name ship-name)) " " (name status) "!\n"))

(defn format-ships [statuses]
  (apply str (map format-ship (sort-by first statuses))))

(def alphabet "ABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn- all-ship-squares [ships]
  (apply sets/union (map val ships)))

(defn- display-square [guesses ships letter number]
  (let [coord (keyword (str letter (inc number)))]
    (if-not (some #{coord} guesses)
      " "
      (if (some #{coord} (all-ship-squares ships))
        "X"
        "/"))))

(defn- display-cell [size guesses ships letter]
  (str letter (apply str (map (partial display-square guesses ships letter) (range size))) "\n"))

(defn display-grid [size guesses ships]
  (str " " (apply str (range 1 (inc size))) "\n"
       (apply str (map (partial display-cell size guesses ships) (take size alphabet)))))

(defn play-game [ships previous-guesses]
 (let [guess (keyword (read-line))
       statuses (play-guess guess previous-guesses ships)]
   (println (format-ships (first statuses)))

   (if (game-over? (first statuses))
     (println "Game over, well done!")
     (play-game ships (second statuses))
     )
   ))

(defn -main []
  (play-game {:submarine #{:A1} :destroyer #{:B1 :B2}} #{}))
