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
