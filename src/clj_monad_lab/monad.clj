(ns clj-monad-lab.monad
  "Learn to dry my understanding of monads. Implement something `'equivalent` in clojure'")

;; functor
;; fmap :: (a -> b) -> f a -> f b

;; Monad

(defn return "a -> m a"
  [a]
  (when a {:value a}))

(defn bind "(>>=) :: Monad m => m a -> (a -> m b) -> m b"
  [{:keys [value] :as monad-value} a->b]
  (when monad-value
    (-> value
        a->b
        return)))

;; (bind nil        _)           <=> nil
;; (bind (return a) identity)    <=> (return a)
;; (bind (return a) (fn [a] ...) <=> (return ((fn [a] ...) a))

;; Samples:
;; clj-monad-lab.monad> (bind (return nil) (fn [a] (+ 1 a)))
;; nil
;; clj-monad-lab.monad> (bind nil (fn [a] (+ 1 a)))
;; nil
;; clj-monad-lab.monad> (bind 3 (fn [a] (+ 1 a)))
;; NullPointerException   clojure.lang.Numbers.ops (Numbers.java:942)
;; clj-monad-lab.monad> (bind (return 10) (fn [a] (+ 1 a)))
;; {:value 11}
;; clj-monad-lab.monad> (bind (return 10) identity)
;; {:value 10}
