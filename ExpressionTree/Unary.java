package ExpressionTree;

/**
 * Operator class for unary operations.
 */
public class Unary extends Operator {
    
    /**
     * Constructor which class for super-class constructor.
     * @param Operation operation symbol
     * @param columnNumber column number
     */
    public Unary (char Operation, int columnNumber) {
        super(Operation, columnNumber);
    }
    
    /**
     * Checks if sign is - and changes it accordingly.
     * @return constant node with new value
     */
    @Override
    public Node evaluate() {
        int rightOperand = ((Operand) this.getRight()).getValue();
        if (this.operation == '-') {
            rightOperand = -rightOperand;
        }
        
        return new Constant(rightOperand);
    }
    
}
