(ns clojure.core-test.string-questionmark
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.number-range :as r]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer)  [when-var-exists]]))

(when-var-exists clojure.core/string?
 (deftest test-string?
   (are [expected x] (= expected (string? x))
     false 0
     false 1
     false -1
     false r/max-int
     false r/min-int
     false 0.0
     false 1.0
     false -1.0
     false (float 0.0)
     false (float 1.0)
     false (float -1.0)
     false (double 0.0)
     false (double 1.0)
     false (double -1.0)
     false r/max-double
     false r/min-double
     false ##Inf
     false ##-Inf
     false ##NaN
     false 0N
     false 1N
     false -1N
     #?@(:cljs []
         :default
         [false 0/2
          false 1/2
          false -1/2])
     false 0.0M
     false 1.0M
     false -1.0M
     false nil
     false true
     false false
     true "a string"
     true "0"
     true "1"
     true "-1"
     false {:a :map}
     false #{:a-set}
     false [:a :vector]
     false '(:a :list)
     false  \0
     false  \1
     false \A
     false \space
     false :a-keyword
     false :0
     false :1
     false :-1
     false 'a-sym)))
