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

(def ships
  {:submarine #{:A1}})

(def destroyer-ships
  {:destroyer #{:B1 :B2}})
(describe "BattleWomanShip"
          (it "Sinks ship if all co-ordinates for that ship are guessed"
              (should= {:submarine :sunk} (statuses #{:A1} ships)))
          (it "Marks ship as untouched"
              (should= {:submarine :untouched} (statuses #{:A2} ships)))
          (it "Marks ship as hit"
              (should= {:destroyer :hit} (statuses #{:B2} destroyer-ships)))
          (it "Sinks destroyer"
              (should= {:destroyer :sunk} (statuses #{:B1 :B2} destroyer-ships)))
          (it "Marks multiple ships"
              (should= {:submarine :sunk
                        :destroyer :untouched} (statuses #{:A1} {:submarine #{:A1} :destroyer #{:B1 :B2}})))
          (it "Marks ship as hit"
              (should= [:A1 :hit :submarine] (to-hit :A1 ships)))
          (it "does not mark when no ship is hit"
              (should= [:A2 :miss] (to-hit :A2 ships))))

(describe :play-move
          (it "plays a first move"
              (should= [{:submarine :sunk} #{:A1}] (play-guess :A1 #{} ships)))
          (it "remembers guesses"
              (should= [{:destroyer :sunk} #{:B2 :B1}] (play-guess :B2 #{:B1} destroyer-ships))))

(describe :game-over
          (it "is not over is nothing hit"
              (should= false (game-over? {:a :untouched :b :untouched})))
          (it "is over"
              (should= true (game-over? {:a :sunk :b :sunk}))))

(describe :play-game
          (it "Formats ship status"
              (should= "Destroyer untouched!\nSubmarine sunk!\n" (format-ships {:submarine :sunk
                                                                          :destroyer :untouched})) )
          (it "plays until won"
               (should-contain "Destroyer sunk!\nSubmarine sunk!\n\nGame over, well done!"
                               (with-out-str (with-in-str "A1\nA2\nB1\n"
                                               (play-game {:submarine #{:B1} :destroyer #{:A1 :A2}} #{}))))))
