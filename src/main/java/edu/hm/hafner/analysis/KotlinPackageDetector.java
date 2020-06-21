package edu.hm.hafner.analysis;

import java.util.regex.Pattern;

import edu.hm.hafner.util.VisibleForTesting;

import static edu.hm.hafner.analysis.PackageDetectors.*;

/**
 * Detects the package name of a Kotlin file.
 *
 * @author Ullrich Hafner
 */
class KotlinPackageDetector extends AbstractPackageDetector {

    private static final Pattern PACKAGE_PATTERN = Pattern.compile(
            "^\\s*package\\s*([a-z]+[.\\w]*)*");

    KotlinPackageDetector() {
        this(new FileSystem());
    }

    @VisibleForTesting
    KotlinPackageDetector(final FileSystem fileSystem) {
        super(fileSystem);
    }

    @Override
    boolean accepts(final String fileName) {
        return fileName.endsWith(".kt") || fileName.endsWith(".kts") || fileName.endsWith(".ktm");
    }

    @Override
    Pattern getPattern() {
        return PACKAGE_PATTERN;
    }
}
