(ns cs-441-coin-game.core)

(require '[clojure.string :as cstr])

(def sum1 0)
(def sum2 0)

(defn readFile [fileName]
	(with-open [rdr (clojure.java.io/reader (str "/Users/jeet/Workspace/Clojure_projects/cs-441-coin-game/resources/" fileName ".txt"))](clojure.string/join "\n" (line-seq rdr)))
)

(defn convertStringToList [numbers]
	(drop 1 (map #(Integer/parseInt %)(cstr/split numbers #" ")))
)

(declare processOddNumbers)
(declare processOddNumbersP2)

;; Current implementation
;; ------------------------------------------------------------------------------------------------------------------

(defn processEvenNumbersP1 [numbers]
	;;(println "EVEN NUMBERS FUN = " numbers)
	(def evenSum (reduce + (vec (take-nth 2 (rest numbers)))))
	(def oddSum (reduce + (vec (take-nth 2 (vec numbers)))))

	;;(println oddSum evenSum numbers)

	(if (> evenSum oddSum)
		(do
			;;(println "Winner = " (last numbers))
			1
		)
		(do
			;;(println "Winner = " (first numbers))
			0
		)
	)
)

(defn processEvenNumbersP2 [numbers]
	(def winValue (processEvenNumbersP1 numbers))
	(if (= winValue 0)
		(+ (first numbers) (processOddNumbersP2 (drop 1 numbers)) )
		(+ (last numbers) (processOddNumbersP2 (drop-last numbers)) )
	)
)

(defn processOddNumbersP2 [numbers]
	(if (<= 1 (count numbers))
		0
		(do
			(max (processEvenNumbersP2 (drop 1 numbers)) (processEvenNumbersP2 (drop-last numbers)) )
		)
	)
)

(defn processOddNumbers[numbers]
	(def lval (processEvenNumbersP2 (drop 1 numbers)) )
	(def rval (processEvenNumbersP2 (drop-last numbers)) )
	(if (> lval rval)
		(last numbers)
		(first numbers)
	)
)

(defn handlerMethod [numbers]
	(if (even? (count numbers))
		(do
			(def res (processEvenNumbersP1 numbers))
			res
		)
		(do
			(def res (processOddNumbers numbers))
			res
		)
	)
)

(defn -main [& args]
	(def numbersStringList (readFile 10))
	;;(def numbers (convertStringToList numbersStringList))
	(def numbers '(1 2 3 6 5 4))
	(def evenSum (reduce + (vec (take-nth 2 (rest numbers)))))
	(def oddSum (reduce + (vec (take-nth 2 (vec numbers)))))
	(println "PICK" (handlerMethod '(71 83 6 37 35 94 1 11 8)) "GIVEN (71 83 6 37 35 94 1 11 8)")
	;;(println (process2 numbers evenSum oddSum 0))
	;;(process numbers evenSum oddSum)
	;;(println sum1)

)