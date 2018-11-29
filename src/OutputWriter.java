import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OutputWriter {
    public String fileName;
    public String sentence;
    public Unigram frUni;
    public Unigram engUni;
    public Bigram frBi;
    public Bigram engBi;

    public OutputWriter() {}

    public OutputWriter(String sentence, String fileName, Unigram frUni, Bigram frBi, Unigram engUni, Bigram engBi) {
        this.fileName = fileName;
        this.sentence = sentence;
        this.engBi = engBi;
        this.engUni = engUni;
        this.frBi = frBi;
        this.frUni = frUni;
    }

    public void write() {

        String[] engUniString = this.engUni.outputString.split("\\+");
        String[] frUniString = this.frUni.outputString.split("\\+");

        String[] engBiString = this.engBi.outputString.split("\\+");
        String[] frBiString = this.frBi.outputString.split("\\+");

        try {
            PrintWriter writer = new PrintWriter("logs/" + fileName);
            String cumulation = "";
            cumulation += (sentence + "\n\n");

            // Unigram printing
            cumulation += ("UNIGRAM MODEL:\n\n");
            for (int i = 0; i < this.engUni.sentenceArray.length; ++i) {
                cumulation += ("UNIGRAM: " + this.engUni.sentenceArray[i] + "\n");

                cumulation += ("ENGLISH: " + engUniString[i] + "\n");
                cumulation += ("FRENCH: " + frUniString[i] + "\n\n");
            }

            // Uni result
            cumulation += ("According to the unigram model, this sentence is in " + getResultString(1) + ".\n\n");

            // Bigram printing
            cumulation += ("------------------\n\n");
            cumulation += ("BIGRAM MODEL:\n\n");
            for (int i = 0; i < this.engBi.sentenceArray.length; ++i) {
                cumulation += ("BIGRAM: " + this.engBi.sentenceArray[i] + "\n");

                cumulation += ("ENGLISH: " + engBiString[i] + "\n");
                cumulation += ("FRENCH: " + frBiString[i] + "\n\n");
            }

            // Bi result
            cumulation += ("According to the bigram model, this sentence is in " + getResultString(2) + ".\n");
            writer.write(cumulation);
            writer.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getResultString(int gram) {
        if (gram == 1) {
            if (engUni.result > frUni.result) {
                return "English";
            } else {
                return "French";
            }
        } else {
            if (engBi.result > frBi.result) {
                return "English";
            } else {
                return "French";
            }
        }
    }

}
