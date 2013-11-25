

trait AggregateRoot[A <: AggregateRoot[A, E], E] {
  self =>

  def t: A
}




def test[A <: AggregateRoot[A, E], E](r: A): A = r


var t = List(0)
test(t)



