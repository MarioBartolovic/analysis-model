package edu.hm.hafner.analysis;

import java.util.regex.Pattern;

import static edu.hm.hafner.analysis.PackageDetectors.*;

/**
 * Detects the package name of a Java file.
 *
 * @author Ullrich Hafner
 */
class JavaPackageDetector extends AbstractPackageDetector {
    private static final Pattern PACKAGE_PATTERN = Pattern.compile(
            "^\\s*package\\s*([a-z]+[.\\w]*)\\s*;.*");

    JavaPackageDetector() {
        this(new FileSystem());
    }

    JavaPackageDetector(final FileSystem fileSystem) {
        super(fileSystem);
    }

    @Override
    public boolean accepts(final String fileName) {
        return fileName.endsWith(".java");
    }

    @Override
    Pattern getPattern() {
        return PACKAGE_PATTERN;
    }
}

