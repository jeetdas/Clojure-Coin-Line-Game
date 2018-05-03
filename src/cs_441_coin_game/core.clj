(ns cs-441-coin-game.core)

(require '[clojure.string :as cstr])

(def sum1 0)
(def sum2 0)

(defn readFile [fileName]
	(with-open [rdr (clojure.java.io/reader (str "/Users/jeet/Dropbox/_UMKC/SPRING 2018/CS 441/Clojure Project/" fileName ".txt"))](clojure.string/join "\n" (line-seq rdr))))

(defn convertStringToList [numbers]
	(drop 1 (map #(Integer/parseInt %)(cstr/split numbers #" "))))

(defn process [numbers evenSum oddSum]
	(println numbers evenSum oddSum))

(defn -main [& args]
	(def numbersStringList (readFile "10"))
	(def numbers (convertStringToList numbersStringList))
	(process numbers 0 0)
)