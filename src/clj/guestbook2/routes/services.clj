(ns guestbook2.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [guestbook2.db.core :as db]
            [guestbook2.services.disc :as disc]
            [clj-http.client :as client]
            [cheshire.core :as cheshire]))

(use 'clojure.walk)

(defn insert-numbers [params]
  (do
    (db/insert-numbers!
      params))
    (ok 1)
  )

(defn import-disc-data
  [url]

  (def discData (client/get url))
  (def jsonData (client/get url {:as :json-strict-string-keys}))

  ;(def discs2 (cheshire/parse-string (client/get url {:as :json})))

  ;(def discsParsed (cheshire/parse-string discs true))

  ;(def discs (cheshire/parse-string (str jsonData)))


  ;(str jsonData)



  (def data(get discData :body))



  ;(str jsonData)

  (def discsParsed (cheshire/parse-string data true))


  (def foo(map
    #(vec %)
    (map
      #(postwalk-replace % discsParsed) [{:missing :lost}])))

  (def foo2 (map
    #(postwalk-replace % discsParsed)
    [{:missing :is_lost, :missing_description :is_lost_description}]))

  (def discData (get (first foo2) :data))

  ;(map #(println %) foo)
  discData

  (map insert-disc-data discData)
)

(defn add-missing-key
  [coll missing-key]
  )

(defn insert-disc-data
  [data]

  (if (not (contains? data :is_lost_description))
    (conj data {:is_lost_description ""})
    data
  )

  (do
    (db/insert-disc!
      data))
    (ok 1)
  )

(defrecord Disc [id type manufacturer])
(def disc1 (Disc. 10 "putter" "Innova"))

(defn toHash
  [defRecord]
    (hash-map :id (:id defRecord) :type (:type defRecord) :manufacturer (:manufacturer defRecord))
  )

(defn return-response
  [response]
  (ok [{:data response}])
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
      :summary  "Returns a list of available discs"
      (ok [(toHash disc1)]))

    (GET "/import" []
      :summary "Imports disc data from restdb"
      (ok [{:ok (import-disc-data "http://discs.janimattiellonen.fi/api/discs")}]))

  )
)
