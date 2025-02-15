(ns me.explore.pg
  (:require
   ["pg" :as pg :refer [Pool]]))


(comment
  ,

  (->> (js/Object.entries pg)
       (mapv first))

  ["defaults" "Client" "Query" "Pool" "_pools"
   "Connection" "types" "DatabaseError" "escapeIdentifier" "escapeLiteral"]

  (js/console.dir Pool)

  (def pool
    (Pool. #js {:host "127.0.0.1"
                :user "ridho"
                :password "pass"
                :max 20
                :idleTimeoutMillis 30000
                :connectionTimeoutMillis 2000}))

  (.end pool)

  (-> (. pool (query "SELECT version()"))
      (.then (fn [x] (println (-> (. x -rows)
                                  (js->clj :keywordize-keys true)
                                  first
                                  :version))))
      (.catch (fn [x] (js/console.error x))))

  ,)
