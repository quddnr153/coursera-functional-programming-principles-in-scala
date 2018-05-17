# Coursera : Functional Programming

---
### Week 4
---

#### Eta expansion
Eta-expansion converts an expression of method type to an equivalent expression of function type.

```scala
def isEven(i: Int) = i % 2 == 0
val evens = List(1, 2, 3, 4, 5, 6).filter(isEven)
```

It uses this "Eta Expansion" capability to automatically convert the method isEven into a function - a true Function1 instance - so it can be passed into filter.

This happens automatically during the compilation process, so you generally don't even have to think about.

1. The scaladoc for collections methods like map and filter show that they take functions as input parameters.
2. Despite that, somehow you can pass methods into them
3. The reason that works is called "Eta Expansion."

ref:
- https://alvinalexander.com/scala/fp-book/how-to-use-scala-methods-like-functions
- https://www.scala-lang.org/files/archive/spec/2.12/06-expressions.html#eta-expansion

#### Bounds
스칼라에서는 다형성 변수를 bounds 를 사용해 제약할 수 있다.

Type bounds, 
- S <: T => S is a subtype of T
- S >: T => S is a supertype of T, T is a subtype of S

Lower bounds,
- S >: T

Mixed bounds,
- S >: NonEmpty <: IntSet

```
scala> class Animal { val sound = "rustle" }
defined class Animal

scala> class Bird extends Animal { override val sound = "call" }
defined class Bird

scala> class Chicken extends Bird { override val sound = "cluck" }
defined class Chicken

scala> def cacophony[T](things: Seq[T]) = things map (_.sound)
<console>:7: error: value sound is not a member of type parameter T
       def cacophony[T](things: Seq[T]) = things map (_.sound)
                                                        ^

scala> def biophony[T <: Animal](things: Seq[T]) = things map (_.sound)
biophony: [T <: Animal](things: Seq[T])Seq[java.lang.String]

scala> biophony(Seq(new Chicken, new Bird))
res5: Seq[java.lang.String] = List(cluck, call)

scala> val flock = List(new Bird, new Bird)
flock: List[Bird] = List(Bird@7e1ec70e, Bird@169ea8d2)

scala> new Chicken :: flock
res53: List[Bird] = List(Chicken@56fbda05, Bird@7e1ec70e, Bird@169ea8d2)

scala> new Animal :: flock
res59: List[Animal] = List(Animal@11f8d3a8, Bird@7e1ec70e, Bird@169ea8d2)
```

하위 타입 바운드도 지원된다. 하위 타입 바운드는 반공변성인 경우를 처리하거나 공변성 관계를 좀 더 똑똑하게 처리할 때 유용하다. List[+T]는 공변성이 있다. 새의 리스트는 동물의 리스트이기도 하다.

List에는 ::(elem T) 연산자가 있다. 이 연산자는 elem이 앞에 붙은 새 List를 반환한다. 이 새 List는 원래의 리스트와 같은 타입이 된다.

List에는 ::[B >: T](x: B)라는 연산자가 또한 있어서 List[B]를 반환한다. B >: T 관계를 보라. 이는 B가 T의 상위 클래스라는 의미이다. 이로 인해 Animal을 List[Bird]의 앞에 붙이는 경우도 제대로 처리할 수 있다.
