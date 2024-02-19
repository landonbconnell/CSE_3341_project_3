import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Executor {
    public static Deque<Set<Variable>> scopes;
    public static Scanner in;

    public Executor(String input) {
        scopes = new ArrayDeque<>();
        in = new Scanner(input);
    }

    public void run(Procedure procedure) {
        procedure.execute();
    }

    public static Set<Variable> getGlobalScope() {
        return scopes.peekLast();
    }

    /**
     * Pushes a new scope to the top of the scope stack.
     */
    public static void pushNewScope() {
        scopes.addFirst(new HashSet<>());
    }

    /**
     * Pops the most recently added scope off the scope stack.
     */
    public static void popScope() {
        scopes.pop();
    }

    /**
     * Adds a new Variable instance to the top-most scope in the scope stack, initializing it
     * with the name and type provided by the arguments.
     * 
     * @param identifier the name of the variable being added to the current scope
     * @param type the type of the variable being added to the current scope (integer/object)
     */
    public static void addVariableToCurrentScope(String identifier, Type type) {
        scopes.getFirst().add(new Variable(identifier, type));
    }

    /**
     * Iterates over the variables contained in the stack and returns the Variable instance associated with the identifier,
     * or return null to indicate it hasn't been declared yet.
     * 
     * @param identifier the name of the variable being fetched.
     * @return the Variable object instance whose identifier matches the argument; return 'null' if it hasn't been declared yet.
     */
    public static Variable getVariable(String identifier) {
        Variable variable = null;
        Iterator<Set<Variable>> scopeIterator = scopes.iterator();
        
        while (scopeIterator.hasNext()) {
            Iterator<Variable> variableIterator = scopeIterator.next().iterator();

            while (variableIterator.hasNext()) {
                Variable currentVariable = variableIterator.next();
                if (currentVariable.identifier.equals(identifier)) variable = currentVariable;
            }
        }

        return variable;
    }

    /**
     * Checks if any scope in the stack contains a variable with a name matching the 'identifier' argument
     * 
     * @param identifier the name of the variable being checked for declaration
     * @return true if the variable has been declared, false otherwise.
     */
    public static boolean isInScope(String identifier) {
        return getVariable(identifier) != null;
    }

    /**
     * Checks if the top-most/current scope on the stack contains a variable with a name matching the 'identifier' argument.
     * 
     * @param identifier the name of the variable being checked for declaration in the current scope
     * @return true if the variable has been declared in the current scope, false otherwise.
     */
    public static boolean isInCurrentScope(String identifier) {
        boolean isInCurrentScope = false;
        Set<Variable> currentScope = scopes.peekFirst();
        Iterator<Variable> it = currentScope.iterator();

        while (it.hasNext()) {
            Variable variable = it.next();
            if (variable.identifier.equals(identifier)) isInCurrentScope = true;
        }

        return isInCurrentScope;
    }

    
}
