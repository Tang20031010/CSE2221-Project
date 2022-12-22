import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Coney Tang
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        /*
         * Declare and initialize integer value to store value
         */
        int value = 0;
        /*
         * If the xml label is number, value will be its attribute "value" value
         */
        if (exp.label().equals("number")) {
            /*
             * Change the string into integer
             */
            value = Integer.parseInt(exp.attributeValue("value"));
            /*
             * If the xml label is not a number, do recursion
             */
        } else {
            /*
             * If xml label is plus, value will be its child(0) plus child(1)
             */
            if (exp.label().equals("plus")) {
                value = evaluate(exp.child(0)) + evaluate(exp.child(1));
                /*
                 * If xml label is minus, value will be its child(0) minus
                 * child(1)
                 */
            } else if (exp.label().equals("minus")) {
                value = evaluate(exp.child(0)) - evaluate(exp.child(1));
                /*
                 * If xml label is divide, value will be its child(0) divide
                 * child(1)
                 */
            } else if (exp.label().equals("divide")) {
                value = evaluate(exp.child(0)) / evaluate(exp.child(1));
                /*
                 * If xml label is times, value will be its child(0) times
                 * child(1)
                 */
            } else if (exp.label().equals("times")) {
                value = evaluate(exp.child(0)) * evaluate(exp.child(1));
            }
        }
        /*
         * return value
         */
        return value;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }
}
