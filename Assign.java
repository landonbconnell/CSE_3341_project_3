public class Assign {
    
    String identifier1, identifier2;
    Expr expr1, expr2;
    boolean isInstantiatingObject = false;


    /**
     * Parses the <assign> non-terminal in the Core context-free-grammar, which is defined as:
     * <assign> ::= id = <expr>; | id [ <expr> ] = <expr>; | id = new object( <expr> ); | id : id;
     */
    void parse() {
        identifier1 = Parser.scanner.getId();

        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(false, Core.ASSIGN, Core.COLON, Core.LBRACE);

        // id = <expr>; | id = new object( <expr> );
        if (Parser.currentTokenIs(Core.ASSIGN)) {
            Parser.scanner.nextToken();

            // id = <expr>;
            if (!Parser.currentTokenIs(Core.NEW)) {
                expr1 = new Expr();
                expr1.parse();

            // id = new object( <expr> );
            } else {
                isInstantiatingObject = true;

                Parser.scanner.nextToken();

                Parser.checkCurrentTokenIs(true, Core.OBJECT);
                Parser.checkCurrentTokenIs(true, Core.LPAREN);

                expr1 = new Expr();
                expr1.parse();

                Parser.checkCurrentTokenIs(true, Core.RPAREN);
            }

        // id [ <expr> ] = <expr>;
        } else if (Parser.currentTokenIs(Core.LBRACE)) {
            Parser.scanner.nextToken();

            expr1 = new Expr();
            expr1.parse();

            Parser.checkCurrentTokenIs(true, Core.RBRACE);
            Parser.checkCurrentTokenIs(true, Core.ASSIGN);

            expr2 = new Expr();
            expr2.parse();

        // id : id;
        } else if (Parser.currentTokenIs(Core.COLON)) {
            Parser.scanner.nextToken();
            Parser.checkCurrentTokenIs(false, Core.ID);
            identifier2 = Parser.scanner.getId();
            Parser.scanner.nextToken();
        }

        Parser.checkCurrentTokenIs(false, Core.SEMICOLON);
    }

    // Prints an assignment statement that's syntactically identical to the program input.
    void printer() {
        // id = <expr>; | id = new object( <expr> );
        if ((expr1 != null) && (expr2 == null)) {

            // id = <expr>;
            if (!isInstantiatingObject) {
                System.out.print("\t" + identifier1 + " = ");
                expr1.printer();
                System.out.println(";");

            // id = new object( <expr> );
            } else {
                System.out.print("\t" + identifier1 + " = new object( ");
                expr1.printer();
                System.out.println(" );");
            }

        // id [ <expr> ] = <expr>;
        } else if ((expr1 != null) && (expr2 != null)) {
            System.out.print("\t" + identifier1 + " [ ");
            expr1.printer();
            System.out.print(" ] = ");
            expr2.printer();
            System.out.println(";");

        // id : id;
        } else if (identifier2 != null) {
            System.out.println("\t" + identifier1 + " : " + identifier2 + ";");
        }
    }

    // Performs semantic checks on the assignment statement and non-terminals lower in the parse tree.
    void check() {

        if (!SemanticChecker.isInScope(identifier1)) {
            System.out.println("ERROR: '" + identifier1 + "' has not been declared.");
            System.exit(0);
        }

        Variable variable1 = SemanticChecker.getVariable(identifier1);

        // id = <expr>; | id = new object( <expr> );
        if ((expr1 != null) && (expr2 == null)) {

            // id = <expr>;
            if (!isInstantiatingObject) {
                if (variable1.type != Type.INTEGER) {
                    System.out.println("ERROR: cannot assign an integer to a variable of type 'object'.");
                    System.exit(0);
                }

            // id = new object( <expr> );
            } else {
                if (variable1.type != Type.OBJECT) {
                    System.out.println("ERROR: cannot assign an object to a variable of type 'integer'.");
                    System.exit(0);
                }
            }

            expr1.check();

        // id [ <expr> ] = <expr>;
        } else if ((expr1 != null) && (expr2 != null)) {

            if (variable1.type != Type.OBJECT) {
                System.out.print("ERROR: the statement '" + identifier1 + "[");
                expr1.printer();
                System.out.print("] = ");
                expr2.printer();
                System.out.println("' cannot be used on a variable with type 'integer'.");

                System.exit(0);
            }

            expr1.check();
            expr2.check();

        // id : id;
        } else if (identifier2 != null) {
            if (!SemanticChecker.isInScope(identifier2)) {
                System.out.println("ERROR: '" + identifier2 + "' has not been declared.");
                System.exit(0);
            }

            Variable variable2 = SemanticChecker.getVariable(identifier2);

            if (variable1.type != Type.OBJECT || variable2.type != Type.OBJECT) {
                System.out.println("ERROR: the statement '" + identifier1 + " : " + identifier2 + "' requires " + identifier1 + " and " + identifier2 + " are both objects.");
                System.exit(0);
            }
        }
    }
}
