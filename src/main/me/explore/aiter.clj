(ns me.explore.aiter)

(defmacro hello-macro [msg]
  `(println "Your message is:" (pr-str ~msg)))


(comment
  ,

  (hello-macro "world")

  ,)
