public class Out {
    Expr expr;

    /**
     * Parses the <out> non-terminal in the Core context-free-grammar, which is defined as:
     *      <out> ::= out ( <expr> ); 
     */
    void parse() {
        Parser.scanner.nextToken();
        Parser.checkCurrentTokenIs(true, Core.LPAREN);

        expr = new Expr();
        expr.parse();

        Parser.checkCurrentTokenIs(true, Core.RPAREN);
        Parser.checkCurrentTokenIs(false, Core.SEMICOLON);
    }

    // Prints a call to the 'out' function that's syntactically identical to the program input.
    void printer() {
        System.out.print("out(");
        expr.printer();
        System.out.println(");");
    }

    // Performs a semantic check on a non-terminal lower in the parse tree.
    void check() {
        expr.check();
    }
}
