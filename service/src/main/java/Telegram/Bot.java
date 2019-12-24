package Telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


import Handlers.*;
import Parser.Parser;
import Database.Database;

public class Bot extends TelegramLongPollingBot {

    final private String token;
    final private String appid;
    private String chat_id;
    private Location location = new Location();
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private Database database = new Database();
    private Boolean gameMode = false;
    private Game game;

    public Bot() {
        Properties properties = Parser.parser();
        this.token = properties.getProperty("token");
        this.appid = properties.getProperty("appid");
        this.database.create();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        if (update.getMessage().hasLocation()) {
            this.location = update.getMessage().getLocation();
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        this.chat_id = update.getMessage().getChatId().toString();
        this.database.add(this.chat_id, this.location.toString());
        sendMessage.setReplyMarkup(this.replyKeyboardMarkup);
        if (this.gameMode && message.matches("\\d+")) {
            sendMessage.setText(this.game.gameLogic(message));
        } else {
            try {
                sendMessage.setText(messageParser(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String messageParser(String message) throws IOException {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRowFirstRow = new KeyboardRow();
        KeyboardRow keyboardRowSecondRow = new KeyboardRow();
        this.replyKeyboardMarkup.setSelective(true);
        this.replyKeyboardMarkup.setResizeKeyboard(true);
        this.replyKeyboardMarkup.setOneTimeKeyboard(false);

        if (message.startsWith("/city")) {
            TapingCityHandler tapingCityHandler = new TapingCityHandler();
            return tapingCityHandler.weatherHandle(message, this.appid, this.database, this.chat_id);
        }
        switch (message) {
            case ("/start"):
            case ("Back"):
                MainHandler mainHandler = new MainHandler();
                return mainHandler.handle(keyboard, this.replyKeyboardMarkup, keyboardRowFirstRow, keyboardRowSecondRow);
            case ("Game"):
                this.gameMode = true;
                this.game = new Game();
                return "Game is ready, write number.";
            case ("Settings"):
                SettingsHandler settingsHandler = new SettingsHandler();
                return settingsHandler.handle(keyboard, this.replyKeyboardMarkup, keyboardRowFirstRow, null);
            case ("Change location"):
                this.database.change(location, chat_id);
                return "Changed";
            case ("Weather"):
                WeatherHandler weatherHandler = new WeatherHandler();
                return weatherHandler.handle(keyboard, this.replyKeyboardMarkup, keyboardRowFirstRow, keyboardRowSecondRow);
            case ("Help"):
                HelpHandler helpHandler = new HelpHandler();
                return helpHandler.handle(null, null, null, null);
            case ("By geolocation"):
                GeolocationHandler geolocationHandler = new GeolocationHandler();
                return geolocationHandler.weatherHandle(message, this.appid, this.database, this.chat_id);
            case ("By taping"):
                return "Write your city like \"/city YOURCITY\".";
            default:
                return message;
        }
    }

    public String getBotUsername() {
        return "BotHelper";
    }

    public String getBotToken() {
        return this.token;
    }
}
