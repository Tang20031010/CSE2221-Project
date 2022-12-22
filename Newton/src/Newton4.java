import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Newton4 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton4() {
    }

    /**
     * @return the estimation of square root of x; when x == 0, returns 0
     * @param x
     *            non-negative number to compute square root of
     * @param standard
     *            number for ε
     *
     */
    private static double sqrt(double x, double standard) {
        /*
         * Check if user's positive input is 0 and return 0 if it is
         */
        if (x == 0) {
            return 0;
        } else {
            /*
             * Declare and initialize the guess for the square root of x
             */
            double guess = x;
            /*
             * Repeatedly update guess with the formula provided by Newton
             * Iteration
             */
            while (Math.abs(guess * guess - x) / x >= standard * standard) {
                guess = (guess + x / guess) / 2;
            }
            return guess;
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Ask for user input of ε
         */
        out.print("Please set a ε: ");
        final double standardInput = in.nextDouble();
        /*
         * Ask user for a number
         */
        out.print("Enter a number: ");
        double userInputNum = in.nextDouble();
        /*
         * Declare and initialize temp for storing data temporarily
         */
        double temp = 0;
        /*
         * Repeatedly ask the user to enter a number and proceed only when
         * number is not negative
         */
        while (userInputNum >= 0) {
            /*
             * print out the estimate square root of that number
             */
            temp = sqrt(userInputNum, standardInput);
            out.println(temp);
            out.println(" ");
            /*
             * Ask user for a number
             */
            out.print("Enter a number: ");
            userInputNum = in.nextDouble();
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
