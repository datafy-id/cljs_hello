(ns me.app
  (:require
   [uix.core :refer [defui $]]
   [uix.dom]
   ["date-fns" :as df]
   ["date-fns-tz" :as dfz]
   ["date-fns/locale/id" :rename {id locale-id}]
   ))

(defn ^:export hello []
  "Hello world!")

(defui button [{:keys [on-click children]}]
  ($ :button.btn.btn-primary {:on-click on-click} children))

(defui app []
  (let [[state set-state!] (uix.core/use-state 0)]
    ($ :div.border.border-red-500.flex.gap-2.items-center.justify-center.p-4.m-4.rounded-md
       ($ button {:on-click #(set-state! dec)} "-")
       ($ :div.min-w-8.text-center state)
       ($ button {:on-click #(set-state! inc)} "+"))))

(defonce root
  (uix.dom/create-root (js/document.getElementById "app")))

(uix.dom/render-root ($ app) root)


(comment
  (def date (js/Date. "2025-02-12T08:00:00Z"))

  (js/Object.keys df)
  (-> (df/constructNow) (df/addDays 7))

  (dfz/formatInTimeZone date "Asia/Jakarta" "yyyy-MM-dd HH:mm:ssXXX")
  (dfz/formatInTimeZone date "+08" "yyyy-MM-dd HH:mm:ssXXX")
  (dfz/formatInTimeZone date "UTC" "yyyy-MM-dd HH:mm:ssXXX")

  (dfz/formatInTimeZone date "+07" "yyyy-MM-dd HH:mm:ss zzzz"
                        #js {:locale locale-id})

  (dfz/formatInTimeZone date "Asia/Jakarta" "yyyy-MM-dd HH:mm:ss zzzz"
                        #js {:locale locale-id})


  ;; `fromZonedTime ignore any zone, treat it as system tz

  (dfz/fromZonedTime "2025-02-12T08:00" "+07") ;; interpreted as in +7 then converted to UTC

  ,)
