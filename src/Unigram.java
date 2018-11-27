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

    public Unigram(String fileName) {
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

        countWords();
    }

    private void countWords() {
        String[] words = this.contentString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        this.size = words.length;
        for (String w : words) {
            Integer n = this.contentMap.get(w);
            this.contentMap.put(w, (n == null) ? 1 : ++n);
        }
    }

    public void printMap() {
        System.out.println(this.contentMap.toString());
    }

    public double sentenceProbability(String sentence) {
        String[] temp = sentence.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        double probability = 0;

        for (String word : temp) {
            probability += wordProbability(word);
        }

        return probability;
    }

    public double wordProbability(String word) {

        // Assume probability 1 if word is not present
        // Not sure if this counts as smoothing, as size is not increased.
        // Seems to perform pretty well though...
        if (this.contentMap.get(word) == null) {
            return Math.log10((double)1 / this.size);
        }

        // Otherwise, calculate it
        double probability = (double)this.contentMap.get(word) / this.size;
        return Math.log10(probability);
    }
}
