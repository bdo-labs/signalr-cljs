(set-env!
 :source-paths    #{"src"}
 :resource-paths  #{"src" "html" "assets"}
 :dependencies    '[[adzerk/boot-cljs              "1.7.228-1"]
                    [org.clojure/clojurescript     "1.9.671"]
                    [pandeiro/boot-http            "0.7.0"]
                    [degree9/boot-semver           "1.6.0"    :scope "test"]])

(require
 '[adzerk.boot-cljs   :refer [cljs]]
 '[pandeiro.boot-http :refer [serve]]
 '[degree9.boot-semver :refer :all])

(task-options!
 pom {:project 'bdo-labs/signalr
      :description "Light wrapper over signalR."
      :url         "https://github.com/bdo-labs/signalr-cljs"
      :scm {:url "https://github.com/bdo-labs/signalr-cljs"}}
 cljs {:compiler-options 
       {:parallel-build true
        :foreign-libs [{:file "js/jquery-1.6.4.js"
                        :file-min "js/jquery-1.6.4.min.js"
                        :provides ["js.jquery"]}
                       {:file "js/jquery.signalR-2.2.2.js"
                        :file-min "js/jquery.signalR-2.2.2.min.js"
                        :provides ["js.signalr"]
                        :requires ["js.jquery"]}]
        :externs ["js/jquery-1.6.4.js" "js/jquery.signalR-2.2.2.js"]}}
 target {:dir #{"target"}})


(deftask dev []
  (comp
   (watch)
   (cljs :ids #{"example"})
   (serve :port 8080)))


(deftask release []
  (comp
   (cljs :ids #{"main"}
         :optimizations :whitespace)
   (target)
   identity))

(deftask deploy
  "Build boot-semver and deploy to clojars."
  []
  (comp
   (version :minor 'inc)
   (release)
   (build-jar)
   (push-release)))