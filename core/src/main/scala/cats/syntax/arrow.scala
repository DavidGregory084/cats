package cats
package syntax

import cats.arrow.Arrow

trait ArrowSyntax {
  implicit def arrowSyntax[F[_, _]: Arrow, M[_], A, B](fab: F[A, B])(implicit R: Run[F, M]): ArrowOps[F, M, A, B] =
    new ArrowOps[F, M, A, B](fab)
}

class ArrowOps[F[_, _]: Arrow, M[_], A, B](fab: F[A, B])(implicit R: Run[F, M]) {
  def -<(a: A): ArrowForSyntax[F, M, A, B] = new ArrowForSyntax[F, M, A, B](fab, a)
}

class ArrowForSyntax[F[_, _], M[_], A, B](fab: F[A, B], a: A)(implicit A: Arrow[F], R: Run[F, M]) {
  def map[C](g: B => C): M[C] = R.run(A.andThen(fab, A.lift(g)))(a)
  def flatMap[C](g: B => M[C]): M[C] = R.F.flatten(map(g))
}
