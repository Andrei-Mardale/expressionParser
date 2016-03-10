package ExpressionTree;

/**
 * Class which holds only an integer value.
 * Is always a leaf.
 */
public class Constant extends Operand {
    /**
     * Integer value for leaf.
     */
    private final int value;
    
    /**
     * Basic constructor which build leaf from a string representation of a number.
     * @param value string representation of numerical value
     * @param columnNumber column number
     */
    public Constant (String value, int columnNumber) {
        super(columnNumber);
        this.value = Integer.valueOf(value);
    }
    
    /**
     * Constructor used after evaluating an expression. Column number does not need to be stored.
     * -1 is still stored in column number as a mean for debugging
     * @param value numerical value obtained after evaluation
     */
    public Constant (int value) {
        super(-1);
        this.value = value;        
    }
    
    /**
     * Getter for value.
     * @return numerical value
     */
    @Override
    public int getValue () {
        return this.value;
    }
}
