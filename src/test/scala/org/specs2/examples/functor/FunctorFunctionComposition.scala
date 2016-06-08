package org.specs2.examples.functor

import org.fp._
import org.fp.studies.functor.composition.{CatsSpec, ScalazSpec}
import org.specs2.ugbase.UserGuidePage

/**
  *
  */
object FunctorFunctionComposition extends UserGuidePage {

  def is = s"Functor function composition".title ^ s2"""

  ${concepts.typeClass}s can be defined manually, the default ${resources.Scala.id} way, or automated using ${resources.Simulacrum.id}

    * in ${resources.Scalaz.id} ${ScalazSpec.is}

    * in ${resources.Cats.id} ${CatsSpec.is}

"""
}
