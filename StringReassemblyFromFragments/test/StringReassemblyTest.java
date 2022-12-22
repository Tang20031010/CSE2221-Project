import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    /*
     * Test for combination
     */
    @Test
    public void combination_noOverlap() {
        String str1 = "abc";
        String str2 = "defgf";
        String expectedStr1 = "abc";
        String expectedStr2 = "defgf";

        String com = StringReassembly.combination(str1, str2, 0);
        String expectedCom = "abcdefgf";

        assertEquals(com, expectedCom);
        assertEquals(str1, expectedStr1);
        assertEquals(str2, expectedStr2);
    }

    @Test
    public void combination_oddOverlap() {
        String str1 = "abcf";
        String str2 = "bcfgf";
        String expectedStr1 = "abcf";
        String expectedStr2 = "bcfgf";

        String com = StringReassembly.combination(str1, str2, 3);
        String expectedCom = "abcfgf";

        assertEquals(com, expectedCom);
        assertEquals(str1, expectedStr1);
        assertEquals(str2, expectedStr2);
    }

    @Test
    public void combination_evenOverlap() {
        String str1 = "abcfm";
        String str2 = "bcfmgf";
        String expectedStr1 = "abcfm";
        String expectedStr2 = "bcfmgf";

        String com = StringReassembly.combination(str1, str2, 4);
        String expectedCom = "abcfmgf";

        assertEquals(com, expectedCom);
        assertEquals(str1, expectedStr1);
        assertEquals(str2, expectedStr2);
    }

    @Test
    public void combination_emptyString() {
        String str1 = "";
        String str2 = "bcfmgf";
        String expectedStr1 = "";
        String expectedStr2 = "bcfmgf";

        String com = StringReassembly.combination(str1, str2, 0);
        String expectedCom = "bcfmgf";

        assertEquals(com, expectedCom);
        assertEquals(str1, expectedStr1);
        assertEquals(str2, expectedStr2);
    }

    /*
     * Test for addToSetAvoidingSubstrings
     */

    @Test
    public void addToSetAvoidingSubstrings_strIsSubstringOfStringInSet() {
        Set<String> s = new Set1L<>();
        s.add("jklsm");
        s.add("gmdksla");
        s.add("182odkaf,;l");
        s.add("iiik");
        s.add("nmslfkdj");
        String str = "kls";
        StringReassembly.addToSetAvoidingSubstrings(s, str);

        Set<String> expectedS = new Set1L<>();
        expectedS.add("jklsm");
        expectedS.add("gmdksla");
        expectedS.add("182odkaf,;l");
        expectedS.add("iiik");
        expectedS.add("nmslfkdj");
        String expectedStr = "kls";

        assertEquals(s, expectedS);
        assertEquals(str, expectedStr);
    }

    @Test
    public void addToSetAvoidingSubstrings_StringInSetIsSubstringOfStr() {
        Set<String> s = new Set1L<>();
        s.add("jklsm");
        s.add("md");
        s.add("182odkaf,;l");
        s.add("iiik");
        s.add("nmslfkdj");
        String str = "gmdksla";
        StringReassembly.addToSetAvoidingSubstrings(s, str);

        Set<String> expectedS = new Set1L<>();
        expectedS.add("jklsm");
        expectedS.add("gmdksla");
        expectedS.add("182odkaf,;l");
        expectedS.add("iiik");
        expectedS.add("nmslfkdj");
        String expectedStr = "gmdksla";

        assertEquals(s, expectedS);
        assertEquals(str, expectedStr);
    }

    @Test
    public void addToSetAvoidingSubstrings_notSubstring() {
        Set<String> s = new Set1L<>();
        s.add("jklsm");
        s.add("md");
        s.add("182odkaf,;l");
        s.add("iiik");
        s.add("nmslfkdj");
        String str = "0192k";
        StringReassembly.addToSetAvoidingSubstrings(s, str);

        Set<String> expectedS = new Set1L<>();
        expectedS.add("jklsm");
        expectedS.add("md");
        expectedS.add("182odkaf,;l");
        expectedS.add("iiik");
        expectedS.add("nmslfkdj");
        expectedS.add("0192k");
        String expectedStr = "0192k";

        assertEquals(s, expectedS);
        assertEquals(str, expectedStr);
    }

    @Test
    public void addToSetAvoidingSubstrings_oneSetOneString() {
        Set<String> s = new Set1L<>();
        s.add("jklsm");
        String str = "kk";
        StringReassembly.addToSetAvoidingSubstrings(s, str);

        Set<String> expectedS = new Set1L<>();
        expectedS.add("jklsm");
        expectedS.add("kk");
        String expectedStr = "kk";

        assertEquals(s, expectedS);
        assertEquals(str, expectedStr);
    }

    /*
     * Test for linesFromInput
     */

    @Test
    public void linesFromInput_cheer() {
        SimpleReader input = new SimpleReader1L("data/cheer.txt");
        SimpleReader inputExpected = new SimpleReader1L("expectedCheer.txt");
        Set<String> s = StringReassembly.linesFromInput(input);
        Set<String> expectedS = new Set1L<>();
        while (!inputExpected.atEOS()) {
            String temp = inputExpected.nextLine();
            expectedS.add(temp);
        }
        assertEquals(s, expectedS);
        input.close();
        inputExpected.close();
    }

    @Test
    public void linesFromInput_emptyInput() {
        SimpleReader input = new SimpleReader1L("emptyInput.txt");
        SimpleReader inputExpected = new SimpleReader1L(
                "expectedEmptyInput.txt");
        Set<String> s = StringReassembly.linesFromInput(input);
        Set<String> expectedS = new Set1L<>();
        while (!inputExpected.atEOS()) {
            String temp = inputExpected.nextLine();
            expectedS.add(temp);
        }
        assertEquals(s, expectedS);
        input.close();
        inputExpected.close();
    }

    @Test
    public void linesFromInput_inputWithRepeatedLine() {
        SimpleReader input = new SimpleReader1L("inputWithRepeatedLine.txt");
        SimpleReader inputExpected = new SimpleReader1L(
                "expectedInputWithRepeatedLine.txt");
        Set<String> s = StringReassembly.linesFromInput(input);
        Set<String> expectedS = new Set1L<>();
        while (!inputExpected.atEOS()) {
            String temp = inputExpected.nextLine();
            expectedS.add(temp);
        }
        assertEquals(s, expectedS);
        input.close();
        inputExpected.close();
    }

    @Test
    public void linesFromInput_inputWithEmptyLine() {
        SimpleReader input = new SimpleReader1L("inputWithEmptyLine.txt");
        SimpleReader inputExpected = new SimpleReader1L(
                "expectedInputWithEmptyLine.txt");
        Set<String> s = StringReassembly.linesFromInput(input);
        Set<String> expectedS = new Set1L<>();
        while (!inputExpected.atEOS()) {
            String temp = inputExpected.nextLine();
            expectedS.add(temp);
        }
        assertEquals(s, expectedS);
        input.close();
        inputExpected.close();
    }

    @Test
    public void linesFromInput_inputWithEmptyFrontLine() {
        SimpleReader input = new SimpleReader1L("inputWithEmptyFrontLine.txt");
        SimpleReader inputExpected = new SimpleReader1L(
                "expectedInputWithEmptyFrontLine.txt");
        Set<String> s = StringReassembly.linesFromInput(input);
        Set<String> expectedS = new Set1L<>();
        while (!inputExpected.atEOS()) {
            String temp = inputExpected.nextLine();
            expectedS.add(temp);
        }
        assertEquals(s, expectedS);
        input.close();
        inputExpected.close();
    }

    /*
     * Test for printWithLineSeparators
     */

    @Test
    public void printWithLineSeparators_stringEmpty() {
        String text = "";
        SimpleWriter out = new SimpleWriter1L("stringEmpty.txt");

        StringReassembly.printWithLineSeparators(text, out);
        out.close();
        SimpleReader in = new SimpleReader1L("stringEmpty.txt");
        SimpleReader expectedIn = new SimpleReader1L("expectedStringEmpty.txt");
        while (!in.atEOS() && !expectedIn.atEOS()) {
            String temp = in.nextLine();
            String expectedTemp = expectedIn.nextLine();
            assertEquals(temp, expectedTemp);
        }
        assertEquals(in.atEOS(), true);
        assertEquals(expectedIn.atEOS(), true);

        in.close();
        expectedIn.close();
    }

    @Test
    public void printWithLineSeparators_stringWithOneSymbol() {
        String text = "abc~def";
        SimpleWriter out = new SimpleWriter1L("stringWithOneSymbol.txt");

        StringReassembly.printWithLineSeparators(text, out);
        out.close();
        SimpleReader in = new SimpleReader1L("stringWithOneSymbol.txt");
        SimpleReader expectedIn = new SimpleReader1L(
                "expectedStringWithOneSymbol.txt");
        while (!in.atEOS() && !expectedIn.atEOS()) {
            String temp = in.nextLine();
            String expectedTemp = expectedIn.nextLine();
            assertEquals(temp, expectedTemp);
        }
        assertEquals(in.atEOS(), true);
        assertEquals(expectedIn.atEOS(), true);

        in.close();
        expectedIn.close();
    }

    @Test
    public void printWithLineSeparators_stringWithTwoSymbol() {
        String text = "abc~~def";
        SimpleWriter out = new SimpleWriter1L("stringWithTwoSymbol.txt");

        StringReassembly.printWithLineSeparators(text, out);
        out.close();
        SimpleReader in = new SimpleReader1L("stringWithTwoSymbol.txt");
        SimpleReader expectedIn = new SimpleReader1L(
                "expectedStringWithTwoSymbol.txt");
        while (!in.atEOS() && !expectedIn.atEOS()) {
            String temp = in.nextLine();
            String expectedTemp = expectedIn.nextLine();
            assertEquals(temp, expectedTemp);
        }
        assertEquals(in.atEOS(), true);
        assertEquals(expectedIn.atEOS(), true);

        in.close();
        expectedIn.close();
    }

    @Test
    public void printWithLineSeparators_frontSymbol() {
        String text = "~abc~def";
        SimpleWriter out = new SimpleWriter1L("frontSymbol.txt");

        StringReassembly.printWithLineSeparators(text, out);
        out.close();
        SimpleReader in = new SimpleReader1L("frontSymbol.txt");
        SimpleReader expectedIn = new SimpleReader1L("expectedFrontSymbol.txt");
        while (!in.atEOS() && !expectedIn.atEOS()) {
            String temp = in.nextLine();
            String expectedTemp = expectedIn.nextLine();
            assertEquals(temp, expectedTemp);
        }
        assertEquals(in.atEOS(), true);
        assertEquals(expectedIn.atEOS(), true);

        in.close();
        expectedIn.close();
    }
}
