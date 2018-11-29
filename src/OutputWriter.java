import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

public class OutputWriter {
    public String fileName;
    public String sentence;
    public Unigram frUni;
    public Unigram engUni;
    public Unigram poUni;
    public Bigram frBi;
    public Bigram engBi;
    public Bigram poBi;

    public OutputWriter() {}

    public OutputWriter(String sentence, String fileName, Unigram frUni, Bigram frBi, Unigram engUni, Bigram engBi, Unigram poUni, Bigram poBi) {
        this.fileName = fileName;
        this.sentence = sentence;
        this.engBi = engBi;
        this.engUni = engUni;
        this.frBi = frBi;
        this.frUni = frUni;
        this.poUni = poUni;
        this.poBi = poBi;
    }

    public void write() {

        String[] engUniString = this.engUni.outputString.split("\\+");
        String[] frUniString = this.frUni.outputString.split("\\+");
        String[] poUniString = this.poUni.outputString.split("\\+");

        String[] engBiString = this.engBi.outputString.split("\\+");
        String[] frBiString = this.frBi.outputString.split("\\+");
        String[] poBiString = this.poBi.outputString.split("\\+");

        try {
            PrintWriter writer = new PrintWriter("logs/" + fileName);
            String cumulation = "";
            cumulation += (sentence + "\n\n");

            // Unigram printing
            cumulation += ("UNIGRAM MODEL:\n\n");
            for (int i = 0; i < this.engUni.sentenceArray.length; ++i) {
                cumulation += ("UNIGRAM: " + this.engUni.sentenceArray[i] + "\n");

                cumulation += ("ENGLISH: " + engUniString[i] + "\n");
                cumulation += ("FRENCH: " + frUniString[i] + "\n");
                cumulation += ("PORTUGUESE: " + poUniString[i] + "\n\n");
            }

            // Uni result
            cumulation += ("According to the unigram model, this sentence is in " + getResultString(1) + ".\n\n");

            // Bigram printing
            cumulation += ("------------------\n\n");
            cumulation += ("BIGRAM MODEL:\n\n");
            for (int i = 0; i < this.engBi.sentenceArray.length; ++i) {
                cumulation += ("BIGRAM: " + this.engBi.sentenceArray[i] + "\n");

                cumulation += ("ENGLISH: " + engBiString[i] + "\n");
                cumulation += ("FRENCH: " + frBiString[i] + "\n");
                cumulation += ("PORTUGUESE: " + poBiString[i] + "\n\n");
            }

            // Bi result
            cumulation += ("According to the bigram model, this sentence is in " + getResultString(2) + ".\n");
            writer.write(cumulation);
            writer.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @SuppressWarnings("Duplicates")
    private String getResultString(int gram) {
        if (gram == 1) {
            if (engUni.result > frUni.result && engUni.result > poUni.result) {
                return "English";
            } else if (frUni.result > engUni.result && frUni.result > poUni.result) {
                return "French";
            } else {
                return "Portuguese";
            }
        } else {
            if (engBi.result > frBi.result && engBi.result > poBi.result) {
                return "English";
            } else if (frBi.result > engBi.result && frBi.result > poBi.result) {
                return "French";
            } else {
                return "Portuguese";
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public void writeModels() {

        try {

            // ENG Unigram
            PrintWriter writer = new PrintWriter("models/unigramEN.txt");
            String temp = "";

            Iterator it = engUni.contentMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                temp += ("P(" + pair.getKey() + ") = " + engUni.probability(pair.getKey() + "") + "\n");
            }
            writer.write(temp);
            writer.flush();

            // Fr Unigram
            writer = new PrintWriter("models/unigramFR.txt");
            temp = "";

            it = frUni.contentMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                temp += ("P(" + pair.getKey() + ") = " + frUni.probability(pair.getKey() + "") + "\n");
            }
            writer.write(temp);
            writer.flush();

            // Por Unigram
            writer = new PrintWriter("models/unigramPO.txt");
            temp = "";

            it = poUni.contentMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                temp += ("P(" + pair.getKey() + ") = " + poUni.probability(pair.getKey() + "") + "\n");
            }
            writer.write(temp);
            writer.flush();

            // Eng Bigram
            writer = new PrintWriter("models/bigramEN.txt");
            temp = "";

            it = engBi.contentMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Iterator it2 = ((Map)pair.getValue()).entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair2 = (Map.Entry)it2.next();
                    temp += ("P(" + pair.getKey() + "|" + pair2.getKey() +") = " + engBi.probability(pair.getKey() + "", pair2.getKey() + "") + "\n");
                }
            }
            writer.write(temp);
            writer.flush();

            // Fr Bigram
            writer = new PrintWriter("models/bigramFR.txt");
            temp = "";

            it = frBi.contentMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Iterator it2 = ((Map)pair.getValue()).entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair2 = (Map.Entry)it2.next();
                    temp += ("P(" + pair.getKey() + "|" + pair2.getKey() +") = " + frBi.probability(pair.getKey() + "", pair2.getKey() + "") + "\n");
                }
            }
            writer.write(temp);
            writer.flush();

            // Por Bigram
            writer = new PrintWriter("models/bigramPO.txt");
            temp = "";

            it = poBi.contentMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Iterator it2 = ((Map)pair.getValue()).entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair2 = (Map.Entry)it2.next();
                    temp += ("P(" + pair.getKey() + "|" + pair2.getKey() +") = " + poBi.probability(pair.getKey() + "", pair2.getKey() + "") + "\n");
                }
            }
            writer.write(temp);
            writer.flush();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
