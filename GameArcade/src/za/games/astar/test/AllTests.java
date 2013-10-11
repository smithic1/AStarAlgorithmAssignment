package za.games.astar.test;


import junit.framework.Test;
import junit.framework.TestSuite;
 
public class AllTests {
 
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for test");
        suite.addTestSuite(GameArcadeTest.class);
        suite.addTestSuite(FileValidatorTest.class);
        suite.addTestSuite(AStarPlayerTest.class);
        return suite;
    }
 
}