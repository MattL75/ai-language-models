import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Bigram {

    public File source;
    public String contentString = "";
    public HashMap<String, HashMap<String, Integer>> contentMap = new HashMap<>();

    public Bigram(String fileName) {
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
        for (int i = 0; i < words.length - 1; ++i) {
            if (this.contentMap.get(words[i]) == null) {
                HashMap<String, Integer> temper = new HashMap<>();
                temper.put(words[i + 1], 1);
                this.contentMap.put(words[i], temper);
            } else {
                Integer n = this.contentMap.get(words[i]).get(words[i + 1]);
                this.contentMap.get(words[i]).put(words[i + 1], (n == null) ? 1 : ++n);
            }
        }
    }

    public void printMap() {
        System.out.println(this.contentMap.toString());
    }
}
