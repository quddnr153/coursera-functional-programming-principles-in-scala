package recfun

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) return 1
    pascal(c - 1, r - 1) + pascal(c, r - 1)
  }
  
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    val INITIAL_OPEN_COUNT = 0
    val parentheses = chars filter (c => c == '(' || c == ')')

    def calculateOpens(numberOfOpen: Int, char: Char): Int = {
      if (char == '(') numberOfOpen + 1 else if (char == ')') numberOfOpen - 1 else numberOfOpen
    }

    @tailrec
    def iterate(parentheses: List[Char], numberOfOpen: Int): Boolean = {
      if (parentheses.isEmpty) numberOfOpen == 0
      else if (numberOfOpen < 0) false
      else iterate(parentheses.tail, calculateOpens(numberOfOpen, parentheses.head))
    }

    iterate(parentheses, INITIAL_OPEN_COUNT)
  }
  
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def count(money: Int, sortedCoins: List[Int]) : Int = {
      if (sortedCoins.isEmpty) 0
      else if (money - sortedCoins.head == 0) 1
      else if (money - sortedCoins.head < 0) 0
      else countChange(money - sortedCoins.head, sortedCoins) + countChange(money, sortedCoins.tail)
    }
    count(money, coins.sorted)
  }
}
