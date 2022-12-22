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
public final class Newton1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton1() {
    }

    /**
     * @return the estimation of square root of x
     * @param x
     *            positive number to compute square root of
     */
    private static double sqrt(double x) {
        /*
         * Declare and initialize the guess for the square root of x
         */
        double guess = x;
        /*
         * Declare and initialize the ε
         */
        final double standard = 1.0E-4;
        /*
         * Repeatedly update guess with the formula provided by Newton Iteration
         */
        while (Math.abs(guess * guess - x) / x >= standard * standard) {
            guess = (guess + x / guess) / 2;
        }
        return guess;
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
         * Ask user for decision
         */
        out.print("Do you wish to calculate another square root: ");
        String userInput = in.nextLine();
        /*
         * Declare and initialize temp for storing data temporarily
         */
        double temp = 0;
        /*
         * Repeatedly asking user if they want to calculate another square root
         * and proceed when user input y
         */
        while (userInput.equals("y")) {
            /*
             * Ask user for a number
             */
            out.print("Enter a number: ");
            double userInputNum = in.nextDouble();
            /*
             * print out the estimate square root of that number
             */
            temp = sqrt(userInputNum);
            out.println(temp);
            out.println(" ");
            /*
             * Ask user for their decision
             */
            out.print("Do you wish to calculate another square root: ");
            userInput = in.nextLine();
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
