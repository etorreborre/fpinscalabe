package org.specs2.guide.functor

import org.fp._
import org.fp.studies.functor.laws._
import org.specs2.ugbase.UserGuidePage

/**
  *
  */
object FunctorsLaws_UserGuidePage extends UserGuidePage {

  def is = "Functors laws".title ^ s2"""

  ${concepts.functor}s are govered/defined by a few laws : ${concepts.lawIdentity}, ${concepts.lawComposition}, etc
  See examples

    * in ${resources.Scalaz.id} ${link(Spec)}

}

"""
}
