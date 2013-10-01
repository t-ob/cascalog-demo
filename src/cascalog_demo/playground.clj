(ns cascalog-demo.playground
  (:require [cascalog.api :refer :all]
            [cascalog.playground :refer [bootstrap-emacs]]
            [cascalog.io :refer [with-log-level]]))

#_(bootstrap-emacs)

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]]]
  (?- (stdout)
      (<- [?a ?b ?c ?d]
          (source ?a ?b ?c ?d))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]]]
  (?- (stdout)
      (<- [?a ?b ?c ?d]
          (source ?a ?b ?c ?d))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d]
              (source ?a ?b ?c ?d)))))


#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]
                ["b" 2 :baz {:another :map}]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d]
              (source ?a ?b ?c ?d)
              (= ?a "b")))))


#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]
                ["b" 2 :baz {:another :map}]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d]
              (source ?a ?b ?c ?d)
              (identity 2 :> ?b)))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]
                ["b" 2 :baz {:another :map}]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d]
              (source ?a ?b ?c ?d)
              (contains? #{2} ?b)))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]
                ["b" 2 :baz {:another :map}]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d]
              (source ?a ?b ?c ?d)
              (contains? #{:foo :bar} ?c) ;; WHERE ?c = :foo OR ?c = :bar
              ))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]
                ["b" 2 :baz {:another :map}]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d ?e]
              (source ?a ?b ?c ?d)
              
              (get ?d :as :> ?e)
              
              (contains? #{:foo :bar} ?c) ;; WHERE ?c = :foo OR ?c = :bar
              ))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]
                ["b" 2 :baz {:another :map}]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?f !e]
              (source ?a ?b ?c ?d)
              
              (get ?d :as :> !e)
              (assoc ?d :cool-key :cool-value :> ?f)
              
              (contains? #{:foo :bar} ?c) ;; WHERE ?c = :foo OR ?c = :bar
              ))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]
                ["b" 2 :baz {:another :map}]
                ["c" 2 :foo "This is a string"]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d !e]
              (source ?a ?b ?c ?d)

              (get ?d :as :> !e)
              (assoc ?d :cool-key :cool-value :> ?f)

              (contains? #{:foo :bar} ?c) ;; WHERE ?c = :foo OR ?c = :bar

              ))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
                ["a" 2 :bar {:this :fits :in :a :single :var :as :well}]
                ["b" 2 :baz {:another :map}]
                ["c" 2 :foo "This is a string"]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d !e]
              (source ?a ?b ?c ?d)

              (get ?d :as :> !e)
              (assoc ?d :cool-key :cool-value :> ?f)

              (contains? #{:foo :bar} ?c) ;; WHERE ?c = :foo OR ?c = :bar

              (:trap (stdout))
              ))))

(defn split-line [line & {:keys [delimiter-pattern]
                          :or   {delimiter-pattern #"\|"}}]
  (clojure.string/split line
                        delimiter-pattern))

#_(let [lines [["a|1|:foo|{:this :fits :in :a :single :var}"]]]
  (with-log-level :warn
    (?- (stdout)
        (<- [?a ?b ?c ?d ?d-class]
            (lines :> ?line)
            (split-line ?line :> ?a ?b ?c ?d)
            (class ?d :> ?d-class)))))

#_(let [lines [["a|1|:foo|{:this :fits :in :a :single :var}"]]]
  (with-log-level :warn
    (?- (stdout)
        (<- [?a ?b ?c ?d ?d-class]
            (lines :> ?line)
            (split-line ?line :> ?a ?b ?c-str ?d-str)

            (read-string ?c-str :> ?c)
            (read-string ?d-str :> ?d)
            (class ?d :> ?d-class)))))

#_(let [lines [["1234|1234_23409|:foo|{:this :fits :in :a :single :var}"]]]
  (with-log-level :warn
    (?- (stdout)
        (<- [?a ?b ?c ?d]
            (lines :> ?line)
            (split-line ?line :> ?a ?b ?c-str ?d-str)

            (read-string ?c-str :> ?c)
            (read-string ?d-str :> ?d)))))

(defn cool-split-line [line & {:keys [delimiter-pattern]
                          :or   {delimiter-pattern #"\|"}}]
  (map read-string
       (clojure.string/split line
                             delimiter-pattern)))

#_(let [lines [["1234|1234_23409|:foo|{:this :fits :in :a :single :var}"]]]
  (with-log-level :warn
    (?- (stdout)
        (<- [?a ?b ?c ?d]
            (lines :> ?line)
            (cool-split-line ?line :> ?a ?b ?c ?d)))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
