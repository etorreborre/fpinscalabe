package org.fp

/**
  *
  */
package object concepts {

  val keyPoint = "$$key point$$>>"
  val bookmarks = "$$bookmarks$$>>"

  //
  val selfType = "self-type"
  /**
    * @todo https://hyp.is/iJIsfC2GEeam8q-I4gf9FA/archive.is/KAdCc
    */
  val structuralType = "structural type"

  //
  val constructor = "[constructor]"
  val typeConstructor = "[type constructor]"
  val typeClass = "[type class]"
  val taggedType = "[tagged type]"
  val higherKindedType = "[higher-Kinded type]"
  val monadicFunction = "[monadic function]"

  //
  val functor = "[functor]"
  val biFunctor = "[bi functor]"
  val applyFunctor = "[apply functor]"
  val applicativeFunctor = "[applicative functor]"
  val applicativeBuilder = "[applicative builder]"
  val applicativeValue = "[applicative value]"
  val traverseFunctor = "[traverse]"
  val kleisli = "[kleisli]"

  /**
    * @todo related to [[operatorLift]]
    */
  val functionLifting = "[function lifting]"
  val functorComposition = "[functor composition]"

  /**
    * @todo "fmap" in Haskell
    */
  val operatorMap = "[map operator]"
  val operatorLift = "[lift operator]"
  val operatorVoid = "[void operator]"
  val operatorFproduct = "[fproduct operator]"
  val operatorFpair = "[fpair operator]"
  val operatorAs = "[as operator]"
  val operatorShift = "[>| operator]" //@todo what is the correct name?
  val operatorStrengthL = "[strengthL operator]"
  val operatorStrengthR = "[RightL operator]"
  val operatorPoint = "[pure/point operator]"
  val operatorApply = "[apply operator]"
  val operatorLHS = "[LHS operator]"
  val operatorRHS = "[RHS operator]"
  val operatorSequence = "[sequence operator]"
  val operatorTraverse = "[traverse operator]"


  /**
    *
    */
  val lawIdentity = "[identity law]"
  val lawComposition = "[composition law]"
  val lawHomomorphism = "[homomorphism law]"
  val lawInterchange = "[interchange law]"
  val lawMapping = "[mapping law]" //@todo ??

  //
  val monad = "[monad]"
  val monoid = "[monoid]"
  val operatorAppend = "[append operator]"
}

