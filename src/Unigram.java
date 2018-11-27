import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Unigram {

    public File source;
    public String contentString = "";
    public Map<String, Integer> contentMap = new HashMap<>();

    public Unigram(String fileName) {
        this.source = new File(fileName);

        // Setup file scanner
        Scanner reader = new Scanner(fileName);
        try {
            reader = new Scanner(this.source);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("File reading error.");
        }

        // Read file
        while (reader.hasNext()) {
            contentString += reader.nextLine();

            // Deal with the issue of new line
            if (Character.isLetter(contentString.charAt(contentString.length() - 1))) {
                contentString += " ";
            }
        }

        countWords();
    }

    private void countWords() {
        String[] words = this.contentString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        for (String w : words) {
            Integer n = this.contentMap.get(w);
            this.contentMap.put(w, (n == null) ? 1 : ++n);
        }
    }

    public void printMap() {
        System.out.println(this.contentMap.toString());
    }
}
