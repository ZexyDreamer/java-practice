package Handlers;

import Interfaces.IHandler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class HelpHandler implements IHandler {
    @Override
    public String handle(ArrayList<KeyboardRow> keyboard, ReplyKeyboardMarkup keyboardMarkup, KeyboardRow firstKeyboardRow, KeyboardRow secondKeyboardRow) {
        return "1) If you want to see the weather, click the \"Weather\" button and " +
                "select necessary point.\n 2) If you want to change your location, click the \"Settings\" button.\n " +
                "3) If you want to play in \"Bulls and Cows\", click the \"Game\" button.";
    }
}
