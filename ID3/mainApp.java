import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class mainApp {

    public static int load(String filepath, Map<String, Integer> email)  {

        int num_emails = -1;

        try {
            int i=1;
            Path dir = FileSystems.getDefault().getPath(filepath);
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);

            for (Path path : stream) {
                String p = filepath + "/" +(path.getFileName()).toString();
                countWords(p, email);
                i++;
            }
            stream.close();
            num_emails = (i - 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return num_emails;
    }

    public static void countWords(String filename, Map<String, Integer> email) throws FileNotFoundException {
        String[] array = {"the", "for", "i", "you", "am", "a", "on", "in", "when", "be", "or", "is", "are", "by", "of", "as", "have", "has",
                                "from", "to", "your", "will", "at", "been", "and", "this", "that", "all", "a", "about" , "above", "after", "again", "against",
                                "am", "an", "any", "me", "my", "myself", "we", "our", "ours", "ourselves", "yours", "yourself", "yourselves", "he", "him", "his", 
                                "himself", "she", "her", "hers", "herself", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom",
                                "these", "those", "am", "was", "were", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an",
                                "but", "if", "because", "as", "until", "while", "with", "between", "into", "through", "during", "before", "below", 
                                "up", "down", "out", "off", "over", "under", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", 
                                "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", 
                                "too", "very", "s", "t", "can", "just", "don", "should", "now"};

        List<String> stopWords = Arrays.asList(array);

        Scanner file = new Scanner(new File(filename));
        while (file.hasNext()) {
            String word = file.next();
            word = word.replaceAll("[^a-zA-Z]","");
            word = word.toLowerCase();
            if (!word.isEmpty()) {
                if (!stopWords.contains(word)) {
                    Integer count = email.get(word);
                    if (count != null) {
                        count++;
                    } else {
                        count = 1;
                    }
                    email.put(word,count);
                    
                }
            }
            
        }
        file.close();
    }

    public static void main(String args[]) {
        
        Map<String, Integer> hams = new HashMap<String, Integer>();
        Map<String, Integer> spams = new HashMap<String, Integer>();
    
        ID3 dt = new ID3();

        load("enron6/ham", hams);
        load("enron6/spam", spams);
        
        dt.setMaps(hams, spams);
        
        dt.Entropy();
        dt.informationGain();
        dt.printIGs();
        //dt.printEntropies();
    }
}