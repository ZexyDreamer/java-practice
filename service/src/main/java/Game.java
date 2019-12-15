import java.util.Random;

class Game {
    
    private String number = "";
    int bulls = 0;
    int cows = 0;

    Game() {
        Random random = new Random();
        do {
            this.number = "";
            for (int i = 0; i < 4; i++) {
                this.number += random.nextInt(10);
            }
        } while(check(number));
    }

    String Step(String number) {
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

    String Logic(String number) {
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
