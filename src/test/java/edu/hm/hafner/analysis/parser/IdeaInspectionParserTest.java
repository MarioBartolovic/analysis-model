package edu.hm.hafner.analysis.parser;

import edu.hm.hafner.analysis.AbstractIssueParserTest;
import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.analysis.Priority;
import edu.hm.hafner.analysis.assertj.SoftAssertions;

/**
 * Tests {@link IdeaInspectionParser } parser class.
 *
 * @author Alex Lopashev, alexlopashev@gmail.com
 */
class IdeaInspectionParserTest extends AbstractIssueParserTest {
    IdeaInspectionParserTest() {
        super("IdeaInspectionExample.xml");
    }

    @Override
    protected IdeaInspectionParser createParser() {
        return new IdeaInspectionParser();
    }

    @Override
    protected void assertThatIssuesArePresent(final Issues issues, final SoftAssertions softly) {
        softly.assertThat(issues).hasSize(1);

        softly.assertThat(issues.get(0))
                .hasPriority(Priority.NORMAL)
                .hasCategory("Unused method parameters")
                .hasLineStart(42)
                .hasLineEnd(42)
                .hasMessage("Parameter <code>intentionallyUnusedString</code> is not used  in either this method or any of its derived methods")
                .hasFileName("file://$PROJECT_DIR$/src/main/java/org/lopashev/Test.java");
    }
}

