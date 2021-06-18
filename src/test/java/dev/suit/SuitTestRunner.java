package dev.suit;

import dev.suit.SuitTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class SuitTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(SuitTest.class);

        for(Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
