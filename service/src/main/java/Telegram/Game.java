package Telegram;

import Interfaces.IGame;

import java.util.Random;

class Game implements IGame {
    
    private String number;
    int bulls = IGame.bulls;
    int cows = IGame.cows;


    Game() {
        Random random = new Random();
        do {
            this.number = "";
            for (int i = 0; i < 4; i++) {
                this.number += random.nextInt(10);
            }
        } while(check(number));
    }

    public String Step(String number) {
        this.bulls = 0;
        this.cows = 0;
        char[] n1 = number.toCharArray();
        char[] n2 = this.number.toCharArray();
        for (int i = 0; i < n1.length; i++) {
            for (int j = 0; j < n2.length; j++) {
                if (n1[i] == n2[j] && i == j) {
                    this.bulls += 1;
                }
                else if (n1[i] == n2[j] && i != j) {
                    this.cows += 1;
                }
            }
        }
        return "bulls are " + this.bulls + " and cows are " + this.cows;
    }

    public String Logic(String number) {
        if (number.length() != 4) {
            return "Only 4 digit numbers!!!";
        }
        if (check(number)) {
            return "All digits are different!!!";
        }
        String result = this.Step(number);
        if (this.bulls == 4) {
            return "You win!";
        }
        return result;
    }

    Boolean check(String number) {
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
