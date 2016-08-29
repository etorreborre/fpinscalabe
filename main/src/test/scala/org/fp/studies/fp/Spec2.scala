package org.fp.studies.fp

import org.fp.concepts._
import org.fp.resources._
import org.fp.bookmarks._

//
import org.specs2.specification.dsl.mutable.{AutoExamples, TextDsl}

/**
  * [[disjunction]] in [[Scalaz]]
  *
  */
object Spec2 extends org.specs2.mutable.Specification with AutoExamples with TextDsl {

  s"$bookmarks: $ann_ScalazDisjunction"
  s2"""$keyPoint Disjunction - aka ${Scalaz.md} Either `\/[A, B]` is an alternative to `Either[A, B]`.
     |
       | * `-\/` is Left  (usually represents failure by convention)
     | * `\/-` is Right (usually represents success by convention)
     |
       |Left or Right - which side of the $disjunction does the "-" appear?
     |
       |Prefer infix notation to express $disjunction type `v: String \/ Double`
     |
       |References
     |- http://eed3si9n.com/learning-scalaz/Either.html @todo
     |
       |A common use of a $disjunction is to explicitly represent the possibility of failure in a result as opposed to throwing an
     |exception. By convention, the `Left -\/` is used for errors and the right `\/-` is reserved for successes.
     |
       |For example, a function that attempts to parse an integer from a string may have a return type of `\/[NumberFormatException, Int]`.
     |
       |However, since there is no need to actually throw an exception, the type `A` chosen for the "left" could be any type representing
     |an error and has no need to actually extend Exception
     |
       |`\/[A, B]` is isomorphic to `scala.Either[A, B]`, but `\/` is is right-biased, so methods such as `$operator_map` and `$operator_flatMap`
     |apply only in the context of the "right" case. This right bias makes `\/` more convenient to use than `scala.Either` in a $monadicContext.
     |
       |
       |Methods such as $operator_swap, $operator_swapped, and $operator_leftMap provide functionality that `scala.Either` exposes through $leftProjection.
     |
       |`\/[A, B]` is also isomorphic to `Validation[A, B]`. The subtle but important difference is that $applicativeFunctor instances for `Validation`
     |accumulates errors ("lefts") while $applyFunctor instances for `\/` "fail fast" on the first "left" they evaluate.
     |
       |This fail-fast behavior allows `\/` to have lawful $monad instances that are consistent with their $applicativeFunctor instances, while `Validation` cannot.
     |
       """.stripMargin.p

  s2"""Builders:
       |- using the disjunction singleton instances \/- and -\/
       |- left method
       |- right method
       |- fromEither method
       |""".stripMargin.p
  eg {
    import scalaz.{\/, -\/, \/-}

    \/-("right") must beAnInstanceOf[\/-[String]]
    \/-("right") must_== \/-("right")

    \/.right("right") must beAnInstanceOf[Nothing \/ String]
    \/.right("right") must_== \/-("right")

    -\/("left")  must beAnInstanceOf[-\/[String]]
    -\/("left")  must_== -\/("left")

    \/.left("left")   must beAnInstanceOf[String \/ Nothing]
    \/.left("left")   must_== -\/("left")
  }

  s2"""There is an isomorphism between `Either[A, B]` and `\/[A, B]`.""".p
  s2"""From `\/[A, B]` to `Either[A, B]`.""".p
  eq {
    import scalaz.{\/, -\/, \/-}

    val e_right = \/.fromEither(Right("right"))

    e_right must beAnInstanceOf[Nothing \/ String]
    e_right must_== \/-("right")

    e_right.toEither must beAnInstanceOf[Either[Nothing, String]]
    e_right.toEither must_== Right("right")

    val e_left = \/.fromEither(Left("left"))

    e_left must beAnInstanceOf[String \/ Nothing]
    e_left must_== -\/("left")

    e_left.toEither must beAnInstanceOf[Either[String, Nothing]]
    e_left.toEither must_== Left("left")
  }

  s2"""From `Either[A, B]` to `\/[A, B]`.""".p
  eg {
    import scalaz.{\/, -\/, \/-}
    import scalaz.syntax.std.either._

    Left("left").disjunction must beAnInstanceOf[String \/ Nothing]
    Left("left").disjunction must_== -\/("left")

    Right("right").disjunction must beAnInstanceOf[Nothing \/ String]
    Right("right").disjunction must_== \/-("right")
  }

  s2"""From `Try[A]` to `\/[Throwable, A]`.""".p
  eg {
    import scalaz.{\/, -\/}

    val r_excp: RuntimeException = new scala.RuntimeException("runtime error")
    val e1 = \/.fromTryCatchNonFatal[Int](throw r_excp)

    e1 must beAnInstanceOf[Throwable \/ Int]
    e1 must_== -\/(r_excp)

    val excp = new java.lang.ArithmeticException()
    def div(a: Int, b: Int) : Int = b match {
      case 0 => throw excp
      case _ => a / b
    }

    val e2 = \/.fromTryCatchNonFatal[Int](div(1, 0))

    e2 must beAnInstanceOf[Throwable \/ Int]
    e2 must beEqualTo(-\/(excp))
  }

