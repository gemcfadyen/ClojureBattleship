(ns battleship.game-spec
  (:require [speclj.core :refer :all]
            [battleship.game :refer :all]))

;[:ac #{:A1 :A2 :A3 :A4 :A5}]

;(defn- is-hit? [move ship]
;  (some ship move))

;(is-hit? :A1 #{:A1 :A2})

;(is-sunk? ) ?

(describe "BattleWomanShip"
          (it "Guessed co-ordinate hits a ship"
              (should= true
                       (hit? :A1 ships))))
