package ExpressionTree;

/**
 * Operator class for '+' and '-'.
 * 
 */
public class Addition extends Operator {
    /**
     * Calls construct for super-class.
     * @param Operation operation symbol
     * @param columnNumber column number
     */
    public Addition (char Operation, int columnNumber) {
        super(Operation, columnNumber);
    }

    /**
     * Adds or subtracts left and right leaves.
     * @return constant node with new value
     */
    @Override
    public Node evaluate() {
        this.evaluateLeaves();
        
        int leftOperand = ((Operand) this.left).getValue();
        int rightOperand = ((Operand) this.right).getValue();
        
        if (this.operation == '-') {
            rightOperand = -rightOperand;
        }
        
        return new Constant(leftOperand + rightOperand);        
    }
}
