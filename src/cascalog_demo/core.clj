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
              ["a" 2 :bar {:this p:fits :in :a :single :var :as :well}]]]
  (?- (stdout)
      (<- [?a ?b ?c ?d]
          (source ?a ?b ?c ?d))))

#_(let [source [["a" 1 :foo {:this :fits :in :a :single :var}]
              ["a" 2 :bar {:this p:fits :in :a :single :var :as :well}]]]
    (with-log-level :warn
      (?- (stdout)
          (<- [?a ?b ?c ?d]
              (source ?a ?b ?c ?d)))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
