(ns battleship.game-spec
  (:require [speclj.core :refer :all]
            [battleship.game :refer :all]))

;[:ac #{:A1 :A2 :A3 :A4 :A5}]

;(defn- is-hit? [move ship]
;  (some ship move))

;(is-hit? :A1 #{:A1 :A2})

;(is-sunk? ) ?

[:A1 :hit :submarine]
[:A2 :hit :submarine]
[:A3 :hit :cruiser]

{:submarine :sunk
 :cruiser :hit
 :destroyer :untouched}

(describe "BattleWomanShip"
          (it "Sinks ship if all co-ordinates for that ship are guessed"
              (should= {:submarine :sunk} (to-collate [[:A1 :hit :submarine]] ships)))
          (it "Marks ship as untouched"
              (should= {:submarine :untouched} (to-collate [[:A2 :miss]] ships)))
          (it "Marks ship as hit"
              (should= [:A1 :hit :submarine] (to-hit :A1 ships)))
          (it "does not mark when no ship is hit"
              (should= [:A2 :miss] (to-hit :A2 ships))))
