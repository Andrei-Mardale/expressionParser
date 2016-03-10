package ExpressionTree;

/**
 * Node sub-class for operands.
 */
public abstract class Operand extends Node {
    
    /**
     * Constructor which calls for super-class constructor
     * @param columnNumber column number
     */
    public Operand (int columnNumber) {
        super(columnNumber);
    }
    
    /**
     * Getter for operand value
     * @return numerical value
     */
    public abstract int getValue();    
}
