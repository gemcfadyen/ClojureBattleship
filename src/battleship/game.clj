(ns battleship.game)

(def ships
  {:submarine #{:A1}})

(defn hit? [guess ships]
  (boolean (some true? (map (fn [[ship-name coordinates]] (boolean (some #(= guess %) coordinates))) ships))))
