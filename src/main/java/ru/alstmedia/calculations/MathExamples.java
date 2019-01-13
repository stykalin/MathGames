package ru.alstmedia.calculations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class MathExamples {

    private static final Logger LOG = LogManager.getLogger(MathExamples.class);
    private static final Random rand = new Random();

    public static String getExample(MathSign sign, int resultNum) {
        switch (sign) {
            case PLUS:
                return getAdditionExample(resultNum);
            case MINUS:
                return getSubtractionExample(resultNum);
                default:
                    throw new VerifyError("There is no realisation for sign: " + sign);
        }
    }

    private static String getAdditionExample(int resultNum) {
        int a = getRandomNum(resultNum);
        int b = resultNum - a;
        return a + "+" + b;
    }

    private static String getSubtractionExample(int resultNum) {
        int a = getRandomNum(resultNum);
        int b = resultNum + a;
        return b + "-" + a;
    }

    private static int getRandomNum(int maxNum) {
        return rand.nextInt(maxNum);
    }
}
