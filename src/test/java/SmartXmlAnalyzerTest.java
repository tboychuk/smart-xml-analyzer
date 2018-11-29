import com.tarasboychuk.SmartXmlAnalyzer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SmartXmlAnalyzerTest {

    @Test
    public void testFindButtonWhenNewDangerButtonIsAddedBeforeTarget() throws URISyntaxException {
        URI originFileUri = getClass().getClassLoader().getResource("samples/sample-0-origin.html").toURI();
        File originFile = new File(originFileUri);
        URI diffCaseFileUri = getClass().getClassLoader().getResource("samples/sample-1-evil-gemini.html").toURI();
        File diffCaseFile = new File(diffCaseFileUri);
        String elementId = "make-everything-ok-button";

        String result = SmartXmlAnalyzer.findElementInDiffFile(originFile, diffCaseFile, elementId);

        assertThat(result, equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > a[2]"));
    }

    @Test
    public void testFindButtonWithChangedClassOnClickAndValue() throws URISyntaxException {
        URI originFileUri = getClass().getClassLoader().getResource("samples/sample-0-origin.html").toURI();
        File originFile = new File(originFileUri);
        URI diffCaseFileUri = getClass().getClassLoader().getResource("samples/sample-2-container-and-clone.html").toURI();
        File diffCaseFile = new File(diffCaseFileUri);
        String elementId = "make-everything-ok-button";

        String result = SmartXmlAnalyzer.findElementInDiffFile(originFile, diffCaseFile, elementId);

        assertThat(result, equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > div > a"));
    }

    @Test
    public void testFindButtonWhenWarningButtonWithSameHrefAndRelIsAddedBefore() throws URISyntaxException {
        URI originFileUri = getClass().getClassLoader().getResource("samples/sample-0-origin.html").toURI();
        File originFile = new File(originFileUri);
        URI diffCaseFileUri = getClass().getClassLoader().getResource("samples/sample-3-the-escape.html").toURI();
        File diffCaseFile = new File(diffCaseFileUri);
        String elementId = "make-everything-ok-button";

        String result = SmartXmlAnalyzer.findElementInDiffFile(originFile, diffCaseFile, elementId);

        assertThat(result, equalTo("html > body > div > div > div[3] > div[1] > div > div[3] > a"));
    }

    @Test
    public void testFindButtonWhenButtonWithSameClassAndValueIsAddedBefore() throws URISyntaxException {
        URI originFileUri = getClass().getClassLoader().getResource("samples/sample-0-origin.html").toURI();
        File originFile = new File(originFileUri);
        URI diffCaseFileUri = getClass().getClassLoader().getResource("samples/sample-4-the-mash.html").toURI();
        File diffCaseFile = new File(diffCaseFileUri);
        String elementId = "make-everything-ok-button";

        String result = SmartXmlAnalyzer.findElementInDiffFile(originFile, diffCaseFile, elementId);

        assertThat(result, equalTo("html > body > div > div > div[3] > div[1] > div > div[3] > a"));
    }
}
