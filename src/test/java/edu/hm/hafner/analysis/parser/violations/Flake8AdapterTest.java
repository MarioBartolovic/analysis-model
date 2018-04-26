package edu.hm.hafner.analysis.parser.violations;

import edu.hm.hafner.analysis.AbstractParser;
import edu.hm.hafner.analysis.AbstractParserTest;
import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.analysis.Priority;
import edu.hm.hafner.analysis.assertj.SoftAssertions;

/**
 * Tests the class {@link Flake8Adapter}.
 *
 * @author Ullrich Hafner
 */
class Flake8AdapterTest extends AbstractParserTest {
    Flake8AdapterTest() {
        super("flake8.txt");
    }

    @Override
    protected void assertThatIssuesArePresent(final Issues issues, final SoftAssertions softly) {
        softly.assertThat(issues).hasSize(12);
        softly.assertThat(issues.get(0))
                .hasMessage("'db' imported but unused")
                .hasFileName("myproject/__init__.py")
                .hasType("401")
                .hasLineStart(7)
                .hasPriority(Priority.HIGH);
    }

    @Override
    protected AbstractParser createParser() {
        return new Flake8Adapter();
    }
}