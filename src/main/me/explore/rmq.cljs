(ns me.explore.rmq
  (:require
   ["amqplib" :as amq]))


(comment
  ,

  (-> (amq/connect "amqp://guest:guest@localhost:5672")
      (.then (fn [x]
               (def _conn x))))

  (js/console.dir _conn)

  (-> _conn (.-connection) (.-serverProperties))

  (.. _conn -connection -serverProperties)

  (.. _conn close)



  )
