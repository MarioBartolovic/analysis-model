package edu.hm.hafner.analysis.parser.violations;

import edu.hm.hafner.analysis.AbstractParser;
import edu.hm.hafner.analysis.AbstractParserTest;
import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.analysis.Priority;
import edu.hm.hafner.analysis.assertj.SoftAssertions;

/**
 * Tests the class {@link MyPyAdapter}.
 *
 * @author Ullrich Hafner
 */
class MyPyAdapterTest extends AbstractParserTest  {
    MyPyAdapterTest() {
        super("mypy.txt");
    }

    @Override
    protected void assertThatIssuesArePresent(final Issues issues, final SoftAssertions softly) {
        softly.assertThat(issues).hasSize(5);
        softly.assertThat(issues.get(0))
                .hasMessage("\"LogRecord\" has no attribute \"user_uuid\"")
                .hasFileName("fs/cs/backend/log.py")
                .hasLineStart(16)
                .hasPriority(Priority.HIGH);
        softly.assertThat(issues.get(1))
                .hasMessage("\"LogRecord\" has no attribute \"tenant_id\"")
                .hasFileName("fs/cs/backend/log.py")
                .hasLineStart(17)
                .hasPriority(Priority.HIGH);
    }

    @Override
    protected AbstractParser createParser() {
        return new MyPyAdapter();
    }
}