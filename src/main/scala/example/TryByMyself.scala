package example

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
}
