package Handlers;

import Interfaces.IHandler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class MainHandler implements IHandler {

    @Override
    public String handle(ArrayList<KeyboardRow> keyboard, ReplyKeyboardMarkup keyboardMarkup, KeyboardRow firstKeyboardRow, KeyboardRow secondKeyboardRow) {
        firstKeyboardRow.add(new KeyboardButton("Weather"));
        firstKeyboardRow.add(new KeyboardButton("Help"));
        secondKeyboardRow.add(new KeyboardButton("Settings"));
        secondKeyboardRow.add(new KeyboardButton("Game"));
        keyboard.add(firstKeyboardRow);
        keyboard.add(secondKeyboardRow);
        keyboardMarkup.setKeyboard(keyboard);
        return "Select necessary point";
    }
}
