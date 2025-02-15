(ns me.node
  (:require
   [shadow.cljs.modern :refer (js-await)]))


(defn hello [] "hello")

(defn ^:export main [& args]
  (js/console.log (hello) args))

(comment
  (js-await [x (js/Promise. (fn [s _f] (s 10)))]
    (prn x))

  (-> (js/Promise.resolve 10)
      (.then prn))

  ,)
