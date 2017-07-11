# signalr-cljs
[![Clojars Project](https://clojars.org/bdo-labs/signalr/latest-version.svg)](https://clojars.org/bdo-labs/signalr)

A light wrapper around Microsoft's signalR. Wraps jQuery and the signalR client library. 

## Usage
Add this to your project.clj/build.boot file:
```clojure
[bdo-labs/signalr "1.1.0"]
```

And, remember to add the foreign libs in your project.clj or build.boot file like this. 

```clojure
:foreign-libs [{:file "jquery-1.6.4.js"
                :file-min "jquery-1.6.4.min.js"
                :provides ["js.jquery"]}
               {:file "jquery.signalR-2.2.2.js"
                :file-min "jquery.signalR-2.2.2.min.js"
                :provides ["js.signalr"]
                :requires ["js.jquery"]}]
```
*Note: the path is where your compiled files is*

## Examples
Examples can be found in src/signalr/example.cljs. 

## SignalR documentation
Microsofts own documentation can be found [here](https://docs.microsoft.com/en-us/aspnet/signalr/).
