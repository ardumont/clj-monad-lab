(ns clj-monad-lab.monad
  "Learn to dry my understanding of monads. Implement something `'equivalent` in clojure'"
  (:require [expectations :as t]))

;; functor
;; fmap :: (a -> b) -> f a -> f b

;; Monad

;; (>>= nil        _)           <=> nil
;; (>>= (return a) identity)    <=> (return a)
;; (>>= (return a) (fn [a] ...) <=> (return ((fn [a] ...) a))

(defn return "a -> m a"
  [a]
  {:value a})

(t/expect {:value 10} (return 10))
(t/expect {:value [1 2 3]} (return [1 2 3]))

(defn fail "Deal with failure"
  [msg]
  (return msg))

(t/expect {:value "error message"} (fail "error message"))

(defn >>= "Monad m => m a -> (a -> m b) -> m b"
  [{:keys [value] :as monad-value} a->mb]
  (try (when monad-value (a->mb value))
       (catch java.lang.Exception e (fail "Error"))))

;; value
(t/expect {:value 10}
          (>>= (return 10) (comp identity return)))

(t/expect {:value 110}
          (>>= (return 10) #(return (+ 100 %))))

(t/expect {:value -11}
          (>>= (>>= (>>= {:value 10} #(return (* 2 %))) #(return (+ 1 %))) #(return (- 10 %))))

;; nil
(t/expect nil
          (>>= (>>= (>>= nil #(return (* 2 %))) #(return (+ 1 %))) #(return (- 10 %))))
(t/expect nil
          (>>= (>>= (>>= nil #(return (* 2 %))) #(return (+ 1 %))) #(return (- 10 %))))

;; exception
(t/expect nil
          (>>= (>>= (>>= {:value 10} #(return (* 2 %))) (constantly nil)) #(return (- 10 %))))

(t/expect {:value "Error"} (>>= (>>= (>>= {:value 10} #(return (* 2 %))) (fn [_] (/ 1 0))) #(return (- 10 %))))
