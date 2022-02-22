package categoryTheory

object LearningQs {

// What is an effect?
  // something we want to try to do?? The value/action held in the monad??
  // **** Adam says this is hard to define, it's purposefully abstract. John de Goes coined it - see his conference talk.

// What is the type signature for flatmap?
  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = ???

// Explain flatMap in terms of effects.
  // Takes dependent effects. Uses the result of a given effect, to generate the next.

// What is a pure function?
  // It depends only on its declared inputs and its internal algorithm to produce its output.
  // so whenever you give it the same input, it will produce the same result.
  // https://docs.scala-lang.org/overviews/scala-book/pure-functions.html

// What is referential transparency?
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

  def map2[A, B, C](as: Option[A], bs: Option[B])(f: (A, B) => C): Option[C] = {
    for {
      a <- as
      b <- bs
    } yield f(a, b)
  }

// Define map2 in terms of effects ***
  // map2 takes 2 independent effects and creates a new effect from them.

// Implement map3 using map2
   def map3[A, B, C, D](as: List[A], bs: List[B], cs: List[C])(f: (A, B, C) => D): List[D] = {
     val abs = map2(as, bs) { (a, b) =>
       (a, b)
     }
     map2(abs, cs) { case ((a, b), c) =>
       f(a, b, c)
     }
   }

// Implement productR using map2
  def productR[A, B](as: Option[A], bs: Option[B]): Option[B] = map2(as, bs)((_,b) => b)

// What is *>?
  // it's the same as productR

// Define traverse
  def traverse[A, B](as: List[A])(f: A => Option[B]): List[Option[B]] = ???

// What is a type constructor?
  // a List, a Map, an Option, an Either, a Future etc
  // also called first order types
  // https://medium.com/bigpanda-engineering/understanding-f-in-scala-4bec5996761f

// What is a higher kinded type?
  // higher kinded types take type constructors
  // they are an abstraction over type constructors

// What is a typeclass?
// What is the purpose of an instances object?
// What is a functor?
// Write a functor typeclass with an abstract map function
// Implement a List Functor
// What is an applicative?
// Write out the applicative typeclass.
// Implement applicatives of types List, Set and Option.
// What is a monad?
// Write out the monad typeclass.
// Implement monads of types List, Set and Option.
// What is subtype polymorphism?
// What is adhoc polymorphism?
// functor - pure / applicative - independent (pure combo of 2 indepdt) / monad - dependent

}
