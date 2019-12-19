public interface IGame {
    String number = "";
    int bulls = 0;
    int cows = 0;

    String Step(String number);
    String Logic(String number);
    Boolean check(String number);
}
