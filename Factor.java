public class Factor {

    String identifier;
    Integer constant;
    Expr expr;

    /**
     * Parses the <factor> non-terminal in the Core context-free-grammar, which is defined as:
     *      <factor> ::= id | id [ <expr> ] | const | ( <expr> ) | in ( ) ;
     */
    void parse() {

        Parser.checkCurrentTokenIs(false, Core.ID, Core.CONST, Core.LPAREN, Core.IN);

        // id | id [ <expr> ]
        if (Parser.currentTokenIs(Core.ID)) {
            // save identifier for later use
            identifier = Parser.scanner.getId();
            Parser.scanner.nextToken();

            // id [ <expr> ]
            if (Parser.currentTokenIs(Core.LBRACE)) {
                Parser.scanner.nextToken();

                expr = new Expr();
                expr.parse();

                Parser.checkCurrentTokenIs(true, Core.RBRACE);
            }

        // const
        } else if (Parser.currentTokenIs(Core.CONST)) {
            constant = Parser.scanner.getConst();
            Parser.scanner.nextToken();

        // ( <expr> )
        } else if (Parser.currentTokenIs(Core.LPAREN)) {
            Parser.scanner.nextToken();

            expr = new Expr();
            expr.parse();

            Parser.checkCurrentTokenIs(true, Core.RPAREN);

        // in ( )
        } else if (Parser.currentTokenIs(Core.IN)) {
            Parser.scanner.nextToken();

            Parser.checkCurrentTokenIs(true, Core.LPAREN);
            Parser.checkCurrentTokenIs(true, Core.RPAREN);
        }
    }

    // Prints a factor that's syntactically identical to the program input.
    void printer() {

        // id
        if (identifier != null) {
            System.out.print(identifier);

            // id [ <expr> ]
            if (expr != null) {
                System.out.print(" [");
                expr.printer();
                System.out.print(" ]");
            }

        // const
        } else if (constant != null) {
            System.out.print(constant);

        // ( <expr> )
        } else if (expr != null) {
            System.out.print("( ");
            expr.printer();
            System.out.print(" )");

        // in ();
        } else {
            System.out.print("in()");
        }
    }

    // Performs a semantic check on the identifier used as a factor.
    void check() {
        // id
        if (identifier != null) {
            if (!SemanticChecker.isInScope(identifier)) {
                System.out.println("ERROR: '" + identifier + "' has not been declared.");
                System.exit(0);
            }

            // id [ <expr> ]
            if (expr != null) {
                Variable variable = SemanticChecker.getVariable(identifier);
                if (variable.type != Type.OBJECT) {
                    System.out.print("ERROR: the statement '" + identifier + "[");
                    expr.printer();
                    System.out.print("]' cannot be used on a variable with type 'integer'.");

                    System.exit(0);
                }
            }
        }
    }

    // Performs a semantic check on non-terminals lower in the parse tree
    int execute() {
        int value = 0;

        if (constant != null) {
            value = constant;
        }

        return value;
    }
}
