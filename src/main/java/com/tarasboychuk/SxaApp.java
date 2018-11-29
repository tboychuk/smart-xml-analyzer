package com.tarasboychuk;

import com.tarasboychuk.exception.SmartXmlParserException;

import java.io.File;

public class SxaApp {

    public static void main(String[] args) {
        validateParameters(args);
        File originFile = new File(args[0]);
        File diffCaseFile = new File(args[1]);
        String targetElementId = args[2];
        try {
            String foundElementPath = SmartXmlAnalyzer.findElementInDiffFile(originFile, diffCaseFile, targetElementId);
            System.out.println(foundElementPath);
        } catch (SmartXmlParserException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void validateParameters(String[] args) {
        if (args.length < 3 || args[0].isEmpty() || args[1].isEmpty() || args[2].isEmpty()) {
            System.err.println("Invalid parameters!");
            System.err.println("Use the following parameters: origin-file-path diff-case-file-path target-element-id");
            System.exit(1);
        }
    }
}
