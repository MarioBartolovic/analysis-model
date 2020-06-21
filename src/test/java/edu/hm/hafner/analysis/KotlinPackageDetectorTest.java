package edu.hm.hafner.analysis;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.hm.hafner.util.ResourceTest;

import static org.assertj.core.api.Assertions.*;

class KotlinPackageDetectorTest extends ResourceTest {

    @ParameterizedTest(name = "{index} => file={0}, expected package={1}")
    @CsvSource({
            "MavenJavaTest.txt, -",
            "complicated-package-declaration.txt, -",
            "pom.xml, -",
            "ActionBinding.cs, -"})

    void shouldExtractPackageNameFromJavaSource(final String fileName, final String expectedPackage) throws IOException {
        try (InputStream stream = asInputStream(fileName)) {
            assertThat(new KotlinPackageDetector().detectPackageName(stream, StandardCharsets.UTF_8))
                    .isEqualTo(expectedPackage);
        }
    }

    @Test
    void shouldSkipPackagesThatDoNotStartWithLowerCase() {
        KotlinPackageDetector detector = new KotlinPackageDetector();

        String[] packages = {"import EDU.hm.hafner.analysis;",
                "import 0123.hm.hafner.analysis;"};

        assertThat(detector.detectPackageName(Arrays.stream(packages))).isEqualTo("-");
    }

    @Test
    void shouldAcceptCorrectFileSuffix() {
        JavaPackageDetector packageDetector = new JavaPackageDetector();
        assertThat(packageDetector.accepts("Action.java")).as("Does not accept a Java file.")
                .isTrue();
        assertThat(packageDetector.accepts("ActionBinding.cs")).as("Accepts a non-Java file.")
                .isFalse();
        assertThat(packageDetector.accepts("pom.xml")).as("Accepts a non-C# file.")
                .isFalse();
    }

}
