package org.fp.studies.traverse.operators

import org.fp.concepts._
import org.fp.resources._
import org.fp.bookmarks._

import scala.language.higherKinds

//
import org.specs2.specification.dsl.mutable.{TextDsl, AutoExamples}

/**
  *
  */
package object dfault {

  object Spec extends org.specs2.mutable.Spec with AutoExamples with TextDsl {

    s"$keyPoint The $traverseFunctor's $operatorSequence turns F[G[A] into G[F[A]] given that there exists " +
      s"an implementation of $traverseFunctor[F], and of $applicativeFunctor[G] (Option and List happen to satisfy both). " +
      s"This is like turning the structure 'inside-out'"

    s"$bookmarks $ann_TraverseUsage"

    eg { /** in [[Scalaz]] */

      import scalaz.Traverse
      import scalaz.std.list._
      import scalaz.std.option._

      val list1: List[Option[Int]] = List(Some(1), Some(2), Some(3), Some(4))
      // Explicit type declaration is needed to help inference
      Traverse[List].sequence(list1) must_== Some(List(1,2,3,4))
    }

    eg { /** in [[Cats]] */

      import cats._
      import cats.std.list._
      import cats.std.option._

      val list1: List[Option[Int]] = List(Some(1), Some(2), Some(3), Some(4))
      Traverse[List].sequence(list1) must_== Some(List(1,2,3,4))
    }

    s"$keyPoint Importing the $traverseFunctor syntax will enhance values which have an available $traverseFunctor instance:"

    eg { /** in [[Scalaz]] */

      import scalaz.std.list._
      import scalaz.std.option._

      import scalaz.syntax.traverse._

      val list1: List[Option[Int]] = List(Some(1), Some(2), Some(3), Some(4))
      // Explicit type declaration is needed to help inference
      list1.sequence must_== Some(List(1,2,3,4))
      list1.sequence.sequence must_== list1
    }

    eg { /** in [[Cats]] */

      import cats.std.list._
      import cats.std.option._
      import cats.syntax.traverse._

      val list1: List[Option[Int]] = List(Some(1), Some(2), Some(3), Some(4))
      //@todo list1.sequence must_== Some(List(1,2,3,4))
      //@todo list1.sequence.sequence must_== list1
      success
    }

    s"$keyPoint The $traverseFunctor's $operatorSequence maps a function over a structure through the effects of the " +
      s"inner $applicativeFunctor. You can think of this as combining a $operatorMap with a $operatorSequence. " +
      s"So when you find yourself calling fa.map(f).sequence, it can be replaced with just fa.traverse(f)::"
    /**
      * @todo determine if the [[operatorTraverse]] implementation is more economical in terms of intermediate data structures
      */

    eg { /** in [[Scalaz]] */

      import scalaz.std.list._
      import scalaz.std.option._
      import scalaz.syntax.traverse._

      val smallNumbers = List(1,2,3,4,5)
      val bigNumbers = List(10,20,30,40,50)
      val doubleSmall: Int => Option[Int] = x => if (x < 30) Some(x * 2) else None

      s"This multiples small numbers".p
      smallNumbers.traverse(doubleSmall) must_== Some(List(2,4,6,8,10))
      smallNumbers.traverse(doubleSmall) must_== smallNumbers.map(doubleSmall).sequence

      s"When we hit the 30, we get a None, which 'fails' the whole computation".p
      bigNumbers.traverse(doubleSmall) must_== scalaz.std.option.none[List[Int]]
      bigNumbers.traverse(doubleSmall) must_== bigNumbers.map(doubleSmall).sequence
    }

    eg { /** in [[Cats]] */

      import cats.std.list._
      import cats.std.option._
      import cats.syntax.traverse._

      val smallNumbers = List(1,2,3,4,5)
      val bigNumbers = List(10,20,30,40,50)
      val doubleSmall: Int => Option[Int] = x => if (x < 30) Some(x * 2) else None

      s"This multiples small numbers".p
      smallNumbers.traverse(doubleSmall) must_== Some(List(2,4,6,8,10))
      //@todo smallNumbers.traverse(doubleSmall) must_== smallNumbers.map(doubleSmall).sequence

      s"When we hit the 30, we get a None, which 'fails' the whole computation".p
      bigNumbers.traverse(doubleSmall) must_== scalaz.std.option.none[List[Int]]
      //@todo bigNumbers.traverse(doubleSmall) must_== bigNumbers.map(doubleSmall).sequence
    }

    s"$keyPoint The semantics of $applicativeFunctor[Option] are such that if any of the elements of a List of Options are None, then " +
      s"the sequence will be None as well."

    s"$bookmarks $ann_Traverse"

    eq { /** [[Scalaz]] */

      s"If you want to get a list of all the Some values regardless of whether any other values are None, you can do this:".p

      //      val list: List[Option[Int]] = List(Some(1), Some(2), None, Some(3))
      //      list.flatMap (_.toList) must_== Some(List(1, 2, 3))

      s"You can generalize that for any $monad that also forms a $monoid (List happens to be one of these)".p

      //      import scalaz.{Monad, Monoid}
      //      import scalaz.syntax.monad._
      //      import scalaz.std.option._
      //
      //      def somes[F[_], A](x: F[Option[A]])
      //                       (implicit m: Monad[F], z: Monoid[F[A]]) =
      //        x flatMap (o => o.fold(_.pure[F]) (z.zero))
      //
      //      implicit val m = Monad[List]
      //      implicit val z = Monoid[List[Int]]
      //
      //      somes(list)(m, z) must_== Some(List(1, 2, 3))
      success
    }

    eg { /** in [[Cats]] */

      import cats._
      import cats.std.list._
      import cats.std.option._

      //@todo
      success
    }
  }
}
