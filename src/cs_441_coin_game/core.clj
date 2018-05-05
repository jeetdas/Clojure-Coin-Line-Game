(ns cs-441-coin-game.core)

(require '[clojure.string :as cstr])

(def player1Total 0)
(def player2Total 0)

(defn readFile [fileName]
	;; Function accepts a file path, reads in contents and returns it as a string
	(with-open [rdr (clojure.java.io/reader (str fileName))](clojure.string/join "\n" (line-seq rdr)))
)

(defn convertStringToList [numbers]
	;; Function accepts a string of numbers separated by spaces and returns a list of integers
	(drop 1 (map #(Integer/parseInt %)(cstr/split numbers #" ")))
)

(declare processOddNumbers)
(declare processOddNumbersP2)

;; Current implementation
;; ------------------------------------------------------------------------------------------------------------------
;; 1. 

(defn processEvenNumbersP1 [numbers]
	;; Method expects a list of even size and applies even-odd sum strategy
	(def evenSum (future (reduce + (vec (take-nth 2 (rest numbers))))))
	(def oddSum (future (reduce + (vec (take-nth 2 (vec numbers))))))

	(if (> @evenSum @oddSum)
		1
		0
	)
)

(defn processEvenNumbersP2 [numbers]
	;; Method expects a list of even size and returns the sum of how much this current state would result in
	(def winValue (processEvenNumbersP1 numbers))
	(if (= winValue 0)
		(+ (first numbers) (processOddNumbersP2 (drop 1 numbers)) )
		(+ (last numbers) (processOddNumbersP2 (drop-last numbers)) )
	)
)

(defn processOddNumbersP2 [numbers]
	;; Method expects a list of odd size and APPLIES branching strategy to it
	(if (>= 1 (count numbers))
		0
		(do
			(def val1 (future (processEvenNumbersP2 (drop 1 numbers))))
			(def val2 (future (processEvenNumbersP2 (drop-last numbers))))
			(max @val1 @val2)
		)
	)
)

(defn processOddNumbers[numbers]
	;; Method expects a list of odd size and STARTS branching strategy to it
	(if (>= 1 (count numbers))
		0
		(do
			(def lval (future (processEvenNumbersP2 (drop 1 numbers))) )
			(def rval (future (processEvenNumbersP2 (drop-last numbers))) )
			(if (> @lval @rval)
				1
				0
			)
		)
	)
	
)

(defn handlerMethod [numbers]
	;; Function handler that separtes out player 1 and 2
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

(defn playGame [numbers]
	;; Simulates game between two players
	(if (= 0 (count numbers))
		(do
			(println "Game over")
			(println "Player 1 total =" player1Total)
			(println "Player 2 total =" player2Total)
			(System/exit 0)
		)
		(do
			(def player1Move (handlerMethod numbers))
			(println "Player 1 move:" (if (= player1Move 0) (first numbers) (last numbers) ))
			(def player1Total (+ player1Total (if (= player1Move 0) (first numbers) (last numbers)) ))
			(def updatedNumbers (if (= player1Move 0) (drop 1 numbers) (drop-last numbers) ))
			(println "After player 1 move:" updatedNumbers)

			(def player2Move (handlerMethod updatedNumbers))
			(println "Player 2 move:" (if (= player2Move 0) (first updatedNumbers) (last updatedNumbers) ))
			(def player2Total (+ player2Total (if (= player2Move 0) (first updatedNumbers) (last updatedNumbers)) ))
			(def updatedNumbers2 (if (= player2Move 0) (drop 1 updatedNumbers) (drop-last updatedNumbers) ))
			(println "After player 2 move:" updatedNumbers2)

			(playGame updatedNumbers2)
		)
	)
)

(defn -main [& args]
	(def numbersStringList (readFile (first *command-line-args*)))
	(def numbers (convertStringToList numbersStringList))
	(println "Coins on the table:" numbers)
	(playGame numbers)
)