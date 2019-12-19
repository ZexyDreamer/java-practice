package Telegram;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import DataBase.Database;
import Main.Parser;

public class Bot extends TelegramLongPollingBot {

    private String token;
    private String appid;
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
            sendMessage.setText(this.game.Logic(message));
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
        String helpMessage = "1) If you want to see the weather, click the \"Weather\" button and " +
                "select necessary point.\n 2) If you want to change your location, click the \"Settings\" button.\n " +
                "3) If you want to play in \"Bulls and Cows\", click the \"Game\" button.";

        if (message.equals("/start") | message.equals("Back")) {
            keyboardRowFirstRow.add(new KeyboardButton("Weather"));
            keyboardRowFirstRow.add(new KeyboardButton("Help"));
            keyboardRowSecondRow.add(new KeyboardButton("Settings"));
            keyboardRowSecondRow.add(new KeyboardButton("Game"));
            keyboard.add(keyboardRowFirstRow);
            keyboard.add(keyboardRowSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Select necessary point";
        }

        if (message.equals("Game")) {
            this.gameMode = true;
            this.game = new Game();
            return "Game is ready, write number.";
        }

        if (message.equals("Settings")) {
            keyboardRowFirstRow.add(new KeyboardButton("Change location"));
            keyboardRowFirstRow.add(new KeyboardButton("Back"));
            keyboard.add(keyboardRowFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Send your geolocation and press \"Change location\" button";
        }

        if (message.equals("Change location")) {
            database.change(location, chat_id);
            return "Changed";
        }

        if (message.equals("Weather")) {
            keyboardRowFirstRow.add(new KeyboardButton("By geolocation"));
            keyboardRowFirstRow.add(new KeyboardButton("By taping"));
            keyboardRowSecondRow.add(new KeyboardButton("Back"));
            keyboard.add(keyboardRowFirstRow);
            keyboard.add(keyboardRowSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Select necessary point";
        }

        if (message.equals("Help")) {
            return helpMessage;
        }

        if (message.equals("By geolocation")) {
            Weather weather = new Weather();
            String lat = "";
            String lon = "";
            int offsetLon = 10;
            int offsetLat = 9;
            String loc = database.get(chat_id);
            Pattern pattern1 = Pattern.compile("(longitude=)(\\d*\\.\\d*)");
            Matcher matcher1 = pattern1.matcher(loc);
            while (matcher1.find()) {
                String temp = loc.substring(matcher1.start(), matcher1.end());
                lon = temp.substring(offsetLon);
            }
            Pattern pattern2 = Pattern.compile("(latitude=)(\\d*\\.\\d*)");
            Matcher matcher2 = pattern2.matcher(loc);
            while (matcher2.find()) {
                String temp = loc.substring(matcher2.start(), matcher2.end());
                lat = temp.substring(offsetLat);
            }
            if (StringUtils.isBlank(lat) || StringUtils.isBlank(lon)) {
                return "You should change your location in settings before it.";
            } else {
                String appid = "&APPID=" + this.appid;
                String s = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + appid;
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
