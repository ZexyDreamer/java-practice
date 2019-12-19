import Telegram.Bot;
import org.junit.Assert;
import org.junit.Test;

public class Test_Bot {
    @Test
    public void testGetBotUsername() {
        String expected = "BotHelper";
        Bot bot = new Bot();
        String actual = bot.getBotUsername();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMessageParser() {
        String[] expected = new String[] {"It's help", "abc"};
        String[] args = new String[] {"/help", "abc"};
        String[] actual = new String[expected.length];
        Bot bot = new Bot();
        for (int i = 0; i < args.length; i++) {
            actual[i] = bot.messageParser(args[i]);
        }
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual[i]);
        }
    }
}
