(ns bdo-labs.signalr.connection
  (:require [js.signalr]))


(defn connect
  "Connect to the socket on [url]"
  [url]
  (let [conn (js/$.hubConnection)]
   (set! (.-url conn) url)
   conn))


(defn add-errors
  "Log all [connection] errors"
  [connection]
  (let [error (.error connection)]
    (.fail error #(.error js/console error))))
