(set-env!
 :source-paths    #{"src"}
 :resource-paths  #{"html" "resources"}
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
 target {:dir #{"target"}})


(deftask dev []
  (comp
   (watch)
   (cljs :ids #{"example" "main"}
         :optimizations :simple)
   (serve :port 8080)))


(deftask release []
  (comp
   (cljs :ids #{"main"}
         :optimizations :simple)
   (version :patch 'inc)
   (target)
   (build-jar)
   identity))

(deftask deploy
  "Build boot-semver and deploy to clojars."
  []
  (comp
   (release)
   (push-release)))
   
  
