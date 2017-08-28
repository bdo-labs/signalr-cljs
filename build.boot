(set-env!
 :source-paths    #{"src"}
 :resource-paths  #{"resources" "src"}
 :dependencies    '[[adzerk/boot-cljs "1.7.228-1"]
                    [org.clojure/clojurescript "1.9.671" :scope "provided"]
                    [pandeiro/boot-http "0.7.0" :scope "test"]
                    [degree9/boot-semver "1.7.0" :scope "test"]])


(require
 '[adzerk.boot-cljs   :refer [cljs]]
 '[pandeiro.boot-http :refer [serve]]
 '[degree9.boot-semver :refer :all])


(task-options!
 pom {:project 'bdo-labs/signalr
      :description "Light wrapper over signalR"
      :url         "https://github.com/bdo-labs/signalr-cljs"
      :scm {:url "https://github.com/bdo-labs/signalr-cljs"}}
 target {:dir #{"target"}})


(deftask example []
  (set-env! :resource-paths #(conj % "html"))
  (comp
   (serve :port 8080)
   (watch)
   (cljs)))


(deftask release []
  (comp
   (version :patch 'inc)
   (cljs :ids #{"main"}
         :optimizations :simple)
   (target)
   (build-jar)
   identity))


(deftask deploy
  "Build boot-semver and deploy to clojars."
  []
  (comp
   (release)
   (push-release)))
