package org.specs2.thirdparty.blogs.scalac

import org.fp.concepts._
import org.fp.resources._
import org.fp.bookmarks._

import scala.language.higherKinds

//
import org.specs2.ugbase.UserGuidePage
import org.specs2.common.SnippetHelper._
import org.specs2.execute.SnippetParams

import org.fp.thirdparty.scalac.Overview_of_free_monads_in_cats.snippets.{API01, API02}

/**
  *
  * @see [[freeMonad]], [[coProduct]]
  */

/**
  *
  */
object Overview_of_free_monad_in_cats extends UserGuidePage {
  def is = s"Overview of free monad in cats".title ^ s2"""

## Putting the Free monad to work

We gonna implement basic instructions from LOGO language - the famous “Turtle”.
We will implement movement in a 2 dimensional space.

### Creating set of instructions (AST)

Ok, let’s say we have some set of instructions that we want to use in order to create program.

Using the LOGO example I would like to implement basic functionality like moving forward and backward,
rotating left or right and showing the current position.
To do that I’ve created a few simple classes to represent those actions.

These are just simple case classes. Type A will be the type that $freeMonad will be working on.
That means if we use a $operator_flatMap we will have to provide a function of type `A => Instruction[B]`.

${incl[API01]}

There are also two classes that will be used. It’s a part of simplified domain model.

${snippet {
   /**/
   case class Position(x: Double, y: Double, heading: Degree)
   case class Degree(private val d: Int) {
     val value = d % 360
   }
}}

At this point we have defined only our actions with some additional types, but it does not compute anything, it’s abstract.

## Creating the DSL

DSL is a Domain Specific Language. It contains functions that refer to the domain. Such functions make the code more readable
and expressive. To make it easier to use I’ve created some helper methods that will create $freeMonad-s wrapping our actions,
by lifting `Instruction[A]` into `Free[Instruction, A]`.

${incl[API02]}

These methods are used to write the application and make it stateless by taking a position as a parameter and not storing it inside the function.

## The program

Now we can easily use our DSL. Having the methods in place we can use them in a $forComprehension to build a program description.
Let’s say we want to get from `point (0,0)` to `point (10,10)`. The code looks like this:

${snippet{
    /**/
    // 8<--
    import API02._
    import API02.Logo._
    import org.specs2.matcher.{EitherBaseMatchers => _, _}

    // 8<--
    import cats.free.Free

    val program: (Position => Free[Instruction, Position]) = {
      start: Position =>
        for {
          p1 <- forward(start, 10)
          p2 <- API02.right(p1, Degree(90))
          p3 <- forward(p2, 10)
        } yield p3
    }
}}

As we can see the program is just a function that takes a starting position and moves the turtle.
The result of calling the function is also a $freeMonad and that’s great news because it’s very easy to compose another function using this one.
We can simply think of creating functions to draw basic geometric shapes and then using these functions to draw something more complicated;
furthermore, it’s going to compose very well. It’s like that because of referential transparency property of the $freeMonad.

Another advantage is that we don’t worry how it is computed. We are just expressing how program should work using the DSL.

## Interpreter

At this moment we have our program description, but we want to execute it. In order to do that a $naturalTransformation is needed.
It’s a function that can transform one $typeConstructor into another one. A $naturalTransformation from `F` to `G` is often written as `F[_] ~> G[_]`.
What is important, is that we have to provide implicit conversion from `G` to a `Monad[G]`.

It’s possible to have multiple interpreters, e.g. one for testing and another one for production.
This is very useful when we create an AST for interacting with a database or some other external service.
The simplest $monad is the identity monad Id, which is defined in ${Cats.md} as a simple type container.

${snippet{
    type Id[A] = A
  }}

It has all necessary functions implemented and implicit value that is a `Monad[Id]`.
In the ${Cats.md} package object there is an implicit instance that helps with conversion to an Id.

Let’s use it to write our own interpreter.

${snippet{
    /**/
    // 8<--
    import API02._
    import API02.Logo._

    //type Id[A] = A
    // 8<--
    import cats.{Id, ~>}
    //import cats.free.Free

    object InterpreterId extends (Instruction ~> Id) {
      //import Computations._
      override def apply[A](fa: Instruction[A]): Id[A] = fa match {
        case Forward(p, length) => forward(p, length)
        case Backward(p, length) => backward(p, length)
        case RotateLeft(p, degree) => left(p, degree)
        case RotateRight(p, degree) => API02.right(p, degree)
        case ShowPosition(p) => println(s"showing position $p")
      }
    }
  }}


""".stripMargin

  implicit override def snippetParams[T]: SnippetParams[T] = defaultSnippetParameters[T].copy(evalCode = true).offsetIs(-4)
}