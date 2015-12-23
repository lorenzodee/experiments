# Maker-Checker

This is an experiment in implementing maker-checker in Java using Spring Security's method-level expression-based access control.

Please see [MakerCheckerTests](src/test/java/makerchecker/security/MakerCheckerTests.java). To run the test in Eclipse, use `MakerCheckerTests.launch` to properly add `-javaagent` VM arguments. Otherwise, the instantiated objects will not have the proper aspects to provide maker-checker security.

From [Wikipedia](https://en.wikipedia.org/wiki/Maker-checker):

> Maker-checker (or Maker and Checker, or 4-Eyes) is one of the central principles of authorization in the Information Systems of financial organizations. *The principle of maker and checker means that for each transaction, there must be at least two individuals necessary for its completion.* While **one individual** may create a transaction, the **_other_ individual** should be involved in confirmation/authorization of the same.
>
> Here the segregation of duties plays an important role. In this way, strict control is kept over system software and data, keeping in mind functional division of labor between all classes of employees.
