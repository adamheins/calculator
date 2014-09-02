# calculator

An implementation of a calculator. Instead of tediously pressing buttons, the user enters a string of text that gets parsed as math. The calculator has a large number of functions including all basic arithmetic, factorials, and trigonometry. Refer to Operator.java in the source code for a list of all supported operations.

The expression parser makes use of the shunting-yard algorithm to convert the string in infix notation to postfix notation for evaluation. The [Apfloat library](http://www.apfloat.org/apfloat_java/) was used to provide arbitrary precision numbers. Written in Java.
