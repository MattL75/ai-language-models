import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Scanner setup
        Scanner input = new Scanner(System.in);

        // English
        System.out.print("Enter training file name for English: ");
        String engFile = input.nextLine();
        Unigram engUni = new Unigram("texts/" + engFile);
        Bigram engBi = new Bigram("texts/" + engFile);
        engBi.printMap();

        // French
        System.out.print("Enter training file name for French: ");
        String frFile = input.nextLine();
        Unigram frUni = new Unigram("texts/" + frFile);
        Bigram frBi = new Bigram("texts/" + frFile);
        frBi.printMap();

        // Need to find a third language to do
    }
}
