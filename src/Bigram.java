import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Bigram {

    public File source;
    public String contentString = "";
    public HashMap<String, HashMap<String, Integer>> contentMap = new HashMap<>();
    public int size;
    public int uniques;
    public final double SMOOTH = 0.5;
    private int type;
    public String outputString = "";
    public String[] sentenceArray;
    public double result;

    public Bigram(String fileName, int type) {
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

    private void countWords() {
        String[] words = this.contentString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");

        // Iterate through the words
        for (int i = 0; i < words.length; ++i) {
            String word = words[i];
            String previous = (i == 0 ? "." : words[i - 1] + "");

            // TODO Maybe we need to consider '.' as a unit
            // Currently only adding it to second level, nothing else done

            if (this.contentMap.get(word) == null) {
                this.contentMap.put(word, new HashMap<>());
                this.uniques++;
            }
            Integer n = this.contentMap.get(word).get(previous);
            this.contentMap.get(word).put(previous, (n == null) ? 1 : ++n);
        }
    }

    private void countChars() {
        String[] words = this.contentString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");

        // Iterate through the words
        for (String w : words) {
            for (int i = 0; i < w.length(); ++i) {
                String character = w.charAt(i) + "";
                String previous = (i == 0 ? "." : w.charAt(i - 1) + "");

                // TODO Maybe we need to consider '.' as a unit
                // Currently only adding it to second level, nothing else done

                if (this.contentMap.get(character) == null) {
                    this.contentMap.put(character, new HashMap<>());
                    this.uniques++;
                }
                Integer n = this.contentMap.get(character).get(previous);
                this.contentMap.get(character).put(previous, (n == null) ? 1 : ++n);
            }
        }
    }

    public void printMap() {
        System.out.println(this.contentMap.toString());
    }

    public double sentenceProbability(String sentence) {
        if (this.type == 0) {
            return wordsProbability(sentence);
        }
        return charsProbability(sentence);
    }

    @SuppressWarnings("Duplicates")
    private double wordsProbability(String sentence) {
        String uses = "";
        String[] temp = sentence.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        double probability = 0;

        for (int i = 0; i < temp.length; ++i) {
            String word = temp[i];
            String previous = (i == 0 ? "." : temp[i - 1]);

            if (this.contentMap.get(word) == null || this.contentMap.get(word).get(previous) == null) {
                double probs = Math.log10(SMOOTH / (this.size + uniques * uniques * SMOOTH));
                probability += probs;
                this.outputString +=  "P(" + word + "|" + previous + ") = " + probs + "==> log prob of sentence so far: " + probability + "+";
                uses += previous + word + "+";
            } else {
                int count = this.contentMap.get(word).get(previous);
                double probs = Math.log10((count + SMOOTH) / (this.size + uniques * uniques * SMOOTH));
                probability += probs;
                this.outputString +=  "P(" + word + "|" + previous + ") = " + probs + "==> log prob of sentence so far: " + probability + "+";
                uses += previous + word + "+";
            }
        }

        this.sentenceArray = uses.split("\\+");
        this.result = probability;
        return probability;
    }

    @SuppressWarnings("Duplicates")
    private double charsProbability(String sentence) {
        String uses = "";
        String[] temp = sentence.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        double probability = 0;

        for (String w : temp) {
            for (int j = 0; j < w.length(); ++j) {
                String character = w.charAt(j) + "";
                String previous = (j == 0 ? "." : w.charAt(j - 1) + "");

                if (this.contentMap.get(character) == null || this.contentMap.get(character).get(previous) == null) {
                    double probs = Math.log10(SMOOTH / (this.size + uniques * uniques * SMOOTH));
                    probability += probs;
                    this.outputString +=  "P(" + character + "|" + previous + ") = " + probs + "==> log prob of sentence so far: " + probability + "+";
                    uses += previous + character + "+";
                } else {
                    int count = this.contentMap.get(character).get(previous);
                    double probs = Math.log10((count + SMOOTH) / (this.size + uniques * uniques * SMOOTH));
                    probability += probs;
                    this.outputString +=  "P(" + character + "|" + previous + ") = " + probs + "==> log prob of sentence so far: " + probability + "+";
                    uses += previous + character + "+";
                }
            }
        }

        this.sentenceArray = uses.split("\\+");
        this.result = probability;
        return probability;
    }

    public double probability(String unit, String previous) {
        if (this.contentMap.get(unit) == null || this.contentMap.get(unit).get(previous) == null) {
            return Math.log10(SMOOTH / (this.size + uniques * uniques * SMOOTH));
        }

        // Otherwise, calculate it
        int count = this.contentMap.get(unit).get(previous);
        return Math.log10((count + SMOOTH) / (this.size + uniques * uniques * SMOOTH));
    }
}
