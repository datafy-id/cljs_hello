(ns me.app
  (:require
   ["date-fns" :as df]
   ["date-fns-tz" :as dfz]
   ["date-fns/locale/id" :rename {id locale-id}]

   ))

(defn ^:export hello []
  "Hello world!")

(comment
  (def date (js/Date. "2025-02-12T08:00:00Z"))

  (dfz/formatInTimeZone date "Asia/Jakarta" "yyyy-MM-dd HH:mm:ssXXX")
  (dfz/formatInTimeZone date "+08" "yyyy-MM-dd HH:mm:ssXXX")
  (dfz/formatInTimeZone date "UTC" "yyyy-MM-dd HH:mm:ssXXX")

  (dfz/formatInTimeZone date "+07" "yyyy-MM-dd HH:mm:ss zzzz"
                        #js {:locale locale-id})

  (dfz/formatInTimeZone date "Asia/Jakarta" "yyyy-MM-dd HH:mm:ss zzzz"
                        #js {:locale locale-id})


  ;; `fromZonedTime ignore any zone, treat it as system tz

  (dfz/fromZonedTime "2025-02-12T08:00" "+07") ;; interpreted as in +7 then converted to UTC



  )
