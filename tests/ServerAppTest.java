import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ServerAppTest {
    @Test
    public void alphaNumericOnly() {
        Assert.assertFalse(ServerApp.alphaNumericOnly("bob"));
        Assert.assertFalse(ServerApp.alphaNumericOnly("Bob"));
        Assert.assertFalse(ServerApp.alphaNumericOnly("BOB"));
        Assert.assertFalse(ServerApp.alphaNumericOnly("Bob1"));
        Assert.assertTrue(ServerApp.alphaNumericOnly("Bob_"));
        Assert.assertTrue(ServerApp.alphaNumericOnly("Bob "));
    }
}