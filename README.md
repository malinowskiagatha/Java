# Java
***Assignment***
#1 : RationalNumber.java

Define a class RationalNumber that represents rational numbers and a few simple operations
on them, as indicated below. A rational number can be expressed as the fraction p
q, where p and q are two integers, q 6= 0. The class should extend the abstract class java.lang.Number
and hence, should implement all its abstract methods.

The operations to be supported are divided into three groups: 
(1) a set of basic operations; 
(2) implementation of methods from the abstract class java.lang.Number; and 
(3) methods implementing the logic for representing binary periodic numbers.

***Basic operations: the following operations should be supported:***
- A constructor RationalNumber(int, int) for creating a rational number of the form p/q, given p and q. The program should handle cases when q = 0 by throwing a RuntimeException.

- An operation void simplify() for simplifying a rational number of the form p/q to its simplest form r/s, where r = p/gcd(p, q) and s = q/gcd(p, q). The method replaces the receiving rational number with its simplified form.

- An operation void add(RationalNumber), which adds the rational number given as parameter to the one on which this method is invoked (the receiving rational number). This operation modifies the receiving rational number with the result of the addition. Recall that the sum of rational numbers p/q and r/s is the simplified form of (ps)+(rq)/qs.

- An operation void multiply(RationalNumber), which multiplies the rational number given as parameter to the one on which this method is invoked (the receiving
rational number). This operation modifies the receiving rational number with the
result of the multiplication in simplified form.

***Implementing java.lang.Number methods***
- double doubleValue() returns a double which represents the rational number in decimal form, including up to 3 decimal digits. (Truncate the result if it has more than 3 decimal digits). For example, calling (new RationalNumber(2,3)).doubleValue() should return the value 0.666.

- float floatValue() returns a float which represents the rational number in decimal form, including up to 3 decimal digits.

- int intValue() returns an int which represents the integer part of the decimal representation of the rational number. For example, calling (new RationalNumber(6,5)).intValue() should return the value 1.

- long longValue() returns a long which represents the integer part of the decimal
representation of the rational number.

***Binary Periodic Number***
A binary periodic number is a binary fractional number that includes a (possibly empty)
periodic (a.k.a recurring) fractional part. For example, 100.01_101 is a binary periodic
number, where 100 is called the characteristic, 01 is called the anti-period, and 101 is called
the period. Hence, a binary periodic number can be represented as a Java String literal
"characteristic.antiperiod period " where any two (but not all three) of characteristic,
antiperiod, period can be empty. Examples of valid strings are .10_001, 10.1, 10._101, ._01
But ._ is an invalid string.
- To evaluate the characteristic, proceed from right to left, and multiply each digit by
increasing powers of 2 (starting from 2^0 = 1). In this example, 0∗2^0 + 0∗2^1 + 1∗2^2 = 4.
- To evaluate the anti-period, proceed from left to right, and divide each digit by
increasing powers 2 (starting from 2^1 = 2). In this example , 0/(2^1) + 1/(2^2) = 1/4.
- To evaluate the period, proceed from right to left, and multiply each digit by increasing
powers of 2 (starting from 2^0 = 1). (This is the same process that is applied to the
characteristic.) Then, divide the result by 2^a(2^p − 1), where a is the length of the
anti-period, and b is the length of the period. In this example, 101 evaluates to 5,
a = length(anti-period) = 2, b = length(period) = 3, yielding 5/(2^2(2^3−1) == 5/4·7 = 5/28.
- The rational number which represents the binary periodic number is obtained by
adding the value of characteristic, anti-period and period. In this example, 100.01_101
evaluates to 4 + 1/4 + 5/28 = 124/28 = 31/7. The final rational number should be in simplifed form.

***Implement the following:***
A constructor RationalNumber(String s) where string s is of the form
characteristic.antiperiod period
The constructor should parse s and convert the binary periodic number to a rational number.
Hint :
- int java.lang.String.indexOf(int ch) returns the index of the first occurrence of
character ch in the string.
- char java.lang.String.charAt(int index) returns the char value at the specified
index.
- char java.lang.String.substring(int beginIndex, int endIndex) returns a new
String that is a substring of this String from beginIndex included up to endIndex
excluded.

---------------------------------------------------------------------------------------------------------------------------------------

***Assignment***
#2 : Treap.java

A ***treap*** is a binary search tree(BST) which additionally maintains heap priorities. A node consists of:
  - A key k (given by the letter in the example),
  - A random heap priority p (given by the number in the example). The heap priority p
    is assigned at random upon insertion of a node. It should be unique in the treap (but
    your code does not need to enforce that).
  - References to the left and right child node
 
 ***Node Class***
- Data Fields:
  - public E data
  - public int priority
  - public Node <E> left
  - public Node <E> right
- Constructors:
  - public Node(E data, int priority)
- Methods:
  - public Node <E> rotateRight()
  - public Node <E> rotateLeft()
  
***Treap Class***
- Data fields:
  - private Random priorityGenerator;
  - private Node <E> root;
- Constructors:
  - public Treap()
  - public Treap(long seed)
- Methods:
  - public boolean add(E key)
  - public boolean add(E key, int priority)
  - private Node <E> add(Node <E> localroot, E key, int priority)
  - public E delete(E key)
  - private Node <E> delete(Node <E> localroot, E key)
  - public E find(E key)
  - private E find(Node <E> root, E key)
  - public String toString()
  
  ***The Add Method***
    - This method inserts a given element into the tree via public boolean add(E key, int priority). If the priority is not given, the           method should utilize the random priority generator "priorityGenerator".
    - private Node<E> add(Node<E> localroot, E key, int priority) is a helper function to the add method. It will return true/false           using the addReturn variable in order to add the element into the tree using the add method. addReturn only gets set to false if a       node with the given key already exisits within the treap. 
 
  ***The Delete Method***
    - This method deletes a node with the given key from the treap and returns the new treap. If the given node's key was not found           within the treap, the treap reamins unchanged.
    - The private helper function, private Node<E> delete(Node<E> localroot, E key), uses the "deleteReturn" variable. "deleteReturn           will return null only if the given nodes' key does not exist within the tree. The helper method will locate the node with the           given key and, using rotateRight() and rotateLeft(), will move the node down the tree until it becomes a leaf. From there it can         be removed simply.

   ***The Find Method***
    - This method searches for a given key within the treap.
    - Its private helper function, private E find(Node<E> root, E key), uses the findReturn variable to help its public method, public E       find(E key), determine if a certin node with the given key exists within the treap. If a node does exist, the node is returned. If       a node does not exist, null is returned.





