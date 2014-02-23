(ns hack-clj.vm-test
  (:require [clojure.test :refer :all]
            [hack-clj.vm :refer :all]))

(deftest test-command-type
  (testing "should output :c-arithmetic for add/sub/neg/etc"
    (is (= :c-arithmetic (command-type "neg")))
    (is (= :c-arithmetic (command-type "and")))
    (is (= :c-arithmetic (command-type "sub")))
    (is (= :c-arithmetic (command-type "add"))))
  (testing "should output :c-push for push"
    (is (= :c-push (command-type "push"))))
  (testing "should output :c-pop for pop"
    (is (= :c-pop (command-type "pop"))))
  (testing "should output :c-label for label"
    (is (= :c-label (command-type "label"))))
  (testing "should output :c-function"
    (is (= :c-function (command-type "function"))))
  (testing "should output :c-return for return"
    (is (= :c-return (command-type "return"))))
  (testing "should output :c-call for call"
    (is (= :c-call (command-type "call")))))

(deftest test-compile-instruction
  (testing "compile push constant 10"
    (let [correct-output ["//push constant 10" 
                          "@10" 
                          "D=A" 
                          "@SP" 
                          "A=M" 
                          "M=D" 
                          "@SP" 
                          "M=M+1"]]
       (is (= correct-output (compile-instruction "push constant 10")))))
  (testing "compile pop local 2"
    (let [correct-output ["//pop local 2" "@SP" 
                          "M=M-1" "@LCL" 
                          "D=M" "@2" 
                          "D=D+A" "@R13" 
                          "M=D" "@SP" 
                          "A=M" "D=M" 
                          "@R13" "A=M" "M=D"]]
       (is (= correct-output (compile-instruction "pop local 2"))))))
