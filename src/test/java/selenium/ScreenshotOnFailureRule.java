package selenium;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Calling {@code driver.close();} is unnecessary when using this rule, as it
 * is performed automatically after each test execution. I.e., there is no need
 * for a {@code tearDown()} method in tests using this rule.
 */
public class ScreenshotOnFailureRule implements MethodRule {

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        return new ScreenshotOnFailureStatement(base, method, target);
    }
}
