(ns guestbook2.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [guestbook2.db.core :as db]
            [guestbook2.services.disc :as disc]))

(defn insert-numbers [params]
  (do
    (db/insert-numbers!
      params))
    (ok 1)
  )

(defrecord Disc [id type manufacturer])
(def disc1 (Disc. 10 "putter" "Innova"))

(defn toHash
  [defRecord]
    (hash-map :id (:id defRecord) :type (:type defRecord) :manufacturer (:manufacturer defRecord))
  )

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}





  (context "/api" []
    :tags ["thingie"]

    (GET "/plus" []
      :return       Long
      :query-params [x :- Long, {y :- Long 1}]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok (+ x y)))

    (POST "/minus" []
      :return      Long
      :body-params [x :- Long, y :- Long]
      :summary     "x-y with body-parameters."
      (ok (- x y)))

    (GET "/times/:x/:y" []
      :return      Long
      :path-params [x :- Long, y :- Long]
      :summary     "x*y with path-parameters"
      (ok (* x y)))

    (POST "/divide" []
      :return      Double
      :form-params [x :- Long, y :- Long]
      :summary     "x/y with form-parameters"
      (ok (/ x y)))

    (GET "/power" []
      :return      Long
      :header-params [x :- Long, y :- Long]
      :summary     "x^y with header-parameters"
      (ok (long (Math/pow x y))))

    (GET "/sum" []
      :return       Long
      :query-params [val1 :- Long, {val2 :- Long 0}]
      :summary      "sums given values and returns the sum"
      (ok  (+ val1 val2)))

    (POST "/sum" []
      :return       Long
      :query-params [val1 :- Long, val2 :- Long]
      :summary      "adds two values to db"
      (insert-numbers {:val1 val1 :val2 val2}))

    (GET "/discs" []
      :summary  "echoes a Thingie from json-body"
      (ok [(toHash disc1)])))
)
