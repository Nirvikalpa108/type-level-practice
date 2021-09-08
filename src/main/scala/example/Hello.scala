package example

object Hello {
  def main(args: Array[String]): Unit = ???

  // 3 levels of power: map, mapN, flatMap (and traverse - although can derive from others)

  // pure function with an effect
  def map[A, B](as: List[A])(f: A => B): List[B] = ???

  // pure function of 2 arguments that depends on two effects
  // two independent effects - effect of A and effect of B
  // if they both happen successfully, we can run f
  def map2[A, B, C](as: Option[A], bs: Option[B])(f: (A, B) => C): Option[C] = ???

  // implementing with flatMap
  def map2[A, B, C](as: Option[A], bs: Option[B])(f: (A, B) => C): Option[C] = {
    for {
      a <- as
      b <- bs
    } yield f(a, b)
  }

  // product of these 2 effects and give me the right one
  def productR[A, B](as: Option[A], bs: Option[B]): Option[B] = map2(as, bs)((_,b) => b)
  // The *> operator, defined on any Apply (so, Applicative, Monad etc.), means "process the original computation,
  // and replace the result with whatever is given in the second argument"
  // Using *> then becomes a useful shortcut when dealing with delayed computation through Monix task, IO, or the like.
  // There’s also a symmetric operation, <*
  // https://blog.softwaremill.com/9-tips-about-using-cats-in-scala-you-might-want-to-know-e1bafd365f88
  def *>[A, B](as: Option[A], bs: Option[B]): Option[B] = ???

  // TODO h/w
  // product of these 2 effects and give me the left one
  def productL[A, B](as: Option[A], bs: Option[B]): Option[A] = map2(as, bs)((a,_) => a)
  def *>[A, B](as: Option[A], bs: Option[B]): Option[A] = ??? // I can't remember what this method was for!

  // we can have any number of mapN
  def map2[A, B, C](as: List[A], bs: List[B])(f: (A, B) => C): List[C] = ???
  def map3[A, B, C, D](as: Option[A], bs: Option[B], cs: Option[C])(f: (A, B, C) => D): Option[D] = ???
  // TODO h/w - implement map3 and map4 with map2
  // then explain why you can do mapN with map2
  def map4[A, B, C, D](as: Option[A], bs: Option[B], cs: Option[C])(f: (A, B, C) => D): Option[D] = ???

  // applying a dependent function
  // perform an effectful computation that depends on first effect
  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = ???

  // for comprehensions do those dependent effects line by line
  // sequence of dependent operations - each of which might fail
  // combine multiple effectful operations

  // pure function referential transparency (references all around your codebase to that function are always the same)
  def f(i: Int): Int = ???
  f(1)
  f(1)

  // traverse takes two higher kinded types instead of just one
  // about swapping effects
  def traverse[A, B](as: List[A])(f: A => Option[B]): Option[List[B]] = ???

}
