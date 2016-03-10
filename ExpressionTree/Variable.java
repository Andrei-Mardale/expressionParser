package ExpressionTree;

import java.util.HashMap;

/**
 * Node class for variables.
 */
public class Variable extends Operand {
    /**
     * Variable name
     */
    private final String name;
    
    /**
     * Static hash with (variable name, variable value).
     * Used as a mean of determining if a variable is initialized or not.
     */
    private static final HashMap<String, VariableValue> vars = new HashMap<>();
    
    /**
     * Internal class used to store information in hash.
     */
    private class VariableValue {
        /**
         * Actual numerical value.
         */
        int value;
        
        /**
         * Flag used to determine if a numerical value has been assigned to the variable yet.
         */
        boolean initialized = false;
        
        /**
         * Basic constructor
         * @param value numerical value
         */
        public VariableValue (int value) {
            this.value = value;
        }
        
        /**
         * Assigns new value to variable and sets initialized flag to true.
         * @param value numerical value
         */
        public void set (int value) {
            this.value = value;
            this.initialized = true;
        }
    }
    
    /**
     * Constructor for variable node.
     * Same variable can be present in multiple nodes but can only hold one value which is stored in hash.
     * @param name variable name
     * @param columnNumber column number
     */
    public Variable (String name, int columnNumber) {
        super(columnNumber);
        this.name = name;
        
        //add new variable if needed
        //initial value is set to Integer.MIN_VALUE but is never used until initialization
        vars.putIfAbsent(this.name, new VariableValue(Integer.MIN_VALUE));
    }
    
    /**
     * Assigns value to variable.
     * @param newValue 
     */
    public void setValue (int newValue) {
        vars.get(this.name).set(newValue);        
    }
    
    /**
     * Getter for variable value.
     * @return numerical value
     */
    @Override
    public int getValue () {
        return vars.get(this.name).value;
    }
    
    /**
     * Getter for name.
     * @return variable name
     */
    public String getName () {
        return name;
    }
    
    /**
     * Checks if variable has been initialized(declared) or not.
     * @return true if yes
     */
    public boolean initialized () {
        return vars.get(this.name).initialized;
    }
}
