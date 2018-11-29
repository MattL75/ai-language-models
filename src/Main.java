import java.io.File;
import java.io.FileNotFoundException;
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

        // French
        System.out.print("Enter training file name for French: ");
        //String frFile = input.nextLine();
        String frFile = "fr-vingt-mille-lieues-sous-les-mers.txt";
        Unigram frUni = new Unigram("texts/" + frFile, TYPE);
        Bigram frBi = new Bigram("texts/" + frFile, TYPE);

        // Portuguese
        System.out.print("Enter training file name for Portuguese: ");
        //String poFile = input.nextLine();
        String poFile = "po-emma.txt";
        Unigram poUni = new Unigram("texts/" + poFile, TYPE);
        Bigram poBi = new Bigram("texts/" + poFile, TYPE);

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
        writer.poUni = poUni;
        writer.poBi = poBi;

        int counter = 1;
        while (reader.hasNext()) {
            String line = reader.nextLine();

            double engUniProb = engUni.sentenceProbability(line);
            double frUniProb = frUni.sentenceProbability(line);
            double poUniProb = poUni.sentenceProbability(line);

            double engBiProb = engBi.sentenceProbability(line);
            double frBiProb = frBi.sentenceProbability(line);
            double poBiProb = poBi.sentenceProbability(line);

            // Console printing
            System.out.println(line);
            System.out.println("Unigram: " + getResultString(engUniProb, frUniProb, poUniProb));
            System.out.println("Bigram: " + getResultString(engBiProb, frBiProb, poBiProb));
            System.out.println();

            // Code to print to file
            writer.fileName = "out" + counter + ".txt";
            writer.sentence = line;
            writer.write();

            ++counter;
        }
    }

    private static String getResultString(double eng, double fr, double po) {
        if (eng > fr && eng > po) {
            return "ENGLISH";
        } else if (fr > eng && fr > po) {
            return "FRENCH";
        } else {
            return "PORTUGUESE";
        }
    }
}
