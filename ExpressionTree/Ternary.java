package ExpressionTree;

/**
 * Operator class for ternary operations (>, ?, :).
 */
public class Ternary extends Operator {
    
    /**
     * Constructor which calls for super-class constructor.
     * @param Operation operation symbol
     * @param columnNumber column number
     */
    public Ternary (char Operation, int columnNumber) {
        super(Operation, columnNumber);
    }

    /**
     * Evaluates all leaves and performs logical check.
     * @return constant node with returned value from ternary operation
     */
    @Override
    public Node evaluate() {
        //check if value if false is an expression
        if (this.right instanceof Operator) {
            this.right = ((Operator) this.right).evaluate(); //evaluate if true
        }
        
        //check if value if true is an expression
        if (this.left.right instanceof Operator) {
            this.left.right = ((Operator) this.left.right).evaluate(); //evaluate if true
        }
        
        //easier referecing for compare sign
        Node ternaryCompare = this.left.left;
        
        //check if left or right are operators, and evaluate them if true
        ((Operator) ternaryCompare).evaluateLeaves(); 
        
        //get numerical values
        int compareLeft = ((Operand) ternaryCompare.left).getValue();
        int compareRight = ((Operand) ternaryCompare.right).getValue();
        
        //perform logical operation
        //if true return value if true
        if (compareLeft > compareRight) {
            return new Constant(((Operand) this.left.right).getValue());
        }
        
        //if false return value if false
        return new Constant(((Operand) this.right).getValue());
    }
    
     
}
