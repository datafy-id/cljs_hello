(ns me.node
  (:require-macros
   [me.explore.aiter :refer [hello-macro]])
  (:require
   [shadow.cljs.modern :refer (js-await)]
   [cljs-node-io.core :as io]
   [missionary.core :as m]
   [clojure.core.async :as a]
   ["node:fs" :as fs]
   ["node:stream" :as stream]))

(defn hello [] "hello")

(defn ^:export main [& args]
  (js/console.log (hello) args))

(defn promise->task [p]
  (let [v (m/dfv)]
    (.then p #(v (fn [] %)) #(v (fn [] (throw %))))
    (m/absolve v)))

(defn >!
  "Puts given value on given channel, returns a task completing with true when
  put is accepted, of false if port was closed."
  [c x]
  (doto (m/dfv) (->> (a/put! c x))))

(defn <!
  "Takes from given channel, returns a task completing with value when take is
  accepted, or nil  if port was closed."
  [c]
  (doto (m/dfv) (->> (a/take! c))))


(comment
  (js-await [x (js/Promise. (fn [s _f] (s 10)))]
    (prn x))

  (-> (js/Promise.resolve 10)
      (.then prn))

  (->> (doto (js/Set.)
         (.add "A")
         (.add "B"))
       (into #{}))

  (io/slurp "./package.json")

  (-> (io/slurp "./package.json")
      (js/JSON.parse))

  ((promise->task (js/Promise.resolve "OK"))
   (fn [x] (prn x))
   (fn [x] (prn x)))


  ((promise->task (js/Promise.reject "failed"))
   (fn [x] (prn x))
   (fn [x] (prn x)))

  ((<! (io/aslurp "./package.json"))
   (fn [x] (prn x))
   (fn [x] (prn x)))

  (let [c (a/chan)]
    ((>! c "20")
     (fn [] (prn "ok"))
     (fn [] (prn "failed")))
    (a/go
      (prn ["GOT" (a/<! c)])))


  ;; ---

  (hello-macro "world")


  ;; ---

  (let [x (fs/createReadStream "./package.json")
        y (js-invoke x js/Symbol.asyncIterator)]
    y)

  (let [stream (stream/Readable.from #js ["Hello" " " "world"])]
    (loop []
      (when-let [x (.read stream)]
        (println x)
        (recur))))

  (let [x (fs/createReadStream "./package.json")
        it (js-invoke x js/Symbol.asyncIterator)]
    (.. it (next) (then (fn [x] (println "x" x))
                        (fn [e] (println "error" e)))))

  ;; TODO:
  ;; Convert iterator to missionary flow.

  ,)
