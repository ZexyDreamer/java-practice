package Telegram;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Handlers.*;
import Parser.Parser;
import DataBase.Database;

public class Bot extends TelegramLongPollingBot {

    final private String token;
    final private String appid;
    private String chat_id;
    private Location location;
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private Database database = new Database();
    private Boolean gameMode = false;
    private Game game;

    public Bot() throws SQLException {
        Properties properties = Parser.parser();
        this.token = properties.getProperty("token");
        this.appid = properties.getProperty("appid");
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        if (update.getMessage().hasLocation()) {
            location = update.getMessage().getLocation();
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        chat_id = update.getMessage().getChatId().toString();
        String nameUser = update.getMessage().getFrom().getUserName();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        database.create();
        Location location = new Location();
        database.add(chat_id, nameUser, location.toString());
        if (this.gameMode && message.matches("\\d+")) {
            sendMessage.setText(this.game.gameLogic(message));
        } else {
            sendMessage.setText(messageParser(message));
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String messageParser(String message) {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRowFirstRow = new KeyboardRow();
        KeyboardRow keyboardRowSecondRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if (message.equals("/start") | message.equals("Back")) {
            MainHandler handler = new MainHandler();
            return handler.handle(keyboard, replyKeyboardMarkup, keyboardRowFirstRow, keyboardRowSecondRow);
        }

        if (message.equals("Game")) {
            this.gameMode = true;
            this.game = new Game();
            return "Game is ready, write number.";
        }

        if (message.equals("Settings")) {
            SettingsHandler handler = new SettingsHandler();
            return handler.handle(keyboard, replyKeyboardMarkup, keyboardRowFirstRow, null);
        }

        if (message.equals("Change location")) {
            database.change(location, chat_id);
            return "Changed";
        }

        if (message.equals("Weather")) {
            WeatherHandler handler = new WeatherHandler();
            return handler.handle(keyboard, replyKeyboardMarkup, keyboardRowFirstRow, keyboardRowSecondRow);
        }

        if (message.equals("Help")) {
            HelpHandler handler = new HelpHandler();
            return handler.handle(null, null, null, null);
        }

        if (message.equals("By geolocation")) {
            Weather weather = new Weather();
            String latitude = "";
            String longitude = "";
            int offsetLongitude = 10;
            int offsetLatitude = 9;
            String location = this.database.get(chat_id);
            Pattern longitudePattern = Pattern.compile("(longitude=)(\\d*\\.\\d*)");
            Matcher longitudeMatcher = longitudePattern.matcher(location);
            while (longitudeMatcher.find()) {
                String temperature = location.substring(longitudeMatcher.start(), longitudeMatcher.end());
                longitude = temperature.substring(offsetLongitude);
            }
            Pattern latitudePattern = Pattern.compile("(latitude=)(\\d*\\.\\d*)");
            Matcher latitudeMatcher = latitudePattern.matcher(location);
            while (latitudeMatcher.find()) {
                String temperature = location.substring(latitudeMatcher.start(), latitudeMatcher.end());
                latitude = temperature.substring(offsetLatitude);
            }
            if (StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude)) {
                return "You should change your location in settings before it.";
            } else {
                String appid = "&APPID=" + this.appid;
                String s = "http://api.openweathermap.org/data/2.5/weather?latitude=" + latitude + "&longitude=" + longitude + appid;
                try {
                    return weather.getWeather(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (message.equals("By taping")) {
            return "Write your city like \"/city YOURCITY\".";
        }

        if (message.startsWith("/city")) {
            int offsetCity = 5;
            String city = message.substring(offsetCity);
            if (city.equals("")) {
                return "Your should write \"/city YOURCITY\"";
            } else {
                Weather weather = new Weather();
                String appid = "&APPID=" + this.appid;
                String s = "http://api.openweathermap.org/data/2.5/weather?q=" + city + appid;
                try {
                    return weather.getWeather(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return message;
    }

    public String getBotUsername() {
        return "BotHelper";
    }

    public String getBotToken() {
        return this.token;
    }
}
