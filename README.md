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

ref:
- https://twitter.github.io/scala_school/ko/type-basics.html

---
#### Case class
---

```scala
trait Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
```

Case class 는 스칼라 컴파일러에게 해당 클래스에 문법적으로 편리한 기능 몇 가지를 추가하려고 지시하는 것이다.

1. 컴파일러는 클래스 이름과 같은 이름의 팩토리 메소드를 추가한다.

```scala
val v = Var("x")
```

2. 케이스 클래스의 파라미터 목록에 있는 모든 인자에 암시적으로 val 접두사를 붙인다.

3. 컴파일러는 케이스 클래스에 toString, hashCode, equals 메소드의 일반적인 구현을 추가한다.

```scala
val op = BinOp("+", Number(1), v)
println(op)
op.right == Var("x")
```

4. 컴파일러는 어떤 케이스 클래스에서 일부를 변경한 복사복을 생성하는 copy 메소드를 추가한다.

```scala
op.copy(operator = "-")
```

위는 operator 만 바꾸고 나머지는 op 와 같은 연산을 어떻게 만드는지 보여준다.

---
#### Pattern match
---

```selector match { alternatives }``` 와 같은 구조.

- constant pattern 은 == 연산자를 사용
- variable pattern 은 모든 값과 매치
- wildcard pattern (_) 은 모든 값과 매치
- constructor pattern 은 생성자와 매치

---
***switch 와 match 비교***

1. match 는 ***표현식***, 따라서 결과 값을 가진다
2. 스칼라의 대안 표현식은 다음 케이스로 빠지지 않는다.
3. 매치에 성공하지 못하는 경우 MatchError 예외가 발생, 따라서 디폴트 케이스를 반드시 추가해야함

---
### Week 5
---

---
#### Lists
---

- Lists are immutable, and recursive (arrays are mutable, and flat)
- Homogeneous: elements 가 같은 타입이여야 한다.
- head, tail, isEmpty, 등등 여러 method 지원
- merge sort ? -> scala List 의 sort 는 Java 의 Arrays.sort() 를 사용한다 (merge sort, stable)

---
scala.math.Ordering -> Java Comparator 구현

---
상수 시간 복잡도인 ```head``` 및 ```tail``` 과 달리, ```init``` 과 ```last``` 는 결과를 계산하기 위해 전체 리스트를 순회해야 한다. 그러므로 이 둘은 리스트 길이만큼 시간이 걸린다.
