package Interfaces;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public interface IHandler {
    String handle(ArrayList<KeyboardRow> keyboard, ReplyKeyboardMarkup keyboardMarkup, KeyboardRow firstKeyboardRow, KeyboardRow secondKeyboardRow);
}
