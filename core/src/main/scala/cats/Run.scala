package cats

trait Run[F[_, _], M[_]] {
  implicit def F: FlatMap[M]
  def run[A, B](fab: F[A, B])(a: A): M[B]
}
