(set-env!
 :source-paths    #{"src"}
 :resource-paths  #{"resources" "src"}
 :dependencies    '[[adzerk/boot-cljs "1.7.228-1"]
                    [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                    [org.clojure/tools.nrepl "0.2.13" :scope "test"]
                    [weasel "0.7.0"  :scope "test"]
                    [org.clojure/clojurescript "1.9.671" :scope "provided"]
                    [com.cemerick/piggieback "0.2.1"  :scope "test"]
                    [pandeiro/boot-http "0.7.0" :scope "test"]
                    [degree9/boot-semver "1.7.0" :scope "test"]])


(require
 '[adzerk.boot-cljs   :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl]]
 '[pandeiro.boot-http :refer [serve]]
 '[degree9.boot-semver :refer :all])


(task-options!
 pom {:project     'bdo-labs/signalr
      :description "Light wrapper over signalR"
      :url         "https://github.com/bdo-labs/signalr-cljs"
      :scm         {:url "https://github.com/bdo-labs/signalr-cljs"}}
 repl {:middleware '[cemerick.piggieback/wrap-cljs-repl]}
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
   (cljs :ids #{"main"})
   (target)
   (build-jar)
   identity))


(deftask deploy
  "Build boot-semver and deploy to clojars."
  []
  (comp
   (release)
   (push-release)))
