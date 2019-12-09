import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {

    private String token;
    private String appid;
    private Boolean gameMode = false;
    private Game game;

    public Bot() {
        Properties properties = Parser.parser();
        this.token = properties.getProperty("token");
        this.appid = properties.getProperty("appid");
    }

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        if (this.gameMode && message.matches("\\d+")) {
            sendMessage.setText(this.game.Logic(message));
        }
        else {
            sendMessage.setText(messageParser(message));
        }
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String messageParser (String message) {
        if (message.equals("Weather")) {
            Weather weather = new Weather();
            String city = "Yekaterinburg"; //message.split(" ")[1];
            String appid = "&APPID=" + this.appid;
            String s = "http://api.openweathermap.org/data/2.5/weather?q=" + city + appid;
            try {
                return weather.getWeather(s);
            } catch (IOException e) {
                 e.printStackTrace();
            }
        }
        else if (message.equals("Game")) {
            this.gameMode = true;
            this.game = new Game();
            return "Game is ready, write number. " + this.game.help;
        }
        else if (message.equals("Help")) {
            return "Click the \"Weather\" button and write the city.";
        }
        else if (message.equals("/start")) {
            return "This bot can tell the weather in your city. Click the \"Weather\" button and write the city.";
        }
        return message;
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRowFirstRow = new KeyboardRow();

        keyboardRowFirstRow.add(new KeyboardButton("Weather"));
        keyboardRowFirstRow.add(new KeyboardButton("Help"));
        keyboardRowFirstRow.add(new KeyboardButton("Game"));

        keyboardRowList.add(keyboardRowFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public String getBotUsername() {
        return "BotHelper";
    }

    public String getBotToken() {
        return this.token;
    }
}
