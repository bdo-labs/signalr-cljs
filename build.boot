(set-env!
 :source-paths    #{"src" "test" "styles"}
 :resource-paths  #{"html" "build"}
 :dependencies    '[[adzerk/boot-cljs          "1.7.228-1"]
                    [adzerk/boot-reload        "0.4.5"]
                    [org.clojure/clojurescript "1.7.228"]
                    [pandeiro/boot-http        "0.7.0"]
                    [binaryage/devtools        "0.9.4"]
                    [powerlaces/boot-cljs-devtools "0.2.0" :scope "test"]])

(require
 '[adzerk.boot-cljs   :refer [cljs]]
 '[adzerk.boot-reload :refer [reload]]
 '[pandeiro.boot-http :refer [serve]]
 '[powerlaces.boot-cljs-devtools :refer [cljs-devtools]])

(deftask dev []
  (comp
   (watch)
   (reload :on-jsload 'frontend.dev/refresh)
   (cljs-devtools)
   (cljs :compiler-options
     {:parallel-build true 
                    :foreign-libs [
                     {:file "js/jquery-1.6.4.js"
                      :file-min "js/jquery-1.6.4.min.js"
                      :provides ["js.jquery"]}
                     {:file "js/jquery.signalR-2.2.2.js"
                      :file-min "js/jquery.signalR-2.2.2.min.js"
                      :provides ["js.signalr"]
                      :requires ["js.jquery"]} ]
                    :externs ["js/jquery-1.6.4.js" "js/jquery.signalR-2.2.2.js"]})
   (serve :port 8080)))

(deftask release []
  (comp
   (cljs :ids #{"main"} :optimizations :advanced)
   (sift :include #{#"(^index\.html|^main\.js|^styles.css)"})
   (target :dir #{"target"})))
