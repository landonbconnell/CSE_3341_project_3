public class DeclInteger {

    String identifier;

    /**
     * Parses the <decl-integer> non-terminal in the Core context-free-grammar, which is defined as:
     *      <decl-integer> ::= integer id ;
     */
    void parse() {
        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(false, Core.ID);

        // saves identifier for later use
        identifier = Parser.scanner.getId();
        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(false, Core.SEMICOLON);
    }

    // Prints an integer declaration that's syntactically identical to the program input.
    void printer() {
        System.out.println("\tinteger " + identifier + ";");
    }

    /**
     * Performs a semantic check on the integer declaration, verifying the provided identifier
     * doesn't match another variable in the same scope.
     */ 
    void check() {
        if (!SemanticChecker.isInCurrentScope(identifier)) {
            SemanticChecker.addVariableToCurrentScope(identifier, Type.INTEGER);
        } else {
            System.out.println("ERROR: '" + identifier + "' is declared multiple times in the same scope.");
            System.exit(0);
        }
    }
}
