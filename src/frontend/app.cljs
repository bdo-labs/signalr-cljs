(ns frontend.app
  (:require [devtools.core :as devtools]
            [js.signalr]
            [clojure.string :as str]))

;; Create and expose connection
(defn get-connection [url]
  (let [connection (js/$.hubConnection)]
   (set! (.-url connection) url)
   connection))
