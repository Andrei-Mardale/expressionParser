package ExpressionTree;

/**
 * Simple tree node implementation.
 */
public abstract class Node {
    
    /**
     * left child.
     */
    protected Node left;
    
    /**
     * right child.
     */
    protected Node right;
    
    /**
     * column number - starting position in expression string. 
     * Used when printing errors to output.
     */
    protected int columnNumber;
    
    /**
     * Node constructor.
     * @param columnNumber column number 
     */
    public Node (int columnNumber) {
        this.columnNumber = columnNumber;
    }
    
    /**
     * Left node setter.
     * @param left left value
     */
    public void setLeft (Node left) {
        this.left = left;
    }
    
    /**
     * Right node setter.
     * @param right right value
     */
    public void setRight (Node right) {
        this.right = right;
    }
    
    /**
     * Left node getter.
     * @return left value
     */
    public Node getLeft () {
        return this.left;
    }
    
    /**
     * Right node getter.
     * @return right value
     */
    public Node getRight () {
        return this.right;
    }
    
    /**
     * Column number getter.
     * @return column number
     */
    public int getColumn () {
        return this.columnNumber;
    }

    /**
     * Checks if all variable (including current node) are declared.
     * It does this by doing an in-order depth-first search.
     * @return first undeclared variable  in expression or null
     */
    public Variable checkVariables () {
        //check left
        if (left != null) {
            Variable check = left.checkVariables();
            if (check != null) return check;
        }
        
        //check root
        if (this instanceof Variable) {
            if (!((Variable) this).initialized()) {
                return (Variable) this;
            }
        }
        
        //check right
        if (right != null) {
            Variable check = right.checkVariables();
            if (check != null) return check;
        }
        
        return null; //no undeclared found for this sub-tree
    }
}
