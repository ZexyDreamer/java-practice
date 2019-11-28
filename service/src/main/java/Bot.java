import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    private String token;
    private String appid;

    public Bot() {
        String[] s = Parser.parser();
        this.token = s[0];
        this.appid = s[1];
    }

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(messageParser(message));
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String messageParser (String message){
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
        } else if (message.equals("Help")) {
            return "Click the \"Weather\" button and write the city.";
        } else if (message.equals("/start")) {
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
