package ExpressionTree;

/**
 * Operator class for *.
 * 
 */
public class Multiplication extends Operator {
    /**
     * Constructor which calls for super-class constructor.
     * @param Operation
     * @param columnNumber 
     */
    public Multiplication (char Operation, int columnNumber) {
        super(Operation, columnNumber);
    }
    
    /**
     * Multiplicates left and right leaves
     * @return constant node with new value
     */
    @Override
    public Node evaluate() {
        this.evaluateLeaves();
        
        int leftOperand = ((Operand) this.getLeft()).getValue();
        int rightOperand = ((Operand) this.getRight()).getValue();
        
        return new Constant(leftOperand * rightOperand);   
    }
    
}
