(ns clojure.core-test.bit-and-not
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.number-range :as r]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer)  [when-var-exists]]))

(when-var-exists clojure.core/bit-and-not
  (deftest test-bit-and-not
    #?(:clj (is (thrown? NullPointerException (bit-and-not nil 1)))
       :cljs (is (bit-and-not nil 1)))
    #?(:clj (is (thrown? NullPointerException (bit-and-not 1 nil)))
       :cljs (is (bit-and-not 1 nil)))

    (are [ex a b] (= ex (bit-and-not a b))
      0                        0                        0
      8                        12                       4
      0xff                     0xff                     0
      0x80                     0xff                     0x7f
      r/all-ones-int           r/all-ones-int           0
      0                        0                        r/all-ones-int
      0                        r/all-ones-int           r/all-ones-int
      r/full-width-checker-pos r/full-width-checker-pos 0
      0                        r/full-width-checker-pos r/full-width-checker-pos
      0                        r/full-width-checker-pos r/all-ones-int
      r/full-width-checker-pos r/full-width-checker-pos r/full-width-checker-neg)))
