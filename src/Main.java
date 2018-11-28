import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    /*
    NEED TO CHANGE UNIGRAM AND BIGRAM
    CURRENTLY ONLY DOES WORDS, BUT TEACHER WANTS CHARACTERS
     */

    public static void main(String[] args) {

        // Scanner setup
        Scanner input = new Scanner(System.in);

        // English
        System.out.print("Enter training file name for English: ");
       // String engFile = input.nextLine();
        String engFile = "en-moby-dick.txt";
        Unigram engUni = new Unigram("texts/" + engFile);
        Bigram engBi = new Bigram("texts/" + engFile);
        //engBi.printMap();

        // French
        System.out.print("Enter training file name for French: ");
        //String frFile = input.nextLine();
        String frFile = "fr-vingt-mille-lieues-sous-les-mers.txt";
        Unigram frUni = new Unigram("texts/" + frFile);
        Bigram frBi = new Bigram("texts/" + frFile);
       // frBi.printMap();

        // Need to find a third language to do

        System.out.print("Enter file to be tested: ");
        String fileName = input.nextLine();
        Scanner reader = new Scanner(fileName);
        try {
            reader = new Scanner(new File("texts/" + fileName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        while (reader.hasNext()) {
            String line = reader.nextLine();

            // To test unigram, replace 'engBi' with 'engUni' etc
            // To test bigram, replace 'engUni' with 'engBi' etc
            double eng = engBi.sentenceProbability(line);
            double fr = frBi.sentenceProbability(line);

            if (eng > fr) {
                System.out.println("The system has found that the sentence: \n\t" + line + "\nis from the ENGLISH Language.\nEN: " + eng + "\nFR: " + fr + "\n");
            } else {
                System.out.println("The system has found that the sentence: \n\t" + line + "\nis from the FRENCH Language.\nEN: " + eng + "\nFR: " + fr + "\n");
            }
        }
    }
}
