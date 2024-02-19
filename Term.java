public class Term {
    
    Factor factor;
    Character operator;
    Term term;

    /**
     * Parses the <term> non-terminal in the Core context-free-grammar, which is defined as:
     *      <term> ::= <factor> | <factor> * <term> | <factor> / <term> 
     */
    void parse() {

        factor = new Factor();
        factor.parse();

        // <factor> * <term> | <factor> / <term>
        if (Parser.currentTokenIs(Core.MULTIPLY) || Parser.currentTokenIs(Core.DIVIDE)) {
            operator = Parser.currentTokenIs(Core.MULTIPLY) ? '*' : '/';
            
            Parser.scanner.nextToken();

            term = new Term();
            term.parse();
        }
    }

    // Prints a term that's syntactically identical to the program input.
    void printer() {
        factor.printer();
        if (term != null) {
            System.out.print(" " + operator + " ");
            term.printer();
        }
    }

    // Performs a semantic check on non-terminals lower in the parse tree
    int execute() {

        int value = factor.execute();

        if (operator != null) {
            if (operator == '*') {
                value *= term.execute();
            } else {
                value /= term.execute();
            }
        } 
        
        return value;
    }
}
