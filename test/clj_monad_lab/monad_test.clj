(ns clj-monad-lab.monad-test
  (:require [clj-monad-lab.monad :refer :all]
            [clojure.test :refer :all]
            [expectations :as t]))

(t/expect 10      ((return 10)))
(t/expect [1 2 3] ((return [1 2 3])))

(t/expect "Error message" ((fail "Error message")))

(t/expect 110
          ((>>= (return 10) #(return (+ 100 %)))))
(t/expect -11
          ((-> (return 10)
               (>>= #(return (* 2 %)))
               (>>= #(return (+ 1 %)))
               (>>= #(return (- 10 %))))))

(t/expect "Error: null"
          ((-> (return 10)
                (>>= #(return (* 2 %)))
                (>>= (constantly nil))
                (>>= #(return (- 10 %))))))
(t/expect "Error: some dummy error"
          ((-> (return 10)
                (>>= (fn [a] (throw (java.lang.NullPointerException. "some dummy error")))))))
