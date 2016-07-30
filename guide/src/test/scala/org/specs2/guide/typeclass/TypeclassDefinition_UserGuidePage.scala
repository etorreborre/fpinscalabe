package org.specs2.guide.typeclass

import org.fp._
import org.fp.studies.typeclass.definition.{SimulacrumSpec, ScalaSpec}
import org.specs2.ugbase.UserGuidePage

/**
  *
  */
object TypeclassDefinition_UserGuidePage extends UserGuidePage {

  def is = s"Defining type classes".title ^ s2"""

  ${concepts.functor}s enable ${concepts.functorComposition}

    * in ${resources.Scala.id} ${ScalaSpec.is}

    * in ${resources.Simulacrum.id} ${SimulacrumSpec.is}

"""
}