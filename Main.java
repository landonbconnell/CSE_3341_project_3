/**
 * This program drives a compiler process which involves scanning an input file, generating a parse tree, 
 * performing semantic checking, and then reconstructing the program from the parse tree to output the Core program.
 */
class Main {

    static Parser parser;
    static SemanticChecker semanticChecker;

    public static void main(String[] args) {
        // Instantiate Parser and Semantic Checker.
        parser = new Parser(args[0]);
        semanticChecker = new SemanticChecker();

        // Run the parser to generate the parse tree.
        parser.run();

        // Run the semantic checker.
        semanticChecker.run(parser.procedure);

        // Print the program from the parse tree.
        parser.procedure.printer();
    }
}