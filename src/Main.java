import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Get file names
        Scanner input = new Scanner(System.in);

        System.out.print("Enter training file name for English: ");
        String engFile = input.nextLine();
        Unigram engUni = new Unigram("texts/" + engFile);
        Bigram engBi = new Bigram("texts/" + engFile);
        engBi.printMap();

//        System.out.print("Enter training file name for French: ");
//        Unigram frUni = new Unigram(input.nextLine());

        // Need to find a third language
    }
}
