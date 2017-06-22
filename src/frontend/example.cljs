(ns frontend.example
  (:require [devtools.core :as devtools]
            [js.signalr]
            [clojure.string :as str]
            [goog.events :as events]
            [goog.dom :as dom]
            [frontend.app :as signalr]))

;; Init
(defn init []
  (let [connection (signalr/get-connection "http://localhost:12345/signalr")]
    (let [hub-proxy (.createHubProxy connection "exampleHub")]
     (proxy-on hub-proxy)
     (connect connection hub-proxy))))

;; Proxy methods
;; To be called from server
(defn proxy-on [hub-proxy]
  (.on hub-proxy "onReceivedFromHub"
    (fn [message]
      (let [msg (.-Message message)]
        (println msg)))))

;; Connect to SignalR hub on server
(defn connect [connection hub-proxy]
  (let [conn (.start connection {:jsonp true})]
    (.done conn #(do
                   (println (str "Done: " connection.id))))
    (.fail conn #(println (str "Failed.." %)))))

;; Call hub on server
(defn call-hub [hub-proxy]
  (.invoke hub-proxy "getSomething"))
