import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Bigram {

    public File source;
    public String contentString = "";
    public HashMap<String, HashMap<String, Integer>> contentMap = new HashMap<>();
    public HashMap<String, Integer> unigram = new HashMap<>();
    public int size;
    public int uniqueWords;
    public final double SMOOTH = 0.5;

    /*
    Maybe we need to consider periods. Currently, they are removed, but they might add some precision.
    To be determined.
     */

    public Bigram(String fileName) {
        this.source = new File(fileName);

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
        String[] words = this.contentString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        this.size = words.length;
        countWords(words);
    }

    // Get word counts
    private void countWords(String[] words) {
        for (int i = 0; i < words.length - 1; ++i) {
            if (this.contentMap.get(words[i]) == null) {
                this.contentMap.put(words[i], new HashMap<>());
                this.uniqueWords++;
            }
            Integer n = this.contentMap.get(words[i]).get(words[i + 1]);
            this.contentMap.get(words[i]).put(words[i + 1], (n == null) ? 1 : ++n);
            this.unigram.put(words[i], (n == null) ? 1 : ++n);
        }
    }

    public void printMap() {
        System.out.println(this.contentMap.toString());
    }

    public double sentenceProbability(String sentence) {
        String[] temp = sentence.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        double probability = 0;

        // TODO Case for first word... This is where periods would be useful.
        for (int i = 0; i < temp.length; ++i) {
            if (i == 0 || this.contentMap.get(temp[i - 1]) == null || this.contentMap.get(temp[i - 1]).get(temp[i]) == null) {

                // MAYBE WRONG
                probability += Math.log10(SMOOTH / (uniqueWords * SMOOTH));
            } else {
                int count = this.contentMap.get(temp[i - 1]).get(temp[i]);
                probability += Math.log10((count + SMOOTH) / (unigramProbability(temp[i - 1]) + uniqueWords * SMOOTH));
            }
        }

        return probability;
    }

    public double unigramProbability(String word) {

        if (this.unigram.get(word) == null) {
            return Math.log10((double)1 / this.size);
        }

        // Otherwise, calculate it
        double probability = (double)this.unigram.get(word) / this.size;
        return Math.log10(probability);
    }
}
