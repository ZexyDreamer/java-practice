package Telegram;

import Interfaces.IGame;

import java.util.ArrayList;
import java.util.Random;

class Game implements IGame {

    private final ArrayList<Integer> number = new ArrayList<>();
    private int bulls = 0;


    Game() {
        Random random = new Random();
        int i = 0;
        int digit;
        while (i < 4) {
            digit = random.nextInt(10);
            if (!this.number.contains(digit)) {
                this.number.add(digit);
                i++;
            }
        }
    }

    @Override
    public String gameStep(String number) {
        this.bulls = 0;
        int cows = 0;
        char[] n1 = number.toCharArray();
        for (int i = 0; i < n1.length; i++) {
            for (int j = 0; j < this.number.size(); j++) {
                if (n1[i] == this.number.get(j) && i == j) {
                    this.bulls += 1;
                }
                else if (n1[i] == this.number.get(j) && i != j) {
                    cows += 1;
                }
            }
        }
        return "bulls are " + this.bulls + " and cows are " + cows;
    }

    @Override
    public String gameLogic(String number) {
        if (number.length() != 4) {
            return "Only 4 digit numbers!!!";
        }
        if (checkNumber(number)) {
            return "All digits should be different!!!";
        }
        String result = this.gameStep(number);
        if (this.bulls == 4) {
            return "You win!";
        }
        return result;
    }

    private Boolean checkNumber(String number) {
        char[] chars = number.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length - 1; j++) {
                if (chars[i] == chars[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
