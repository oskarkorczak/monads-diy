object PointProgram {

  def createDescription(args: Array[String]): IO[Unit] = IO.create {
    args.isEmpty

    val firstIO: IO[Unit] = displayKleisli(hyphens)
    firstIO.unsafeRun()

    val secondIO: IO[Unit] = displayKleisli(question)
    secondIO.unsafeRun()

    val thirdIO: IO[String] = promptKleisli
    val input: String = thirdIO.unsafeRun()

    val integerAmount: Int = converStringToInt(input)
    val positiveAmount: Int = ensureAmountIsPositive(integerAmount)
    val balance: Int = round(positiveAmount)
    val message: String = createMessage(balance)

    val fifthIO: IO[Unit] = displayKleisli(message)
    fifthIO.unsafeRun()

    val sixthIO: IO[Unit] = displayKleisli(hyphens)
    sixthIO.unsafeRun()
  }

  private val hyphens: String = "\u2500" * 50

  private val question: String = "How much money would you like to deposit?"

  // side effect (writing to the console)
  private def displayKleisli(input: Any): IO[Unit] = IO.create {
    println(input)
  }

  private def display(input: Any): Unit = println(input)

  // side effect (reading from the console)
  private def promptKleisli: IO[String] = IO.create("5")

  private def prompt(): String = 5.toString // scala.io.StdIn.readLine

  // potential side effect (throwing of a NumberFormatException)
  private def converStringToInt(input: String): Int = input.toInt

  private def ensureAmountIsPositive(amount: Int): Int = {
    if(amount < 1)
      1
    else
      amount
  }

  @scala.annotation.tailrec
  private def round(amount: Int): Int = {
    if(isDivisibleByHundered(amount))
      amount
    else
      round(amount + 1)
  }

  private def isDivisibleByHundered(amount: Int): Boolean = {
    amount % 100 == 0
  }

  private def createMessage(balance: Int): String = {
    s"Congratulatins, you now have USD $balance."
  }
}