  s2"""Implicit conversion to `\/[A, Nothing]` and `\/[A, B]`.""".p
  eg {
    import scalaz.{\/, -\/, \/-}
    import scalaz.syntax.either._

    "right".right must beAnInstanceOf[Nothing \/ String]
    "right".right must_== \/-("right")

    "left".left must beAnInstanceOf[String \/ Nothing]
    "left".left must_== -\/("left")

    "right".right[Int] must beAnInstanceOf[Int \/ String]
    "right".right[Int] must_== \/-("right")

    "left".left[Double] must beAnInstanceOf[String \/ Double]
    "left".left[Double] must_== -\/("left")
  }

  s2"""Use of `\/[A, B]` in $forComprehension-s.""".p
  eg {
    import scalaz.{\/, -\/, \/-}
    import scalaz.syntax.either._

    val fc1 = for {
      a <- "a".right[String]
      b <- "b".right[String]

    } yield (a, b)

    fc1 must beAnInstanceOf[String \/ (String, String)]
    fc1 must_== \/-(("a", "b"))

    val fc2 = for {
      a <- "a".right[String]
      e <- "e".left[Int]
    } yield (a, e)

    fc2 must beAnInstanceOf[String \/ (String, Int)]
    fc2 must_== -\/("e")

    val fc3 = for {
      a <- "a".right[String]
      e <- "e".left[Int]
      f <- "f".left[Int]
    } yield (a, e, f)

    fc3 must beAnInstanceOf[String \/ (String, Int, Int)]
    fc3 must_== -\/("e")
  }

  s2"""From `Option[A]` to `\/[E, A]`
       |`\/>[E]`
       |`toRightDisjunction[E](e: => E): E \/ A = o.toRight(self)(e)`
       |`toLeftDisjunction[A]`
       |`<\/[A]`
      """.stripMargin.p
  eg {
    import scalaz._
    import syntax.std.option._

    Some(10) \/> "message" must beAnInstanceOf[String \/ Int]
    Some(10) \/> "message" must_== \/-(10)

    Some(10).toRightDisjunction("message") must beAnInstanceOf[String \/ Int]
    Some(10).toRightDisjunction("message") must_== \/-(10)

    //@todo None \/> "message" must beAnInstanceOf[\/[String, A]]
    None \/> "message" must_== -\/("message")

    //@todo None.toRightDisjunction("message") must beAnInstanceOf[\/[String, A]]
    None.toRightDisjunction("message") must_== -\/("message")

    Some(10) <\/ "message" must beAnInstanceOf[Int \/ String]
    Some(10) <\/ "message" must_== -\/(10)

    Some(10).toLeftDisjunction("message") must beAnInstanceOf[Int \/ String]
    Some(10).toLeftDisjunction("message") must_== -\/(10)

    //@todo None <\/ "message" must beAnInstanceOf[\/[A, String]]
    None <\/ "message" must_== \/-("message")

    //@todo None.toLeftDisjunction("message") must beAnInstanceOf[\/[A, String]]
    None.toLeftDisjunction("message") must_== \/-("message")
  }

  eg {
    import scalaz.{\/, -\/, \/-}
    import scalaz.std.list._
    import scalaz.syntax.traverse._
    import scalaz.syntax.either._

    def f(x: Int): \/[String, Int] = if (x > 2) x.right[String] else "failure".left[Int]

    List(1, 2, 3).traverseU(f) must beAnInstanceOf[String \/ List[Int]]
    List(1, 2, 3).traverseU(f) must_== -\/("failure")

    List(3, 4, 5).traverseU(f) must beAnInstanceOf[String \/ List[Int]]
    List(3, 4, 5).traverseU(f)  must_== \/-(List(3, 4, 5))

    List(3, 4, 5).map(f).sequenceU must beAnInstanceOf[String \/ List[Int]]
    List(3, 4, 5).map(f).sequenceU must_== \/-(List(3, 4, 5))

    List(1, 2, 3).map(f).sequenceU must beAnInstanceOf[String \/ List[Int]]
    List(1, 2, 3).map(f).sequenceU must_== -\/("failure")
  }

  eg {
    import scalaz.{\/, -\/, \/-, Validation}
    import scalaz.syntax.std.string._
    import scalaz.syntax.validation._
    import scalaz.syntax.applicative._
    import scalaz.std.AllInstances._

    import java.time.LocalDate

    case class Musician(name: String, born: LocalDate)

    def validate(musician: Musician): Validation[String, Musician] = {
      import scalaz.Scalaz._

      def validName(name: String): Validation[String, String] =
        if (name.isEmpty) "name cannot be empty".failure
        else name.success

      def validateAge(born: LocalDate): Validation[String, LocalDate] =
        if (born.isAfter(LocalDate.now().minusYears(12))) "too young".failure
        else born.success

      (validName(musician.name) |@| validateAge(musician.born))((_, _) => musician)
    }

    val either: \/[String, Musician] = \/-(Musician("", LocalDate.now().minusYears(11)))

    val r = either match {
      case \/-(value) => either.@\?/(_ => validate(value))
      case _ => \/-(Musician("", LocalDate.now()))
    }

    r must_== -\/("name cannot be emptytoo young")
  }
}