import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Executor {
    public static Deque<Map<String, Variable>> scopes;
    public static Deque<Scope> scopeTypes;
    public static Scanner input;
    
    public Executor(String inputFilePath) {
        scopes = new ArrayDeque<>();
        scopeTypes = new ArrayDeque<>();
        input = new Scanner(inputFilePath);
    }

    public static void printScopes() {
        Iterator<Map<String, Variable>> scopeIterator = scopes.iterator();
        System.out.println("START");
        while (scopeIterator.hasNext()) {
            Map<String, Variable> currentScope = scopeIterator.next();
            Set<Map.Entry<String, Variable>> entries = currentScope.entrySet();

            Iterator<Map.Entry<String, Variable>> entryIterator = entries.iterator();

            System.out.println("---------------------------");
            while (entryIterator.hasNext()) {
                Map.Entry<String, Variable> entry = entryIterator.next();
                System.out.print(entry.getKey() + " => ");
                Variable value = entry.getValue();
                if (value.type == Type.INTEGER) {
                    System.out.print(value.int_value + ", ");
                } else {
                    System.out.print(value.obj_value + ", ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------------------");
        System.out.println("END");
    }

    public void run(Procedure procedure) {
        procedure.execute();
    }

    public static Map<String, Variable> getGlobalScope() {
        return scopes.peekLast();
    }

    /**
     * Pushes a new scope to the top of the scope stack.
     */
    public static void pushNewScope(Scope scopeType) {
        scopeTypes.addFirst(scopeType);
        scopes.addFirst(new HashMap<>());
    }

    /**
     * Pops the most recently added scope off the scope stack.
     */
    public static void popScope() {
        scopeTypes.pop();
        scopes.pop();
    }

    public static Scope currentScopeType() {
        return scopeTypes.peekFirst();
    }

    /**
     * Adds a new Variable instance to the top-most scope in the scope stack, initializing it
     * with the name and type provided by the arguments.
     * 
     * @param identifier the name of the variable being added to the current scope
     * @param type the type of the variable being added to the current scope (integer/object)
     */
    public static void addVariableToCurrentScope(String identifier, Type type) {
        scopes.getFirst().put(identifier, new Variable(type));
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
        Iterator<Map<String, Variable>> it = scopes.iterator();
        
        while (it.hasNext()) {
            Map<String, Variable> currentScope = it.next();

            if (currentScope.containsKey(identifier)) {
                variable = currentScope.get(identifier);
                break;
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
        return scopes.peekFirst().containsKey(identifier);
    }
}
