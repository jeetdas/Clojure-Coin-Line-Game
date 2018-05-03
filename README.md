# Coin Toss Game

A simple 2-player game played as follows: An even number of coins is laid out in a row.
Taking turns, each player removes the coin on one of the ends of the row. The object is to have the
highest value in coins when all coins have been taken. Note that a greedy strategy of taking the largestvalue
end coin is not sufficient. Consider this situation:
5 25 10 1
In this case, the player should take the 1 on the right end; after the opponent takes either the 5 or the 10,
the player is guaranteed to get the 25. Simply taking the 5 “because it’s bigger” will result in the
biggest coin going to the other player.

## Algorithm

1. If there are an even number of coins: Find the sum of all of the even-numbered coins, and all the odd-numbered coins. If the sum of the odd numbered coins is higher, take the leftmost coin; otherwise take the rightmost.

2. If there are an odd number of coins: Picking a coin from either end can only affect the sum of the odd numbered coins, so pick the higher valued coin to minimize winning value of the other player.


## Usage

Install Lein (https://gist.github.com/technomancy/2395913)

There are two text files, one with 10 numbers to test the algorithm and one with 10,000 numbers to run the program on.

To run: 'lein run' from project directory

## License

Copyright © 2018 Jeet Das

Distributed under the Eclipse Public License either version 1.0 or any later version.
