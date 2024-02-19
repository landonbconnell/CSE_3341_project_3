public class Variable {
    String identifier;
    Type type;
    int int_value;
    int[] obj_value;

    public Variable(String identifier, Type type) {
        this.identifier = identifier;
        this.type = type;
        this.int_value = 0;
        this.obj_value = null;
    }
}
