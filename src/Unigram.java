import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Unigram {

    public File source;
    public String contentString = "";
    public Map<String, Integer> contentMap = new HashMap<>();
    public int size;
    public int uniques;
    public final double SMOOTH = 0.5;
    private int type;
    public String outputString = "";
    public String[] sentenceArray;
    public double result;

    // Type 0 == words, type 1 == characters
    public Unigram(String fileName, int type) {
        this.source = new File(fileName);
        this.type = type;

        // Setup file scanner
        Scanner reader = new Scanner(fileName);
        try {
            reader = new Scanner(this.source);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Read file
        while (reader.hasNext()) {
            contentString += reader.nextLine();

            // Deal with the issue of new line
            if (this.contentString.length() > 0 && Character.isLetter(contentString.charAt(contentString.length() - 1))) {
                contentString += " ";
            }
        }

        if (type == 0) {
            countWords();
        } else {
            countChars();
        }
    }

    private void countChars() {
        String[] words = this.contentString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        this.size = 0;
        for (String w : words) {
            for (int i = 0; i < w.length(); ++i) {
                String character = w.charAt(i) + "";
                Integer n = this.contentMap.get(character);
                if (n == null) {
                    ++this.uniques;
                }
                this.contentMap.put(character, (n == null) ? 1 : ++n);
                ++this.size;
            }
        }
    }

    private void countWords() {
        String[] words = this.contentString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        this.size = words.length;
        for (String w : words) {
            Integer n = this.contentMap.get(w);
            if (n == null) {
                this.uniques++;
            }
            this.contentMap.put(w, (n == null) ? 1 : ++n);
        }
    }

    public void printMap() {
        System.out.println(this.contentMap.toString());
    }

    public double sentenceProbability(String sentence) {
        String uses = "";
        String[] temp = sentence.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        double probability = 0;

        if (type == 0) {
            for (String unit : temp) {
                double probs = probability(unit);
                probability += probs;
                this.outputString += "P(" + unit + ") = " + probs + " ==> log prob of sentence so far: " + probability + "+";
                uses += unit + "+";
            }
        } else {
            for (String unit : temp) {
                for (int i = 0; i < unit.length(); ++i) {
                    double probs = probability(unit.charAt(i) + "");
                    probability += probs;
                    this.outputString += "P(" + unit.charAt(i) + ") = " + probs + " ==> log prob of sentence so far: " + probability + "+";
                    uses += unit.charAt(i) + "+";
                }
            }
        }

        this.sentenceArray = uses.split("\\+");
        this.result = probability;
        return probability;
    }

    public double probability(String unit) {

        if (this.contentMap.get(unit) == null) {
            return Math.log10(SMOOTH / (this.size + SMOOTH * this.uniques));
        }

        // Otherwise, calculate it
        return Math.log10(this.contentMap.get(unit) + SMOOTH) / (this.size + SMOOTH * this.uniques);
    }
}
