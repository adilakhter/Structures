package structures

import simulacrum.typeclass

/**
 * Monad that defines one additional abstract method, `id`, such that:
 *  - for all `A`, `fa: F[A]`, `flatMap(id)(_ => fa) == id`
 *
 * This gives rise the the `filter` method and its variants.
 */
@typeclass trait MonadFilter[F[_]] extends Monad[F] {

  def id[A]: F[A]

  def filter[A](fa: F[A])(f: A => Boolean) =
    flatMap(fa)(a => if (f(a)) pure(a) else id[A])

  def filterM[A](fa: F[A])(f: A => F[Boolean]) =
    flatMap(fa)(a => flatMap(f(a))(b => if (b) pure(a) else id[A]))
}
