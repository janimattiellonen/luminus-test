(ns user
  (:require [mount.core :as mount]
            guestbook2.core))

(defn start []
  (mount/start-without #'guestbook2.core/http-server
                       #'guestbook2.core/repl-server))

(defn stop []
  (mount/stop-except #'guestbook2.core/http-server
                     #'guestbook2.core/repl-server))

(defn restart []
  (stop)
  (start))


