package categoryTheory

object Typeclasses {
  def main(args: Array[String]): Unit = {
    import Instances._
    println(add(List(1,2,3)))
    println(generalAdd(List(3,4,5)))
    println(generalAdd(Set(3,4,5)))
    println(generalAdd[Option](Some(2)))
    println(generalCapitalise(List("winning"), listFunctor)) // need to give it the list functor instance
    println(generalCapitaliseImplicit(List("winning"))) // nice we dont need to state the implicit
  }

  def add(is: List[Int]): List[Int] = is.map(_ + 1)
  // a type constructor that takes an Int as its parameter

  // the higher kinded type is a Functor
  // conjure implicitly the functor for F and call map on it
  def generalAdd[F[_] : Functor](is: F[Int]): F[Int] = Functor[F].map(is)(_ + 1)
  // recommended way
  def generalAdd[F[_]](is: F[Int])(implicit f: Functor[F]): F[Int] = f.map(is)(_ + 1)
  // expressing things not in what they are, but what they can do
  // functor - ability to map

  //effect of multiplicty
  def capitaliseList(words: List[String]): List[String] = words.map(_.capitalize)
  //effect of might not exist
  def capitaliseOption(word: Option[String]): Option[String] = word.map(_.capitalize)
  //abstracting over type constructor / the effect
  //taking data struct and functor instance
  //F can be any HKT, we constrain it with the f arg
  def generalCapitalise[F[_]](ws: F[String], f: Functor[F]): F[String] = f.map(ws)(_.capitalize)
  def generalCapitaliseImplicit[F[_]](ws: F[String])(implicit f: Functor[F]): F[String] = f.map(ws)(_.capitalize)
  // use the colon and the implicitly key word (or, more idiomatically, apply method in companion object)
  def generalCapitaliseImplicitDifferently[F[_] : Functor](ws: F[String]): F[String] = Functor[F].map(ws)(_.capitalize)

  //h/w boolean method - not looking
  //h/w contains
  def containsList[A](as: List[A], a: A): Boolean = ???
  def containsOption[A](as: Option[A], a: A): Boolean = ???
  def containsGeneral[F[_], A](as: F[A], a: A): Boolean = ???
}

// functor gives us map and anything we can build using map (not flatMap, not map2 etc)
trait Functor[F[_]] {
  def map[A, B](i: F[A])(f: A => B): F[B]

  def lift[A, B](f: A => B): F[A] => F[B] = { fa =>
    map(fa)(f)
  }
  def liftAgain[A, B](f: A => B): F[A] => F[B] = map(_)(f)
}
// this apply method conjures an instance for us, so we dont have to use implicitly everywhere
object Functor {
  def apply[F[_] : Functor] = implicitly[Functor[F]]
}

// map2 and flatMap are really similar, but there is a difference:
// map2 for 2 independent things - if you want to run in parallel, use this over flatMap
// flatMap for 2 dependent things - use the result of first effect to run the next one
// applicative is not that useful, but might see it in fs2
trait Applicative[F[_]] {
  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C]
  def pure[A](a: A): F[A] // put a value in an effect like Some, Right, List etc

  //derived methods (implemented with map2 or pure)
  def map[A, B](as: F[A])(f: A => B): F[B] = {
    map2(as, unit)((a, _) => f(a))
  }
  def unit: F[Unit] = pure(())
  def productR[A, B](fa: F[A], fb: F[B]): F[B] = map2(fa, fb){ (_, b) =>
    b
  }
  def *>[A, B](fa: F[A], fb: F[B]): F[B] = map2(fa, fb){ (_, b) =>
    b
  }
  def productL[A, B](fa: F[A], fb: F[B]): F[A] =  ???
  def <*[A, B](fa: F[A], fb: F[B]): F[A] =  ???
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] = map2(fa, fb) { (a, b) =>
    (a, b)
  }
  // this is map3 but we call it mapN
  def mapN[A, B, C, D](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = {
    val fab = map2(fa, fb){ (a, b) =>
      (a, b)
    }
    map2(fab, fc) { case ((a, b), c) =>
      f(a, b, c)
    }
  }
  // map4 but we use mapN - the compiler can work it out
  def mapN[A, B, C, D, E](fa: F[A], fb: F[B], fc: F[C], fd: F[D])(f: (A, B, C, D) => E): F[E] = ???
  def map3Again[A, B, C, D](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = {
    map2(product(fa, fb), fc) { case ((a, b), c) =>
      f(a, b, c)
    }
  }
}

// if you have a monad, you have an applicative and if you have an applicative, you have a Functor
// therefore if you have a monad, you have a functor
// in cats monad extends applicative and applicative extends Functor
trait Monad[F[_]] {
  // dependent on A - f performs an effectful computation on A
  // the ability to make an F[B] depends on A
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  def pure[A](a: A): F[A]
}

object Instances {
  implicit val listFunctor: Functor[List] = new Functor[List] {
    override def map[A, B](as: List[A])(f: A => B): List[B] = as.map(f)
  }
  implicit val setFunctor: Functor[Set] = new Functor[Set] {
    override def map[A, B](as: Set[A])(f: A => B): Set[B] = as.map(f)
  }
  implicit val optionFunctor: Functor[Option] = new Functor[Option] {
    override def map[A, B](as: Option[A])(f: A => B): Option[B] = as.map(f)
  }
  implicit val listApplicative: Applicative[List] = new Applicative[List] {
    override def map2[A, B, C](fa: List[A], fb: List[B])(f: (A, B) => C): List[C] = {
     for {
       a <- fa
       b <- fb
     } yield f(a, b)
    }

    override def pure[A](a: A): List[A] = List(a)
  }
  implicit val setApplicative: Applicative[Set] = new Applicative[Set] {
    override def map2[A, B, C](fa: Set[A], fb: Set[B])(f: (A, B) => C): Set[C] = {
      for {
        a <- fa
        b <- fb
      } yield f(a, b)
    }

    override def pure[A](a: A): Set[A] = Set(a)
  }
  implicit val optionApplicative: Applicative[Option] = new Applicative[Option] {
    override def map2[A, B, C](fa: Option[A], fb: Option[B])(f: (A, B) => C): Option[C] = {
      for {
        a <- fa
        b <- fb
      } yield f(a, b)
    }

    override def pure[A](a: A): Option[A] = Some(a)
  }
  // come back to this one bygone day - watch second half of that dude monty's video
//  implicit def functorFromApplicative[F[_]](implicit af: Applicative[F]): Functor[F] = {
//    new Functor[F] {
//      override def map[A, B](fa: F[A])(f: A => B): F[B] = {
//        af.map2(fa, af.unit)((a, _) => f(a))
//      }
//    }
//  }
}
