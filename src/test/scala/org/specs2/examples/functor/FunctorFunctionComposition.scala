package org.specs2.examples.functor

import org.fp._
import org.specs2.functor.functioncomposition.{CatsSpec, ScalazSpec}
import org.specs2.ugbase.UserGuidePage

/**
  *
  */
object FunctorFunctionComposition extends UserGuidePage {

  def is = "Defining type classes".title ^ s2"""

  ${concepts.typeClass}s can be defined manually, the default ${resources.Scala} way, or automated using ${resources.Simulacrum}

    * in ${resources.Scalaz} ${ScalazSpec.is}

    * in ${resources.Cats} ${CatsSpec.is}

"""
}