package Handlers;

import Interfaces.IHandler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class SettingsHandler implements IHandler {

    @Override
    public String handle(ArrayList<KeyboardRow> keyboard, ReplyKeyboardMarkup keyboardMarkup, KeyboardRow firstKeyboardRow, KeyboardRow secondKeyboardRow) {
        firstKeyboardRow.add(new KeyboardButton("Change location"));
        firstKeyboardRow.add(new KeyboardButton("Back"));
        keyboard.add(firstKeyboardRow);
        keyboardMarkup.setKeyboard(keyboard);
        return "Send your geolocation and press \"Change location\" button";
    }
}
