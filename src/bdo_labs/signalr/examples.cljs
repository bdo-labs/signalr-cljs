(ns bdo-labs.signalr.examples
  (:require [clojure.string :as str]
            [bdo-labs.signalr.connection :as signalr]))


(enable-console-print!)


;; Proxy methods
;; To be called from server
(defn proxy-on [hub-proxy]
  (.on hub-proxy "getSomething"
    (fn [message]
      (let [msg (.-Message message)]
        (println msg)))))


;; Connect to SignalR hub on server
(defn connect [connection hub-proxy]
  (let [conn (.start connection {:jsonp true})]
    (.done conn #(do
                   (.log js/console (str "Done: " connection.id))))
    (.fail conn #(.log js/console (str "Failed.." %)))))


;; Call hub on server
(defn call-hub [hub-proxy]
  (.invoke hub-proxy "getSomething"))


;; Init
(defn init []
  (let [connection (signalr/connect "http://localhost:20972/signalr")]
    (let [hub-proxy (.createHubProxy connection "notificationHub")]
     (proxy-on hub-proxy)
     (connect connection hub-proxy))))
