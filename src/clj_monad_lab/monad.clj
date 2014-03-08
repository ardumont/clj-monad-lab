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

(defn fail "Deal with failure"
  [msg]
  (return msg))

(defn >>= "Monad m => m a -> (a -> m b) -> m b"
  [monad-value fn]
  (-> (monad-value)
      fn
      (try (catch java.lang.Exception e (fail (format "Error: %s" (.getMessage e)))))))
