package categoryTheory

object TryByMyself {
  def map[A, B](as: Option[A])(f: A => B): Option[B] = as match {
    case Some(a) => Some(f(a))
    case None => None
  }
  def map2[A, B, C](as: Option[A], bs: Option[B])(f: (A, B) => C): Option[C] = {
    for {
      a <- as
      b <- bs
    } yield f(a, b)
  }
  def map3[A, B, C, D](as: Option[A], bs: Option[B], cs: Option[C])(f: (A, B, C) => D): Option[D] = {
    val abs: Option[(A, B)] = map2(as, bs) { (a, b) =>
      (a, b)
    }
    map2(abs, cs) { case ((a, b), c) =>
      f(a, b, c)
    }
  }
  def map4[A, B, C, D, E](as: Option[A], bs: Option[B], cs: Option[C], ds: Option[D])(f: (A, B, C, D) => E): Option[E] = {
    val abs: Option[(A, B)] = map2(as, bs)((a, b) => (a, b))
    val abcs: Option[(A, B, C)] = map2(abs, cs){ case ((a,b),c) =>
      (a,b,c)
    }
    map2(abcs, ds) { case ((a,b,c),d) =>
      f(a,b,c,d)

    }
  }

  // a list of booleans that returns a list of booleans
  def bool(bs: List[Boolean]): List[Boolean] = bs.map(_.booleanValue)
  // a type constructor that takes a Boolean as it's parameter
  // conjure implicitly the Functor for F and call map on it (we know it's there, we don't have it, use apply method to get an instance of Functor)
  // or we could use the keyword implicitly ourselves (like the apply does) but it's not conventional
  def generalBool[F[_] : Functor](bs: F[Boolean]): F[Boolean] = Functor[F].map(bs)(_.booleanValue)
  // now using an implicit parameter
  def generalBoolAgain[F[_]](bs: F[Boolean])(implicit f: Functor[F]): F[Boolean] = f.map(bs)(_.booleanValue)

  // takes a list of ints and returns a list of ints
  def add(is: List[Int]): List[Int] = is.map(_ + 1)
  // takes a type constructor with int as parameter
  // conjuring implicitly the Functor for F
  def generalAdd[F[_] : Functor](is: F[Int]): F[Int] = Functor[F].map(is)(_ + 1)
  // now using implicit parameter
  def generalAddAgain[F[_]](is: F[Int])(implicit f: Functor[F]): F[Int] = f.map(is)(_ + 1)

  // takes a list of as and an a, returns a boolean
  def containsList[A](as: List[A], a: A): Boolean = as.contains(a)
  // take option of a and a, return bool
  def containsOption[A](as: Option[A], a: A): Boolean = as.contains(a)
  // take a type constructor of type a, an a and return bool
  //TODO - do i need to define a contains method on Functor??! NO - coz then it wouldnt be a functor
  def containsGeneral[F[_] : Functor, A](as: F[A], a: A): Boolean = ???
}
