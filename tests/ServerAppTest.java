import org.junit.Assert;
import org.junit.Test;

public class ServerAppTest {
    @Test
    public void alphaNumericOnlyTest() {
        Assert.assertTrue(ServerApp.alphaNumericOnly("bob"));
        Assert.assertTrue(ServerApp.alphaNumericOnly("Bob"));
        Assert.assertTrue(ServerApp.alphaNumericOnly("BOB"));
        Assert.assertTrue(ServerApp.alphaNumericOnly("Bob1"));
        Assert.assertFalse(ServerApp.alphaNumericOnly("Bob_"));
        Assert.assertFalse(ServerApp.alphaNumericOnly("Bob "));
    }
}