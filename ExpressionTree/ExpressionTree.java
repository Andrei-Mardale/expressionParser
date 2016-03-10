package ExpressionTree;

import java.io.PrintWriter;

/**
 * Represents the tree built from an expression.
 * 
 */
public class ExpressionTree {
    /**
    * Root of the expression tree.
    */
    private final Node root;
    
    /**
    * Source expression line number.
    * Used when printing errors (if any).
    */
    private final int lineNumber;

    /**
     * Creates new expression tree with given root and line number.
     * @param root root of the expression tree created by parsing the source expression
     * @param lineNumber line number for the source expression
     */
    public ExpressionTree (Node root, int lineNumber) {
        this.root = root;
        this.lineNumber = lineNumber;
    }
    
    /**
     * Getter for root
     * @return root value
     */
    public Node getRoot () {
        return this.root;
    }
    
    /**
     * Method which checks if the parsed expression was semantically correct
     * @param out output file. If everything is all right then prints "Ok!", 
     * else an error message will be printed.
     * @return true if semantically correct, else false
     */
    public boolean checkSemantics (PrintWriter out) {
        //if left is not variable, then it will be an operator or constant
        //if operator then it will be evaluated to a constant => error
        if (!(root.left instanceof Variable)) {
            out.println("membrul stang nu este o variabila la linia " +
                        this.lineNumber + 
                        " coloana 1");
            return false;
        }
        
        //check if there are any undeclared variables at the right of '=' root
        Variable undeclaredVar = root.right.checkVariables();
        
        //if yes then print error message
        if (undeclaredVar != null) {
            out.println(undeclaredVar.getName() + 
                        " nedeclarata la linia " +
                        this.lineNumber + 
                        " coloana " +
                        (undeclaredVar.getColumn() + 1));
            return false;
        }
        
        //if no error found print ok and return true
        out.println("Ok!");
        return true;
    }
    
    /**
     * Evaluates the expression tree. 
     * It is always assumed that it is semantically correct.
     * Should be preceded by checkSemantics().
     * It is always assumed that the root will be an '=' operator.
     * @param out output file where the result will be printed
     */
    public void evaluate (PrintWriter out) {
        ((Operator) this.root).evaluate();
        
        out.println(((Variable) this.root.left).getName() + 
                    "=" +
                    ((Operand) this.root.right).getValue());
    }
    
}
