package example

object LearningQs {

// What is an effect? ****
  // something we want to try to do?? The value/action held in the monad??
  // man, i don't know how to define this.

// What is the type signature for flatmap?
  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = ???

// Explain flatMap in terms of effects.
  // Takes dependent effects. Uses the result of a given effect, to generate the next.

// What is a pure function?
  // It depends only on its declared inputs and its internal algorithm to produce its output.
  // so whenever you give it the same input, it will produce the same result.
  // https://docs.scala-lang.org/overviews/scala-book/pure-functions.html
  // Referential transparency holds true for pure functions.
  // The top stackoverflow answer here is unreal, soooo amazing:
  // https://stackoverflow.com/questions/210835/what-is-referential-transparency

// write an example of a pure function.
  def examplePureFunction(i: Int): Int = i + 1

// Implement map2 with flatMap
  def map2[A, B, C](as: List[A], bs: List[B])(f: (A, B) => C): List[C] = {
    for {
      a <- as
      b <- bs
    } yield f(a, b)
  }

// Define map2 in terms of effects ***
  // map2 takes the result of 2 dependent effects and runs an effect on them.
  // but in our notes it says the 2 parameters are independent??

//    Implement map3 using map2
//    What is productR?
//    What is *>?
//    What is referential transparency?
//    Define traverse
//    What is a type constructor?
//    What is a higher kinded type?
//    What is a typeclass?
//    What is the purpose of an instances object?
//    What is a functor?
//    Write a functor typeclass with an abstract map function
//    Implement a List Functor
//    What is an applicative?
//    Write out the applicative typeclass.
//    Implement applicatives of types List, Set and Option.
//    What is a monad?
//    Write out the monad typeclass.
//    Implement monads of types List, Set and Option.

}
