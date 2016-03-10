package ExpressionParser;

import ExpressionTree.Constant;
import ExpressionTree.ExpressionTree;
import ExpressionTree.Node;
import ExpressionTree.Operator;
import static ExpressionTree.Operator.createOperator;
import ExpressionTree.Variable;
import java.util.Stack;

/**
 * Non-instantiable class which provides methods for parsing an expression to a tree.
 */
public class ExpressionParser {
    /**
     * Variable which holds the length of the given expression for a single expressionToTree() call.
     */
    private static int expressionLength;
    
    /**
     * Variable which is modified when parsing variable names or constant.
     * Those methods return the new position, and modify this variable as a side-effect.
     */
    private static Node currentNode;
    
    /**
     * Flag variable which is true after encountering an operator.
     * Makes it easier to check if a new '+' or '-' variable is unary or binary.
     */
    private static boolean unaryCheck;
    
    /**
     * Operand stack which will contain leaves and/or operator with leaves attached.
     * Is always emptied after parsing in order to be ready for next call.
     */
    private static final Stack<Node> operandStack = new Stack<>();
    
    /**
     * Operator stack which will hold only operators.
     * Is always emptied after parsing in order to be ready for next call.
     */
    private static final Stack<Operator> operatorStack = new Stack<>();
    
    /**
     * Dummy constructor to prevent instantiating.
     */
    private ExpressionParser() {}
    
    /**
     * Build a tree from an expression.
     * Uses two stacks (operand and operator).
     * @param expression string as it is read from file
     * @param lineNumber line number used in tree for various output messages
     * @return tree for given expression
     */
    public static ExpressionTree expressionToTree (String expression, int lineNumber) {        
        int i = 0; //current index
        
        expressionLength = expression.length(); //init expression length
        unaryCheck = true; //if first character is an operator then it must be unary
        
        while (i < expressionLength) {
            char c = expression.charAt(i); //shorter lines :D
            
            
            
            if (isLetter(c)) { //if letter found then parse variable name
                i = parseVariable(expression, i);
                operandStack.push(currentNode);
                unaryCheck = false;
            } else if (isDigit(c)) { //if digit found then parse constant value
                i = parseConstant(expression, i);
                operandStack.push(currentNode);
                unaryCheck = false;
            } else if (c == '(') { //sub-expression found
                operatorStack.push(createOperator('(', i));
                unaryCheck = true;
                ++i;
            } else if (c == ')') { //sub expression ended
                while (operatorStack.peek().getOperation() != '(') {
                    operandStack.push(createOperationNode());
                }
                
                operatorStack.pop();
                ++i;
                unaryCheck = false;
            } else { //operator found
                if (unaryCheck && (c == '-' || c =='+')) { //check if operator  is unary
                    c = (c == '-') ? '~' : '#';
                    operatorStack.push(createOperator(c, i)); //always add it at top of stack (greatest priority)
                } else { //non-unary operator
                    Operator currOperator = createOperator(expression.charAt(i), i);
                    
                    //while there is a greater priority operator in the stack, extract it and create sub-expression
                    while (!operatorStack.empty() && 
                            currOperator.getPriority() >= operatorStack.peek().getPriority()) {
                        operandStack.push(createOperationNode());                        
                    }
                    operatorStack.push(currOperator);
                }
                unaryCheck = true;
                ++i;
            }
            
        }
        
        //extract all remaining operators
        while (!operatorStack.empty()) {
            operandStack.push(createOperationNode());
        }
        
        //after the algorithm finishes, all that is left is the root in the operand stack
        ExpressionTree t = new ExpressionTree(operandStack.pop(), lineNumber);
        
        operandStack.clear(); //shouldn't be necessary, but let's be careful
        
        return t;
    }
    
    /**
     * Pops operator stack and two (or one if unary) operands from the operand stack which will be attached as leaves.
     * @return operator node with leaves
     */
    private static Node createOperationNode() {
        Operator op = operatorStack.pop();
        op.setRight(operandStack.pop());
        if (op.getPriority() != Operator.UNARY) {
            op.setLeft(operandStack.pop());
        }
        
        return op;
    }
    
    /**
     * Checks if character  is letter
     * @param c input character
     * @return true if letter
     */
    private static boolean isLetter (char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
    
    /**
     * Checks if character is digit
     * @param c input character
     * @return true if digit
     */
    private static boolean isDigit (char c) {
        return (c >= '0') && (c <= '9');
    }
    
    /**
     * Parses a variable named and creates it's node.
     * A variable can be any sequence of alphanumerical characters starting with a letter.
     * @param expression input expression
     * @param offset starting position
     * @return the index that is reached after parsing variable name
     */
    private static int parseVariable (String expression, int offset) {
        int last = offset;
        
        while (last < expressionLength && (
               (isLetter(expression.charAt(last)) ||
                isDigit(expression.charAt(last))))) {
            ++last;
        }
        
        currentNode = new Variable(expression.substring(offset, last), offset);
        
        return last;
    }
    
    /**
     * Parses a constant value and creates it's node
     * @param expression input expression
     * @param offset staring position
     * @return the index that is reached after parsing constant value
     */
    private static int parseConstant (String expression, int offset) {
        int last = offset;
        
        while (last < expressionLength && isDigit(expression.charAt(last))) {
            ++last;
        }
        
        currentNode = new Constant(expression.substring(offset, last), offset);
        
        return last;
    }
}
