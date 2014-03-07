(ns clj-monad-lab.monad
  "Monads tryout. Implement something `'equivalent` in clojure'"
  (:require [expectations :as t]))

;; functor
;; fmap :: (a -> b) -> f a -> f b

;; Monad

;; (>>= nil        _)           <=> nil
;; (>>= (return a) identity)    <=> (return a)
;; (>>= (return a) (fn [a] ...) <=> (return ((fn [a] ...) a))

(defn return "a -> m a"
  [a]
  (fn [] a))

(t/expect 10      ((return 10)))
(t/expect [1 2 3] ((return [1 2 3])))

(defn fail "Deal with failure"
  [msg]
  (return msg))

(t/expect "Error message" ((fail "Error message")))

(defn >>= "Monad m => m a -> (a -> m b) -> m b"
  [monad-value fn]
  (-> (monad-value)
      fn
      (try (catch java.lang.Exception e (fail (format "Error: %s" (.getMessage e)))))))

;; value
(t/expect 110
          ((>>= (return 10) #(return (+ 100 %)))))

(t/expect -11
          ((-> (return 10)
                (>>= #(return (* 2 %)))
                (>>= #(return (+ 1 %)))
                (>>= #(return (- 10 %))))))

;; exception
(t/expect "Error: null"
          ((-> (return 10)
                (>>= #(return (* 2 %)))
                (>>= (constantly nil))
                (>>= #(return (- 10 %))))))

(t/expect "Error: some dummy error"
          ((-> (return 10)
                (>>= (fn [a] (throw (java.lang.NullPointerException. "some dummy error")))))))
