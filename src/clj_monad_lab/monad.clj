(ns clj-monad-lab.monad
  "Learn to dry my understanding of monads. Implement something `'equivalent` in clojure'")

;; functor
;; fmap :: (a -> b) -> f a -> f b

;; Monad

(defn return "a -> m a"
  [a]
  (when a {:value a}))

(defn >>= "Monad m => m a -> (a -> m b) -> m b"
  [{:keys [value] :as monad-value} a->mb]
  (when monad-value
    (a->mb value)))

;; (>>= nil        _)           <=> nil
;; (>>= (return a) identity)    <=> (return a)
;; (>>= (return a) (fn [a] ...) <=> (return ((fn [a] ...) a))

;; clj-monad-lab.monad> (>>= (return 10) (comp identity return))
;; {:value 10}
;; clj-monad-lab.monad> (>>= (return 10) #(return (+ 100 %)))
;; {:value 110}

;; clj-monad-lab.monad> (>>= (>>= (>>= {:value 10} #(return (* 2 %))) #(return (+ 1 %))) #(return (- 10 %)))
;; {:value -11}

;; it would be better in infix notation
;; {:value 10} >>= #(return (* 2 %)) >>= #(return (+ 1 %)) >>= #(return (- 10 %))

;; clj-monad-lab.monad> (>>= (>>= (>>= nil #(return (* 2 %))) #(return (+ 1 %))) #(return (- 10 %)))
;; nil

;; clj-monad-lab.monad> (>>= (>>= (>>= {:value 10} #(return (* 2 %))) (constantly nil)) #(return (- 10 %)))
;; nil
