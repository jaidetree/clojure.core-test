(ns clojure.core-test.symbol
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer)  [when-var-exists]]))

(when-var-exists clojure.core/symbol
 (deftest test-symbol
   ;; "Symbols begin with a non-numeric character and can contain
   ;; alphanumeric characters and *, +, !, -, _, ', ?, <, > and =
   ;; (other characters may be allowed eventually)."
   ;; (see http://clojure.org/reader for details)
   ;;
   ;; From https://clojuredocs.org/clojure.core/keyword
   ;; keyword does not validate input strings for ns and name, and may
   ;; return improper keywords with undefined behavior for non-conformant
   ;; ns and name.

   (are [expected name] (= expected (symbol name))
     'abc "abc"
     'abc 'abc
     'abc :abc
     '* "*"
     '* '*
     '* :*
     '+ "+"
     '+ '+
     '+ :+
     '! "!"
     '! '!
     '! :!
     '- "-"
     '- '-
     '- :-
     '_ "_"
     '_ '_
     '_ :_
     '? "?"
     '? '?
     '? :?
     '< "<"
     '< '<
     '< :<
     '> ">"
     '> '>
     '> :>
     '= "="
     '= '=
     '= :=
     'abc*+!-_'?<>= "abc*+!-_'?<>=")

   (are [expected ns name] (= expected (symbol ns name))
     'abc/abc     "abc"     "abc"
     'abc.def/abc "abc.def" "abc"

     '*/abc "*" "abc"
     '+/abc "+" "abc"
     '!/abc "!" "abc"
     '-/abc "-" "abc"
     '_/abc "_" "abc"
     '?/abc "?" "abc"
     '</abc "<" "abc"
     '>/abc ">" "abc"
     '=/abc "=" "abc"

     'abc.def/* "abc.def" "*"
     'abc.def/+ "abc.def" "+"
     'abc.def/! "abc.def" "!"
     'abc.def/- "abc.def" "-"
     'abc.def/_ "abc.def" "_"
     'abc.def/? "abc.def" "?"
     'abc.def/< "abc.def" "<"
     'abc.def/> "abc.def" ">"
     'abc.def/= "abc.def" "="

     'abc*+!-_'?<>=/abc*+!-_'?<>= "abc*+!-_'?<>=" "abc*+!-_'?<>=")

   (is (thrown? #?(:cljs :default :clj Exception) (symbol nil))) ; keyword returns nil
   (is (= 'abc (symbol nil "abc"))) ; But if ns is nil, it just ignores it.

   ;; prints as 'abc/null but the null is really a nil. Since this is
   ;; not readable via the standard Clojure reader, I'm not even sure
   ;; how to test this case here. That's why it's commented out.
   ;; Note that `keyword` throws for this case.
   ;; (is (= 'abc/null (symbol "abc" nil)))

   ;; Two arg version requires namespace and symbol to be a string, not
   ;; a symbol or keyword like the one arg version.
   (is (thrown? #?(:cljs :default :clj Exception) (symbol 'abc "abc")))
   (is (thrown? #?(:cljs :default :clj Exception) (symbol "abc" 'abc)))
   (is (thrown? #?(:cljs :default :clj Exception) (symbol :abc "abc")))
   (is (thrown? #?(:cljs :default :clj Exception) (symbol "abc" :abc)))))
