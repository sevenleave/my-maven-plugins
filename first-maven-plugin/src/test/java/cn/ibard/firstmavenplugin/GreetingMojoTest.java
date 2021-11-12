package cn.ibard.firstmavenplugin;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

/**
 * @author poirot
 */
public class GreetingMojoTest extends AbstractMojoTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testMojoGoal() throws Exception {
        File testPom = new File(getBasedir(), "src/test/resources/unit/greeting/plugin-config.xml");

        GreetingMojo sayhi = (GreetingMojo) lookupMojo("sayhi", testPom);

        assertNotNull(sayhi);
    }
}
