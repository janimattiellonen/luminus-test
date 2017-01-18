(ns guestbook2.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [guestbook2.layout :refer [error-page]]
            [guestbook2.routes.home :refer [home-routes]]
            [guestbook2.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [guestbook2.env :refer [defaults]]
            [mount.core :as mount]
            [guestbook2.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    #'service-routes
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
