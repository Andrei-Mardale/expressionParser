package Main;

import static ExpressionParser.ExpressionParser.expressionToTree;
import ExpressionTree.ExpressionTree;
import ExpressionTreePrinter.ExpressionTreePrinter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * Container class for main method.
 */
public class Main {
    /**
     * Opens input/output stream and parses expressions, performs syntactical analysis and evaluates them. 
     * @param args command line arguments containing input file name
     */
    public static void main (String[] args) {
        if (args.length < 1) {
            System.out.println("File not specified.");
            return;
        }
        
        //in/out streams
        BufferedReader in;
        PrintWriter parseOut, semanticsOut, evaluateOut;
        
        try {
            in = new BufferedReader(new FileReader(args[0]));
            parseOut = new PrintWriter(new FileWriter(args[0] + "_pt"));
            semanticsOut = new PrintWriter(new FileWriter(args[0] + "_sa"));
            evaluateOut = new PrintWriter (new FileWriter(args[0] + "_ee"));
            
            String s; //expression string
            int i = 1; //line number
            
            //while there is a line to be read, read it
            while ((s = in.readLine()) != null) {
                //parse expression
                ExpressionTree t = expressionToTree(s, i);
                
                //print tree levels
                ExpressionTreePrinter.printTree(t, parseOut);
                
                //print semantical analysis
                if (t.checkSemantics(semanticsOut)) {
                    //if analysis returns true then start evaluation
                    t.evaluate(evaluateOut);
                } else {
                    //else print error and go to next line
                    evaluateOut.println("error");
                }
                
                ++i; //increase line number
            }
            
            //close everything
            in.close();
            parseOut.close();
            semanticsOut.close();
            evaluateOut.close();
        } catch (IOException e) {
            System.out.println("Could not open file");
        }
    }
}
