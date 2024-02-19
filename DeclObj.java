public class DeclObj {

    String identifier;

    /**
     * Parses the <decl-obj> non-terminal in the Core context-free-grammar, which is defined as:
     *      <decl-obj> ::= object id ;
     */
    void parse() {
        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(false, Core.ID);

        // saves identifier for later use
        identifier = Parser.scanner.getId();
        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(false, Core.SEMICOLON);
    }

    // Prints an object declaration that's syntactically identical to the program input.
    void printer() {
        System.out.println("\tobject " + identifier + ";");
    }

    /**
     * Performs a semantic check on the object declaration, verifying the provided identifier
     * doesn't match another variable in the same scope.
     */ 
    void execute() {
        if (!Executor.isInCurrentScope(identifier)) {
            Executor.addVariableToCurrentScope(identifier, Type.OBJECT);
        } else {
            System.out.println("ERROR: " + identifier + " already declared current in scope.");
            System.exit(0);
        }
    }
}