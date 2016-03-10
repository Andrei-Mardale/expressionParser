package ExpressionTree;

/**
 * Node class which is a wrapper for any operation.
 * It has methods for priority determination based on operation character.
 * These are used mainly when parsing expression strings.
 */
public abstract class Operator extends Node {
    /**
     * Operation symbol.
     */
    protected char operation;
    
    /**
     * Operation priority value (lower value means higher precedence).
     */
    protected final int priority;
    
    //precedence definition
    
    /**
     * Priority for unary operations.
     */
    public static final byte UNARY = 0; // unary + or -
    
    
    public static final byte MULTIPLICATION = 1; // *
    
    /**
     * Priority for addition; sum or subtraction.
     */
    public static final byte ADDITION = 2; // + or -
    
    /**
     * Priority for the compare sign in the ternary operation.
     */
    public static final byte TERNARY_COMPARE = 3; // >
    
    /**
     * Priority for the "?" symbol in the ternary operation.
     */
    public static final byte TERNARY_FIRST = 4; // ?
    
    /**
     * Priority for the ":" symbol in the ternary operation.
     */
    public static final byte TERNARY_SECOND = 5; // :
    
    /**
     * Priority for assignment operation.
     */
    public static final byte ASSIGNMENT = 6; // =
    
    /**
     * Basic constructor.
     * @param operation operation symbol
     * @param columnNumber column number
     */
    public Operator (char operation, int columnNumber) {
        super(columnNumber);
        this.operation = operation;
        this.priority = setPriority();
    }
    
    /**
     * Operation getter
     * @return operation symbol
     */
    public char getOperation () {
        return this.operation;
    }
    
    /**
     * Priority value getter
     * @return priority value
     */
    public int getPriority () {
        return this.priority;
    }
    
    /**
     * Wrapper for constructors; they should not be called directly.
     * This check the given symbol and creates the according operation instance.
     * @param operation operation symbol
     * @param columnNumber column number
     * @return node instance with the correct operation
     */
    public static Operator createOperator (char operation, int columnNumber) {
        switch (operation) {
            case '#': return new Unary (operation, columnNumber);
            case '~': return new Unary (operation, columnNumber);
            case '+': return new Addition (operation, columnNumber);
            case '-': return new Addition (operation, columnNumber);
            case '*': return new Multiplication (operation, columnNumber);
            case '=': return new Assignment (operation, columnNumber);
            default: return new Ternary (operation, columnNumber);
        }
    }
    
    /**
     * Evaluates a node in tree by performing the according operation.
     * @return new constant node if operation is not assignment, or the same node with variable value changed if called for tree root
     */
    public abstract Node evaluate ();
    
    /**
     * Calls evaluate method for both leaves (if they are not null).
     * This way it leaves them evaluated as constants or variables which can be used to perform the node operation.
     */
    protected void evaluateLeaves () {
        if (this.left != null && this.left instanceof Operator) {
            this.left = ((Operator) this.left).evaluate();
        }
        
        if (this.right != null && this.right instanceof Operator) {
            this.right = ((Operator) this.right).evaluate();
        }
    }
    
    /**
     * Method called by the constructor.
     * Sets the priority for current instance, and modifies sign for printing if needed.
     * @return priority value or dummy value if symbol is not an operation (eg: "(")
     */
    private int setPriority () {
        switch (operation) {
            case '#': {
                this.operation = '+';
                return UNARY;
            }
            case '~': {
                this.operation = '-';
                return UNARY;
            }
            case '*': return MULTIPLICATION;
            case '+': return ADDITION;
            case '-': return ADDITION;
            case '>': return TERNARY_COMPARE;
            case '?': return TERNARY_FIRST;
            case ':': return TERNARY_SECOND;
            case '=': return ASSIGNMENT;
        }
        
        return Integer.MAX_VALUE; //should never get here (unless '(' or ')' )
    }
}
