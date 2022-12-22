import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * This program will ask user for a non-negative constant and four non-negative
 * and not one real numbers and use the de Jager formula to approximate the
 * constant and calculate the smallest relative error.
 *
 * @author Coney Tang
 *
 */
public final class ABCDGuesser1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * a positive real number. Returns the number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        /*
         * Declare and initialize variables for storing a string and a real
         * number later
         */
        String inputPositiveDouble = "";
        double inputPositiveDouble1 = 0.0;
        /*
         * Repeatedly ask user for a positive real number
         */
        do {
            out.println("Please give a positive real number: ");
            inputPositiveDouble = in.nextLine();
            if (FormatChecker.canParseDouble(inputPositiveDouble)) {
                inputPositiveDouble1 = Double.parseDouble(inputPositiveDouble);
            }
        } while (inputPositiveDouble1 <= 0);
        return inputPositiveDouble1;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        /*
         * Declare and initialize variables for storing a string and a real
         * number later
         */
        String inputPositiveNotOne = "";
        double inputPositiveNotOne1 = 0.0;
        /*
         * Repeatedly ask user for a positive and not one real number
         */
        do {
            out.println("Please give a positive and not one real number: ");
            inputPositiveNotOne = in.nextLine();
            if (FormatChecker.canParseDouble(inputPositiveNotOne)) {
                inputPositiveNotOne1 = Double.parseDouble(inputPositiveNotOne);
            }
        } while (inputPositiveNotOne1 <= 0 || inputPositiveNotOne1 == 1);
        return inputPositiveNotOne1;
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
         * Call the first method to get a constant from user; declare and
         * initialize num to store the return value
         */
        double num = getPositiveDouble(in, out);
        /*
         * Call the second method four times to get w,x,y,z in the formula from
         * user; declare and initialize w,x,y,z to store the return values
         */
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);
        /*
         * Declare and initialize an array to store the 17 numbers
         */
        final double[] seventeenNum = { -5.0, -4.0, -3.0, -2.0, -1.0, -1.0 / 2,
                -1.0 / 3, -1.0 / 4, 0.0, 1.0 / 4, 1.0 / 3, 1.0 / 2, 1.0, 2.0,
                3.0, 4.0, 5.0 };
        /*
         * Declare and initialize length to store the length of the array
         */
        int length = seventeenNum.length;
        /*
         * Declare and initialize relativeError to store the value of the
         * relative error
         */
        double relativeError = (Math.abs(Math.pow(w, seventeenNum[0])
                * Math.pow(x, seventeenNum[0]) * Math.pow(y, seventeenNum[0])
                * Math.pow(z, seventeenNum[0]) - num)) / num;
        /*
         * Declare and initialize approxNum and temp for later storage of value
         */
        double approxNum = 0.0;
        double temp = 0.0;
        /*
         * Declare and initialize countA, countB, countC, and countD to later
         * count the number of times the four loops repeat itself
         */
        int countA = 0, countB = 0, countC = 0, countD = 0;
        /*
         * Declare and initialize tempA, tempB, tempC, and tempD for storage of
         * different elements in the array
         */
        double tempA = 0.0, tempB = 0.0, tempC = 0.0, tempD = 0.0;
        /*
         * Iterate through the array to find the four elements in array that
         * constitute approximated number with least relative error
         */
        while (countD < length) {
            while (countC < length) {
                while (countB < length) {
                    while (countA < length) {
                        temp = Math.abs((Math.pow(w, seventeenNum[countA])
                                * Math.pow(x, seventeenNum[countB])
                                * Math.pow(y, seventeenNum[countC])
                                * Math.pow(z, seventeenNum[countD]) - num))
                                / num;
                        if (temp < relativeError) {
                            relativeError = temp;
                            tempA = seventeenNum[countA];
                            tempB = seventeenNum[countB];
                            tempC = seventeenNum[countC];
                            tempD = seventeenNum[countD];
                            approxNum = Math.pow(w, tempA) * Math.pow(x, tempB)
                                    * Math.pow(y, tempC) * Math.pow(z, tempD);
                        }
                        countA++;
                    }
                    countB++;
                    countA = 0;
                }
                countC++;
                countB = 0;
            }
            countD++;
            countC = 0;
        }
        /*
         * Declare and initialize decimal indicating two digits after the
         * decimal point and hundredth for changing decimal to percent
         */
        final int decimal = 2;
        final int hundredth = 100;
        /*
         * Print out a,b,c,d in formula
         */
        out.print("The a in the formula is: " + tempA);
        out.println(" ");
        out.print("The b in the formula is: " + tempB);
        out.println(" ");
        out.print("The c in the formula is: " + tempC);
        out.println(" ");
        out.print("The d in the formula is: " + tempD);
        out.println(" ");
        /*
         * /* Print out approximated constant
         */
        out.print("The approximated constant is: " + approxNum);
        out.println();
        /*
         * Print out smallest relative error which is round up to two digits
         * after the decimal point
         */
        out.print("The smallest relative error is: ");
        out.print(relativeError * hundredth, decimal, false);
        out.print(" %");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
