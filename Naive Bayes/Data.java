import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter; 
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.util.Map.Entry.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;

public class Data {

    private static ArrayList<Map<String, Integer>> newEmails = new ArrayList<Map<String, Integer>>();

    public Data() {
    
    }

    public ArrayList<Map<String, Integer>> getArrayList() {
        return this.newEmails;
    }

    public double load(String filePath, Map<String, Integer> email, boolean test)  {

        int num_emails = -1;
        
        try {
            int i=1;
            Path dir = FileSystems.getDefault().getPath( filePath );
            DirectoryStream<Path> stream = Files.newDirectoryStream( dir );
            for (Path path : stream) {

                //System.out.println( "" + i + ": " + path.getFileName() );
                String p = filePath + "/" +(path.getFileName()).toString();
                if (test == true) {
                    countWords(p, email, true);
                } else {
                    countWords(p, email, false);
                }
                i++;
            }
            stream.close();
            num_emails = (i - 1);

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return num_emails;
    }

    public Map<String, Integer> dbSort(Map<String, Integer> email, double email_num, double endPoint) {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(email.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {

            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return (-1)* o1.getValue().compareTo(o2.getValue());
            } 
        });

        Map<String, Integer> hashMap = new HashMap<String, Integer>();

        for (int i = 0; i < endPoint; i++) {
            hashMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        
        return hashMap;
    }

    public static void countWords(String fileName, Map<String, Integer> email, boolean test) throws FileNotFoundException {
        String[] array = {/*"the", "for", "i", "you", "am", "a", "on", "in", "when", "be", "or", "is", "are", "by", "of", "as", "have", "has",
                                "from", "to", "your", "will", "at", "been", "and", "this", "that", "all", "a", "about" , "above", "after", "again", "against",
                                "am", "an", "any", "me", "my", "myself", "we", "our", "ours", "ourselves", "yours", "yourself", "yourselves", "he", "him", "his", 
                                "himself", "she", "her", "hers", "herself", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom",
                                "these", "those", "am", "was", "were", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an",
                                "but", "if", "because", "as", "until", "while", "with", "between", "into", "through", "during", "before", "below", 
                                "up", "down", "out", "off", "over", "under", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", 
                                "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", 
    "too", "very", "s", "t", "can", "just", "don", "should", "now"*/};

        List<String> stop_words = Arrays.asList(array);

        if (test == false) {
            Scanner file = new Scanner(new File(fileName));
            while (file.hasNext()) {
                String word = file.next();
                word = word.replaceAll("[^a-zA-Z]","");
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    if (!stop_words.contains(word)) {
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
        } else {
            Map<String, Integer> temp = new HashMap<String, Integer>();
            Scanner file = new Scanner(new File(fileName));
            while (file.hasNext()) {
                String word = file.next();
                word = word.replaceAll("[^a-zA-Z]","");
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    if (!stop_words.contains(word)) {
                        Integer count = 0;
                        if (count != null) {
                            count++;
                        }
                        temp.put(word,count);
                    }
                }
            }
            newEmails.add(temp);
            file.close();
        }
    }

}