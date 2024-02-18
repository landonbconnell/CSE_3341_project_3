public class Cond {
    
    Cond cond;
    Cmpr cmpr;
    String modifier;

    /**
     * Parses the <cond> non-terminal in the Core context-free-grammar, which is defined as:
     *  <cond> ::= <cmpr> | not <cond> | [ <cond> ] | <cmpr> or <cond> | <cmpr> and <cond>
     */
    void parse() {

        // not <cond>
        if (Parser.currentTokenIs(Core.NOT)) {
            Parser.scanner.nextToken();

            modifier = "not";

            cond = new Cond();
            cond.parse();
        
        // [ <cond> ]
        } else if (Parser.currentTokenIs(Core.LBRACE)) {
            Parser.scanner.nextToken();

            cond = new Cond();
            cond.parse();

            Parser.checkCurrentTokenIs(true, Core.RBRACE);

        // <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
        } else {
            // <cmpr>
            cmpr = new Cmpr();
            cmpr.parse();

            // <cmpr> or <cond> | <cmpr> and <cond>
            if (Parser.currentTokenIs(Core.OR) || Parser.currentTokenIs(Core.AND)) {
                modifier = Parser.currentTokenIs(Core.OR) ? "or" : "and";

                cond = new Cond();
                cond.parse();
            }
        }
    }

    // Prints a condition that's syntactically identical to the program input.
    void printer() {

        // [ <cond> ]
        if (cmpr == null && modifier == null) {
            System.out.print("[ ");
            cond.printer();
            System.out.print(" ]");
        
        // <cmpr> | not <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
        } else {
            //<cmpr>
            if (cmpr != null) {
                cmpr.printer();
            }

            // not <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
            if (modifier != null) {
                System.out.print((!modifier.equals("not") ? " " : "") + modifier + " ");
                cond.printer();
            }
        }
    }

    // Performs a semantic check on the condition and non-terminals lower in the parse tree
    void check() {

        // [ <cond> ]
        if (cmpr == null && modifier == null) {
            cond.check();
        
        // <cmpr> | not <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
        } else {
            //<cmpr>
            if (cmpr != null) {
                cmpr.check();
            }
    
            // not <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
            if (modifier != null) {
                cond.check();
            }
        }
    }
}
