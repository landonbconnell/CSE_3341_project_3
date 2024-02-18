public class Stmt {
    
    Assign assign;
    If if_stmt;
    Loop loop;
    Out out;
    Decl decl;

    /**
     * Parses the <stmt> non-terminal in the Core context-free-grammar, which is defined as:
     *      <stmt> ::= <assign> | <if> | <loop> | <out> | <decl>
     */
    void parse() {

        Parser.checkCurrentTokenIs(false, Core.ID, Core.IF, Core.WHILE, Core.OUT, Core.INTEGER, Core.OBJECT);

        // <assign>
        if (Parser.currentTokenIs(Core.ID)) {
            assign = new Assign();
            assign.parse();

        // <if>
        } else if (Parser.currentTokenIs(Core.IF)) {
            if_stmt = new If();
            if_stmt.parse();

        // <loop>
        } else if (Parser.currentTokenIs(Core.WHILE)) {
            loop = new Loop();
            loop.parse();

        // <out>
        } else if (Parser.currentTokenIs(Core.OUT)) {
            out = new Out();
            out.parse();

        // <decl>
        } else if (Parser.currentTokenIs(Core.INTEGER) || Parser.currentTokenIs(Core.OBJECT)) {
            decl = new Decl();
            decl.parse();
        }
    }

    // Prints a statement that's syntactically identical to the program input.
    void printer() {
        if (assign != null) {
            assign.printer();
        } else if (if_stmt != null) {
            if_stmt.printer();
        } else if (loop != null) {
            loop.printer();
        } else if (out != null) {
            out.printer();
        } else if (decl != null) {
            decl.printer();
        }
    }

    // Performs a semantic check on non-terminals lower in the parse tree
    void check() {
        if (assign != null) {
            assign.check();
        } else if (if_stmt != null) {
            SemanticChecker.pushNewScope(); // Pushing if-statement scope
            if_stmt.check();
            SemanticChecker.popScope(); // Popping if-statement scope
        } else if (loop != null) {
            SemanticChecker.pushNewScope(); // Pushing while-loop scope
            loop.check();
            SemanticChecker.popScope(); // Popping while-loop scope
        } else if (out != null) {
            out.check();
        } else if (decl != null) {
            decl.check();
        }
    }
}
