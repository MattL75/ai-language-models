import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final int TYPE = 1;

        // Scanner setup
        Scanner input = new Scanner(System.in);

        // English
        System.out.print("Enter training file name for English: ");
       // String engFile = input.nextLine();
        String engFile = "en-moby-dick.txt";
        Unigram engUni = new Unigram("texts/" + engFile, TYPE);
        Bigram engBi = new Bigram("texts/" + engFile, TYPE);
        //engBi.printMap();

        // French
        System.out.print("Enter training file name for French: ");
        //String frFile = input.nextLine();
        String frFile = "fr-vingt-mille-lieues-sous-les-mers.txt";
        Unigram frUni = new Unigram("texts/" + frFile, TYPE);
        Bigram frBi = new Bigram("texts/" + frFile, TYPE);
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

        OutputWriter writer = new OutputWriter();
        writer.engUni = engUni;
        writer.frUni = frUni;
        writer.engBi = engBi;
        writer.frBi = frBi;

        int counter = 1;
        while (reader.hasNext()) {
            String line = reader.nextLine();

            double engUniProb = engUni.sentenceProbability(line);
            double frUniProb = frUni.sentenceProbability(line);

            double engBiProb = engBi.sentenceProbability(line);
            double frBiProb = frBi.sentenceProbability(line);

            // Console printing
            System.out.println(line);
            System.out.println("Unigram: " + (engUniProb > frUniProb ? "ENGLISH" : "FRENCH"));
            System.out.println("Bigram: " + (engBiProb > frBiProb ? "ENGLISH" : "FRENCH"));
            System.out.println();

            // Code to print to file
            writer.fileName = "out" + counter + ".txt";
            writer.sentence = line;
            writer.write();

            ++counter;
        }
    }
}
