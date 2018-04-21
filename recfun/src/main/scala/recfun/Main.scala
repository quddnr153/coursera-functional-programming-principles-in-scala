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
    val InitialOpenCount = 0
    val parentheses = chars filter (c => c == '(' || c == ')')

    def calculateOpens(numberOfOpen: Int, char: Char): Int = char match {
      case '(' => numberOfOpen + 1
      case ')' => numberOfOpen - 1
    }

    @tailrec
    def iterate(parentheses: List[Char], numberOfOpen: Int): Boolean = {
      if (parentheses.isEmpty) numberOfOpen == 0
      else if (numberOfOpen < 0) false
      else iterate(parentheses.tail, calculateOpens(numberOfOpen, parentheses.head))
    }

    iterate(parentheses, InitialOpenCount)
  }
  
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) 1
    else if (money < 0 || coins.isEmpty) 0
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
