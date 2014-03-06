(ns clj-monad-ab.monad
  "Learn to dry my understanding of monads. Implement something `'equivalent` in clojure'")

;; functor
;; fmap :: (a -> b) -> f a -> f b

;; Monad

(defn bind "(>>=) :: Monad m => m a -> (a -> m b) -> m b"
  [{:keys [maybe-a]} a->b]
  (return (a->b maybe-a))  )

(defn return "a -> m a"
  [a]
  {:maybe a})
