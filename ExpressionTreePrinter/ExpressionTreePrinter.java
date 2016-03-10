/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExpressionTreePrinter;

import ExpressionTree.ExpressionTree;
import ExpressionTree.Operator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Non-instantiable class which has methods for printing all the levels of an expression tree.
 */
public class ExpressionTreePrinter {
    /**
     * Flag variable which is true when there are still expression nodes on the current level.
     */
    private static boolean nonTerminalLevel;
    
    /**
     * Dummy constructor to prevent instantiating.
     */
    private ExpressionTreePrinter () {}
    
    /**
     * Internal class which associates a symbol to it's node.
     */
    private static class Symbol {
        /**
         * Node symbol (E, T, N, F, or operation symbol).
         */
        private final char s;
        
        /**
         * Node associated with symbol.
         */
        private Operator currentOp = null;
        
        /**
         * Parent node.
         */
        private Operator parentOp = null;
        
        /**
         * Constructor for leaf.
         * @param s leaf symbol
         */
        public Symbol (char s) {
            this.s = s;
        }
        
        /**
         * Constructor for non-leaves.
         * @param s operation symbol for current level
         * @param currentOp associated node
         * @param parentOp parent node
         */
        public Symbol (char s, Operator currentOp, Operator parentOp) {
            this.s = s;
            this.currentOp = currentOp;
            this.parentOp = parentOp;
            nonTerminalLevel = true; //if such a node is created then terminal level is not reached
        }
        
        /**
         * Expands a non-leaf symbol to a new expression.
         * @return expanded expression
         */
        private ArrayList<Symbol> expand () {
            ArrayList<Symbol> newExpression = new ArrayList<>();
            
            //add operation symbol
            newExpression.add(0, new Symbol(currentOp.getOperation()));
            
            int priority = currentOp.getPriority();
            
            char nonExpressionSymbol; //symbol used for child leaves
            
            //set nonExpressionSymbol accordingly
            if (priority == Operator.MULTIPLICATION) {
                nonExpressionSymbol = 'F';
            } else if (priority >= Operator.TERNARY_COMPARE && priority <= Operator.TERNARY_SECOND) {
                nonExpressionSymbol = 'N';
            } else  {
                nonExpressionSymbol = 'T';
            }
            
            //check if left is not null
            if (currentOp.getLeft() != null) {
                Symbol left;
               
                if (currentOp.getLeft() instanceof Operator) {
                    // if left is operator create new E symbol
                    left = new Symbol ('E', (Operator) currentOp.getLeft(), currentOp);                
                } else {
                    //else create new T/F/N symbol
                    left = new Symbol (nonExpressionSymbol); 
                }
                newExpression.add(0, left);
            }
            
            //right must always be not null for an operator node
            Symbol right;
            if (currentOp.getRight() instanceof Operator) {
                //if operator create new E symbol
                right = new Symbol ('E', (Operator) currentOp.getRight(), currentOp);
                
            } else {
                //else create new T/F/N symbol
                right = new Symbol (nonExpressionSymbol);
            }
            newExpression.add(right);
                
            //check if parantheses need to be added
            if (parentOp != null && priority > parentOp.getPriority() ||
                priority == Operator.UNARY ||
                priority == Operator.TERNARY_COMPARE) {
                newExpression.add(0, new Symbol('('));
                newExpression.add(new Symbol(')'));
            }
            
            return newExpression;
        }
    }
    
    /**
     * Prints all levels of an expression tree to output stream.     * 
     * @param t expression tree
     * @param out output stream
     * @throws IOException 
     */
    public static void printTree (ExpressionTree t, PrintWriter out) throws IOException {
        ArrayList<Symbol> exp = new ArrayList<>();
        //add root
        exp.add(new Symbol('E', (Operator) t.getRoot(), null));
        
        //print root to output
        printSymbolArray(exp, out);
        
        //while terminal level is not reached print find all non-terminal symbols (E) and expand them
        while (nonTerminalLevel) {
            nonTerminalLevel = false;
            
            int i = 0;
            while (i < exp.size()) {
                if (exp.get(i).s == 'E') {
                    //if non-terminal symbol found, expand it and jump over expanded expression
                    //this way only current level E symbols will be exanded
                    i = replace(exp, i, exp.get(i).expand());
                } else {
                    ++i;
                }
            }
            
            //after symbol expansion print tree to output
            printSymbolArray(exp, out);
        }       
    }
   
    /**
     * Inserts a new (expanded) symbol expression into another expression starting form offset.
     * The new position will be after the expanded expression.
     * @param exp whole expression
     * @param offset insertion index
     * @param newExp expression to be inserted
     * @return new index
     */
    private static int replace (ArrayList<Symbol> exp, int offset, ArrayList<Symbol> newExp) {
        exp.remove(offset);
        
        int newPos = offset + newExp.size();
        
        //pop elements from new expression and insert them in main expression
        int i = 0;
        while (!newExp.isEmpty()) {
            exp.add(offset + i,  newExp.remove(0));
            ++i;
        }
        
        return newPos;
    }  
    
    /**
     * Prints an ArrayList of symbols to output stream
     * @param exp expression array
     * @param out output stream
     * @throws IOException 
     */
    private static void printSymbolArray (ArrayList<Symbol> exp, PrintWriter out) throws IOException {
        for (int i = 0; i < exp.size(); ++i) {
            out.print(exp.get(i).s);
        }
        
        out.println();
    }
}
