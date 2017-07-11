(ns bdo-labs.signalr.connection
  (:require [js.signalr]))

;;;; SignalR wrapper for cljs
;;;; (enable-console-print!)

;;; Create connection
(defn connect [url]
  (let [conn (js/$.hubConnection)]
   (set! (.-url conn) url)
   conn))

;;; Print connection errors
(defn add-errors [connection]
  (let [error (.error connection)]
    (.fail error #(println error))))


(defn init [])
