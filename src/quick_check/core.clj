(ns quick-check.core
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(require '[clojure.test.check :as tc])
(require '[clojure.test.check.generators :as gen])
(require '[clojure.test.check.properties :as prop])

; (def sort-idempotent-prop
;   (prop/for-all [v (gen/vector gen/int)]
;     (= (sort v) (sort (sort v)))))

; (def run-specs
;   (println (tc/quick-check 100 sort-idempotent-prop)))

; (def prop-sorted-first-less-than-last
;   (prop/for-all [v (gen/not-empty (gen/vector gen/int))]
;     (let [s (sort v)]
;       (<= (first s) (last s)))))

; (def run-specs
;   (println (tc/quick-check 100 prop-sorted-first-less-than-last)))

(defn length-score
  [length]
  (cond
    (<= 7 length) 21
    (<= 6 length) 16
    (<= 5 length) 13
    (<= 4 length) 10
    (<= 3 length) 8
    (<= 2 length) 5
    :else 1))

(defn password-score
  [password]
  (length-score (count password)))

(def property-max-length-with-just-letters
  (prop/for-all [v gen/string]
    (and (>= 21 (password-score v))
         (< 0 (password-score v)))))

(def run-specs
  (println (tc/quick-check 1000000 property-max-length-with-just-letters)))


(defn -main
  "Running Specs"
  [& args]
  run-specs)
