package org.jetbrains.plugins.template;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PsiErrorElementUtil;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class MyPluginTest extends BasePlatformTestCase {

    public void testXMLFile() {
        PsiFile psiFile = myFixture.configureByText(XmlFileType.INSTANCE, "<foo>bar</foo>");
        XmlFile xmlFile = assertInstanceOf(psiFile, XmlFile.class);

        assertFalse(PsiErrorElementUtil.hasErrors(getProject(), xmlFile.getVirtualFile()));

        assertNotNull(xmlFile.getRootTag());

        XmlTag rootTag = xmlFile.getRootTag();
        if (rootTag != null) {
            assertEquals("foo", rootTag.getName());
            assertEquals("bar", rootTag.getValue().getText());
        }
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/rename";
    }
}
