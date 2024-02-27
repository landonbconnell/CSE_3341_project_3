Landon Connell

Assign.java - Has code for parsing, printing, and executing on the <assign> non-terminal.
Cmpr.java - Has code for parsing, printing, and executing on the <cmpr> non-terminal.
Cond.java - Has code for parsing, printing, and executing on the <cond> non-terminal.
Core.java - A file containing enums that serve as tokens.
Decl.java - Has code for parsing, printing, and executing on the <decl> non-terminal.
DeclInteger.java - Has code for parsing, printing, and executing on the <decl-integer> non-terminal.
DeclObj.java - Has code for parsing, printing, and executing on the <decl-obj> non-terminal.
DeclSeq.java - Has code for parsing, printing, and executing on the <decl-seq> non-terminal.
Executor.java - Inititates the execution of the program, and contains various methods for using and managing the scope stack.
Expr.java - Has code for parsing, printing, and executing on the <expr> non-terminal.
Factor.java - Has code for parsing, printing, and executing on the <factor> non-terminal.
If.java - Has code for parsing, printing, and executing on the <if> non-terminal.
Loop.java - Has code for parsing, printing, and executing on the <loop> non-terminal.
Main.java - A program that drives a compiler process which involves scanning an input file, generating a parse tree, and
    executing the code.
Out.java - Has code for parsing, printing, and executing on the <out> non-terminal.
Parser.java - This class initiates the parsing process by invoking the 'parse()' method on a 'Procedure' object and
    provides helper methods to validate token correctness during the parsing of the Core programming language.
Procedure.java - Has code for parsing, printing, and executing on the <procedure> non-terminal.
Scanner.java - A class defining a "Scanner" object, which takes source code as input, and outputs a stream of tokens.
Scope.java - A file containing enums for the different types of scopes (global, local, if, and loop)
Stmt.java - Has code for parsing, printing, and executing on the <stmt> non-terminal.
StmtSeq.java - Has code for parsing, printing, and executing on the <stmt-seq> non-terminal.
Term.java - Has code for parsing, printing, and executing on the <term> non-terminal.
Type.java - A file containing enums symbolizing valid Core language variable types (integer and object).
Variable.java - A class defining a "Variable" object which contains 'type' and 'int/object_value' members.

Special Features:

Interpreter Design:

My old SemanticChecker class already had methods for managing scopes and retrieving variables from it, and each of the 
.check() methods in the non-terminal classes already contained code to determine the next step in the production. Therefore,
to implement the Executor, I just had to make some slight modifications to the SemanticChecker class and .check() methods.

For the declaration sequence, each declaration resulted in a new variable being added to the topmost scope in the stack.

For each assignment statement, the right-hand-side was evaluated, then stored in the int_value or obj_value field, depending on its type.

For the if statement, a condition was evaluated and if it was true, the first statement sequence was executed.
Otherwise, if there was an else clause, the second statement was executed.

For the loop, a statement sequence was executed until the supplied condition evaluated to false.

For the out statement, an expression was evaluated then printed to the screen.




Interpreter Testing and Bugs:


