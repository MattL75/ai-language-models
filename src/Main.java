import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Scanner setup
        Scanner input = new Scanner(System.in);

        /* TODO I'm not sure if we're supposed to read the training every time
           Maybe it's better to do it once, save to file, and then read from there every time
           Because otherwise it takes a while to train
         */

        // English
        System.out.print("Enter training file name for English: ");
        String engFile = input.nextLine();
        Unigram engUni = new Unigram("texts/" + engFile);
        //Bigram engBi = new Bigram("texts/" + engFile);

        // French
        System.out.print("Enter training file name for French: ");
        String frFile = input.nextLine();
        Unigram frUni = new Unigram("texts/" + frFile);
       // Bigram frBi = new Bigram("texts/" + frFile);

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
            double eng = engUni.sentenceProbability(line);
            double fr = frUni.sentenceProbability(line);

            if (eng > fr) {
                System.out.println("The system has found that the sentence: \n\t" + line + "\nis from the ENGLISH Language.\nEN: " + eng + "\nFR: " + fr + "\n");
            } else {
                System.out.println("The system has found that the sentence: \n\t" + line + "\nis from the FRENCH Language.\nEN: " + eng + "\nFR: " + fr + "\n");
            }
        }
    }
}
