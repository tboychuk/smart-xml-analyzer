package com.tarasboychuk;

import com.tarasboychuk.exception.SmartXmlParserException;
import com.tarasboychuk.util.HtmlFileUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.tarasboychuk.util.HtmlFileUtil.findElementById;
import static java.util.Comparator.comparing;

public class SmartXmlAnalyzer {

    public static String findElementInDiffFile(File originFile, File diffCaseFile, String elementId) {
        Element foundElement = findElementById(diffCaseFile, elementId)
                .orElseGet(() -> findBestMatchedElement(originFile, diffCaseFile, elementId));

        return computeElementPath(foundElement);
    }

    private static Element findBestMatchedElement(File originFile, File diffCaseFile, String elementId) {
        Element targetElement = findElementById(originFile, elementId)
                .orElseThrow(() -> new SmartXmlParserException(String.format("Cannot get target element by id %s", elementId)));

        return getAllBodyElements(diffCaseFile).stream()
                .max(comparing(element -> calculateMatches(element, targetElement)))
                .orElseThrow(() -> new SmartXmlParserException("Cannot find matched elements"));
    }

    private static Elements getAllBodyElements(File file) {
        Elements elements = HtmlFileUtil.findElementsByQuery(file, "body")
                .orElseThrow(() -> new SmartXmlParserException(String.format("Cannot get a <body> elements from file %s", file.getName())));
        return elements.get(0).getAllElements();
    }

    private static int calculateMatches(Element element, Element targetElement) {
        int numOfMatches = 0;
        numOfMatches += targetElement.tagName().equals(element.tagName()) ? 1 : 0;
        numOfMatches += targetElement.val().equals(element.val()) ? 1 : 0;
        for (Attribute attribute : targetElement.attributes()) {
            numOfMatches += elementHasAttribute(element, attribute) ? 1 : 0;
        }
        return numOfMatches;
    }

    private static boolean elementHasAttribute(Element element, Attribute targetAttribute) {
        for (Attribute attribute : element.attributes()) {
            if (attribute.equals(targetAttribute)) {
                return true;
            }
        }
        return false;
    }

    private static String computeElementPath(Element element) {
        List<String> tags = new ArrayList<>();
        Element currentElement = element;
        while (currentElement.parent() != null) {
            tags.add(tagify(currentElement));
            currentElement = currentElement.parent();
        }
        return reverseAndConcat(tags);
    }

    private static String tagify(Element currentElement) {
        Element parent = currentElement.parent();
        if (parent == null) {
            return currentElement.tagName();
        } else {
            Elements siblingsWithSameTag = getDirectChildrenByTag(parent, currentElement.tagName());
            if (siblingsWithSameTag.size() == 1) {
                return currentElement.tagName();
            } else {
                return tagifyWithIndex(currentElement, siblingsWithSameTag);
            }
        }
    }

    private static Elements getDirectChildrenByTag(Element element, String tag) {
        List<Element> resultChildren = element.children().stream()
                .filter(e -> e.tagName().equals(tag))
                .collect(Collectors.toList());
        return new Elements(resultChildren);
    }


    private static String tagifyWithIndex(Element currentElement, Elements elements) {
        StringBuilder stringBuilder = new StringBuilder(currentElement.tagName());
        int index = elements.indexOf(currentElement);
        stringBuilder.append("[");
        stringBuilder.append(index + 1);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static String reverseAndConcat(List<String> tags) {
        Collections.reverse(tags);
        return String.join(" > ", tags);
    }

}
