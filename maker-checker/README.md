# Maker-Checker

This is an experiment in implementing maker-checker in Java using Spring Security's method-level expression-based access control.

From [Wikipedia](https://en.wikipedia.org/wiki/Maker-checker):

Maker-checker (or Maker and Checker, or 4-Eyes) is one of the central principles of authorization in the Information Systems of financial organizations. <em>The principle of maker and checker means that for each transaction, there must be at least two individuals necessary for its completion.</em> While one individual may create a transaction, the other individual should be involved in confirmation/authorization of the same.

Here the segregation of duties plays an important role. In this way, strict control is kept over system software and data, keeping in mind functional division of labor between all classes of employees.
