public class Expr {

    Term term;
    Character operator;
    Expr expr;

    /**
     * Parses the <expr> non-terminal in the Core context-free-grammar, which is defined as:
     *      <expr> ::= <term> | <term> + <expr> | <term> – <expr>
     */
    void parse() {

        // <term>
        term = new Term();
        term.parse();

        // <term> + <expr> | <term> – <expr>
        if (Parser.currentTokenIs(Core.ADD) || Parser.currentTokenIs(Core.SUBTRACT)) {
            operator = Parser.currentTokenIs(Core.ADD) ? '+' : '-';
            
            Parser.scanner.nextToken();

            expr = new Expr();
            expr.parse();
        }
    }

    // Prints an expression that's syntactically identical to the program input.
    void printer() {
        term.printer();
        if (expr != null) {
            System.out.print(" " + operator + " ");
            expr.printer();
        }
    }

    // Performs a semantic check on non-terminals lower in the parse tree
    int execute() {

        int value = term.execute();

        if (operator != null) {
            if (operator == '+') {
                value += expr.execute();
            } else {
                value -= expr.execute();
            }
        } 
        
        return value;
    }
}
