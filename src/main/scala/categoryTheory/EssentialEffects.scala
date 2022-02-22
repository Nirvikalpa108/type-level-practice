package categoryTheory

import cats.implicits._

object EssentialEffects {
  // Functor - the ability to map
  def map[A, B](as: List[A])(f: A => B): List[B] = ???
  // Functor also has the as and void methods
  //TODO how do I get this to compile?
  val fa: F[A] = ???
  // as ignores the value produced by the Functor and replaces it with a provided value
  val replaced: F[String] = fa.as("replacement")
  // void also ignores the value produced by the Functor, and replaces it with ()
  val voided: F[Unit] = fa.void

  // Applicatives - an applicative functor, also known as an applicative,
  // is a functor that can transform multiple structures, not just one.
  // mapN

  // Monads - mechanism for sequencing computations. Provides a flatMap method.
}
