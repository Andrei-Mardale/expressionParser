package ExpressionTree;

/**
 * Operator class for '='
 * @author aim
 */
public class Assignment extends Operator {
    /**
     * Class constructor for super-class
     * @param Operation operation symbol
     * @param columnNumber column number
     */
    public Assignment (char Operation, int columnNumber) {
        super(Operation, columnNumber);
    }

    /**
     * Assigns right constant value to left variable
     * @return returns same node (this is not evaluated to a single node but to a tree with '=' root and 2 leaves)
     */
    @Override
    public Node evaluate() {
        this.evaluateLeaves();
        
        ((Variable) this.left).setValue(((Operand) this.right).getValue());
        
        return this;
    }
    
    
}
