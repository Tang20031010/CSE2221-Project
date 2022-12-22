import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Coney Tang
 *
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (base * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */

        /*
         * Check if m is zero, if m is not zero, do the recursion
         */
        if (!m.isZero()) {
            /*
             * Divide n by m
             */
            NaturalNumber left = n.divide(m);
            /*
             * Updates the value of n to n mod m
             */
            n.transferFrom(left);
            reduceToGCD(m, n);
            /*
             * Updates the value of n to m and clear m to 0
             */
            n.transferFrom(m);
        }

    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {
        /*
         * Declare and initialize result
         */
        boolean result = false;
        /*
         * Declare and initialize temp as n to not change the value of n
         */
        NaturalNumber temp = new NaturalNumber2(n);
        /*
         * Declare and initialize two to serve later division
         */
        NaturalNumber two = new NaturalNumber2(2);
        /*
         * Check the if there is remainder
         */
        if (temp.divide(two).isZero()) {
            result = true;
        }
        /*
         * return statement
         */
        return result;
    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p,
            NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";
        /*
         * Use the fast-powering algorithm as previously discussed in class,
         * with the additional feature that every multiplication is followed
         * immediately by "reducing the result modulo m"
         */

        /*
         * Declare and initialize one and two
         */
        NaturalNumber one = new NaturalNumber2(1);
        NaturalNumber two = new NaturalNumber2(2);
        /*
         * If p is zero, change n's value to 1
         */
        if (p.isZero()) {
            n.copyFrom(one);
        } else {
            /*
             * If p is not 0, do recursion, check if p is even
             */
            if (isEven(p)) {
                /*
                 * Declare and initialize pLeft to store the remainder of
                 * division for later restoring p
                 */
                NaturalNumber pLeft = p.divide(two);
                powerMod(n, p, m);
                /*
                 * Restore p value
                 */
                p.multiply(two);
                p.add(pLeft);
                /*
                 * Declare and initialize temp1 to store the value of n
                 */
                NaturalNumber temp1 = new NaturalNumber2(n);
                /*
                 * n multiply itself
                 */
                n.multiply(temp1);
                /*
                 * n mod m
                 */
                NaturalNumber left = n.divide(m);
                n.transferFrom(left);
            } else {
                /*
                 * If p is not even, declare and initialize temp3 to store the
                 * original value of n
                 */
                NaturalNumber temp2 = new NaturalNumber2(n);
                /*
                 * Declare and initialize pLeft to store the remainder of
                 * division for later restoring p
                 */
                NaturalNumber pLeft = p.divide(two);
                powerMod(n, p, m);
                /*
                 * Restore p value
                 */
                p.multiply(two);
                p.add(pLeft);
                /*
                 * Declare and initialize temp3 to store the value of n
                 */
                NaturalNumber temp3 = new NaturalNumber2(n);
                /*
                 * n multiply itself and its original value
                 */
                n.multiply(temp3);
                n.multiply(temp2);
                /*
                 * n mod m
                 */
                NaturalNumber left = n.divide(m);
                n.transferFrom(left);
            }
        }
    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w,
            NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();
        /*
         * Declare and initialize result
         */
        boolean result = false;
        /*
         * Declare and initialize tempW to store the value of w
         */
        NaturalNumber tempW = new NaturalNumber2(w);
        /*
         * Declare and initialize two
         */
        NaturalNumber two = new NaturalNumber2(2);
        /*
         * Declare and initialize tempN to store the value of n
         */
        NaturalNumber tempN = new NaturalNumber2(n);
        /*
         * Call powerMod() method
         */
        powerMod(tempW, two, tempN);

        /*
         * Declare and initialize tempW2 to store the value of w
         */
        NaturalNumber tempW2 = new NaturalNumber2(w);
        /*
         * Declare and initialize tempN2 to store the value of n
         */
        NaturalNumber tempN2 = new NaturalNumber2(n);
        /*
         * Declare and initialize tempN3 to store the value of n
         */
        NaturalNumber tempN3 = new NaturalNumber2(n);
        /*
         * Decrement tempN3 by one
         */
        tempN3.decrement();
        /*
         * Call powerMod() method
         */
        powerMod(tempW2, tempN3, tempN2);

        /*
         * Declare and initialize one
         */
        NaturalNumber one = new NaturalNumber2(1);
        /*
         * If tempW is one or tempW2 is not one, set the value of result to true
         */
        if (tempW.compareTo(one) == 0 || tempW2.compareTo(one) != 0) {
            result = true;
        }
        /*
         * return statement
         */
        return result;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * odd n >= 5: simply check whether 2 is a witness that n is
             * composite (which works surprisingly well :-)
             */
            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate several witness candidates --
         * say, 10 to 50 candidates -- guessing that n is prime only if none of
         * these candidates is a witness to n being composite (based on fact #3
         * as described in the project description); use the code for isPrime1
         * as a guide for how to do this, and pay attention to the requires
         * clause of isWitnessToCompositeness
         */

        /*
         * Declare and initialize result to false
         */
        boolean result = false;
        /*
         * Declare and initialize tempN to store value of n
         */
        NaturalNumber tempN = new NaturalNumber2(n);
        /*
         * Decrement tempN by 1
         */
        tempN.decrement();
        /*
         * Declare and initialize one
         */
        NaturalNumber one = new NaturalNumber2(1);
        /*
         * Declare and initialize the number of candidates
         */
        final int upperBound = 30;

        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            result = true;
        } else if (isEven(n)) {
            /*
             * Even are composites
             */
            result = false;
        } else {
            /*
             * Do the iteration
             */
            for (int i = 0; i < upperBound; i++) {
                /*
                 * Generate random number with the upper bound of tempN and
                 * lower bound of 0
                 */
                NaturalNumber randomNum = randomNumber(tempN);
                /*
                 * To fulfill the requirement of isWitnessToCompositeness
                 * method, check if randomNum is less or equal to one, or it is
                 * equal to tempN. If the randomNum does not fulfill the
                 * requirement, generate a new one
                 */
                while (randomNum.compareTo(one) <= 0
                        || randomNum.compareTo(tempN) == 0) {
                    randomNum = randomNumber(tempN);
                }
                /*
                 * If the number is not witness to compositeness, result set to
                 * be true
                 */
                result = !isWitnessToCompositeness(randomNum, n);
            }
        }
        /*
         * Return statement
         */
        return result;
    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 1
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use isPrime2 to check numbers, starting at n and increasing through
         * the odd numbers only (why?), until n is likely prime
         */
        NaturalNumber two = new NaturalNumber2(2);
        /*
         * Check if n is even and not equals to 2
         */
        if (n.compareTo(two) != 0 && isEven(n)) {
            /*
             * If n is even, increment n by 1 to make it odd
             */
            n.increment();
        }
        /*
         * Check if it prime using isPrime2
         */
        while (!isPrime2(n)) {
            /*
             * n increment by 2 to make sure n is odd
             */
            n.increment();
            n.increment();
        }

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

        /*
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println("  expected value = "
                + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
