package org.specs2.typeclass

/**
  *
  */
package object definition {

  /**
    * @see [[org.fp.resources.Scala]] [[org.fp.resources.Scalaz]]
    * @source https://hyp.is/6TGlfiwMEea_38vRFJt4xQ/archive.is/jnGcW
    */
  object ScalaSpec extends org.specs2.mutable.Specification {

    "Boiler plate code to define the type class related stuff manually".p
    eg {

      trait CanTruthy[A] {
        self =>
        /** @return true, if `a` is truthy. */
        def truthys(a: A): Boolean
      }

      object CanTruthy {
        def apply[A](implicit ev: CanTruthy[A]): CanTruthy[A] = ev

        def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
          def truthys(a: A): Boolean = f(a)
        }
      }

      trait CanTruthyOps[A] {
        def self: A

        implicit def F: CanTruthy[A]

        final def truthy: Boolean = F.truthys(self)
      }

      object ToCanIsTruthyOps {
        implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]) =
          new CanTruthyOps[A] {
            def self = v

            implicit def F: CanTruthy[A] = ev
          }
      }

      "Here’s how we can define typeclass instances for Int:".p
      implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
        case 0 => false
        case _ => true
      })

      import ToCanIsTruthyOps._

      10.truthy must_== true

      "Next is for List[A]:".p
      implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
        case Nil => false
        case _   => true
      })

      List("foo").truthy must_== true

      "It looks like we need to treat Nil specially because of the nonvariance.".p
      implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false)
      Nil.truthy must_== false

      "And for Boolean using identity:".p
      implicit val booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity)

      false.truthy must_== false

      "Using CanTruthy typeclass, let’s define truthyIf like LYAHFGG:".p
       //Now let’s make a function that mimics the if statement, but that works with YesNo values.

      "To delay the evaluation of the passed arguments, we can use pass-by-name:".p
      def truthyIf[A: CanTruthy, B, C](cond: A)(ifyes: => B)(ifno: => C) =
        if (cond.truthy) ifyes
        else ifno

      truthyIf (Nil:List[String]) {"YEAH!"} {"NO!"} must_== "NO!"
      truthyIf (2 :: 3 :: 4 :: Nil) {"YEAH!"} {"NO!"} must_== "YEAH!"
      truthyIf (true) {"YEAH!"} {"NO!"} must_== "YEAH!"
    }
  }

  /**
    * @see [[org.fp.resources.Simulacrum]]
    */
  object SimulacrumSpec extends org.specs2.mutable.Specification {

    success
  }


}
