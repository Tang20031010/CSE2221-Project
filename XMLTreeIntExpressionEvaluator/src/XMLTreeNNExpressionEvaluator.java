import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Coney Tang
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
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
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        /*
         * Declare NaturalNumber value to store value
         */
        NaturalNumber value = new NaturalNumber2();
        /*
         * If the label is number, value will be its attribute "value" value
         */
        if (exp.label().equals("number")) {
            /*
             * Check if the number is not negative
             */
            int temp = Integer.parseInt(exp.attributeValue("value"));
            /*
             * If the number is negative, report error
             */
            if (temp < 0) {
                Reporter.fatalErrorToConsole("Value cannot be less than 0");
            }
            /*
             * Change string to NaturalNumber
             */
            value.setFromString(exp.attributeValue("value"));
            /*
             * If the xml label is not a number, do recursion
             */
        } else {
            /*
             * If xml label is plus, value will be its child(0) add child(1)
             */
            if (exp.label().equals("plus")) {
                /*
                 * Declare and initialize tempPlus to store the value of
                 * child(0)
                 */
                NaturalNumber tempPlus = new NaturalNumber2(
                        evaluate(exp.child(0)));
                /*
                 * tempPlus add the value gained from child(1)
                 */
                tempPlus.add(evaluate(exp.child(1)));
                /*
                 * value will be tempPlus
                 */
                value.copyFrom(tempPlus);
                /*
                 * If xml label is minus, value will be its child(0) subtract
                 * child(1)
                 */
            } else if (exp.label().equals("minus")) {
                /*
                 * Declare and initialize tempMinus to store the value of
                 * child(0)
                 */
                NaturalNumber tempMinus = new NaturalNumber2(
                        evaluate(exp.child(0)));
                /*
                 * Declare and initialize tempMinus1 to store the value of
                 * child(1)
                 */
                NaturalNumber tempMinus2 = new NaturalNumber2(
                        evaluate(exp.child(1)));
                /*
                 * If child(1)'s value is greater than child(0)'s value, report
                 * an error and end the program
                 */
                if (tempMinus2.compareTo(tempMinus) > 0) {
                    Reporter.fatalErrorToConsole(
                            "The subtrahend cannot be greater than minuend");
                }
                /*
                 * tempMinus subtract tempMinus2
                 */
                tempMinus.subtract(tempMinus2);
                /*
                 * value will be tempMinus
                 */
                value.copyFrom(tempMinus);
                /*
                 * If xml label is divide, value will be its child(0) divide
                 * child(1)
                 */
            } else if (exp.label().equals("divide")) {
                /*
                 * Declare and initialize tempDivide to store the value of
                 * child(0)
                 */
                NaturalNumber tempDivide = new NaturalNumber2(
                        evaluate(exp.child(0)));
                /*
                 * Declare and initialize tempDivide2 to store the value of
                 * child(0)
                 */
                NaturalNumber tempDivide2 = new NaturalNumber2(
                        evaluate(exp.child(1)));
                /*
                 * If child(1)'s value is 0, report an error and end the program
                 */
                if (tempDivide2.isZero()) {
                    Reporter.fatalErrorToConsole("The divisor cannot be zero");
                }
                /*
                 * tempDivide divide tempDivide2
                 */
                tempDivide.divide(evaluate(exp.child(1)));
                /*
                 * value will be tempMinus
                 */
                value.copyFrom(tempDivide);
                /*
                 * If xml label is times, value will be its child(0) multiply
                 * child(1)
                 */
            } else if (exp.label().equals("times")) {
                /*
                 * Declare and initialize tempTimes to store the value of
                 * child(0)
                 */
                NaturalNumber tempTimes = new NaturalNumber2(
                        evaluate(exp.child(0)));
                /*
                 * tempTimes multiply the value gained from child(1)
                 */
                tempTimes.multiply(evaluate(exp.child(1)));
                /*
                 * value will be tempTimes
                 */
                value.copyFrom(tempTimes);
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
