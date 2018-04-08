package recfun

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
      val parentheses = chars.toStream.filter(c => c == '(' || c == ')').toList

      def newCount(openCount: Int, char: Char): Int = {
        if (char == '(') openCount + 1 else if (char == ')') openCount - 1 else openCount
      }

      def balanceWithCount(parentheses: List[Char], openCount: Int): Boolean = {
        if (parentheses.isEmpty) openCount == 0
        else if (openCount < 0) false
        else balanceWithCount(parentheses.tail, newCount(openCount, parentheses.head))
      }

      balanceWithCount(parentheses, 0)
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
