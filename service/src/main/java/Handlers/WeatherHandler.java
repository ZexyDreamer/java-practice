package Handlers;

import Interfaces.IHandler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class WeatherHandler implements IHandler {
    @Override
    public String handle(ArrayList<KeyboardRow> keyboard, ReplyKeyboardMarkup keyboardMarkup, KeyboardRow firstKeyboardRow, KeyboardRow secondKeyboardRow) {
        firstKeyboardRow.add(new KeyboardButton("By geolocation"));
        firstKeyboardRow.add(new KeyboardButton("By taping"));
        secondKeyboardRow.add(new KeyboardButton("Back"));
        keyboard.add(firstKeyboardRow);
        keyboard.add(secondKeyboardRow);
        keyboardMarkup.setKeyboard(keyboard);
        return "Select necessary point";
    }
}
