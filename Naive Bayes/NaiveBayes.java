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


public class NaiveBayes {

    private static ArrayList<Map<String, Integer>> newEmails = new ArrayList<Map<String, Integer>>();

    public NaiveBayes(ArrayList<Map<String, Integer>> newEmails) {
        this.newEmails = newEmails;
    }
    
    public static double[] P(String word, double ham_emails, double spam_emails, Map<String, Integer> ham, Map<String, Integer> spam) {
        
        double[] array = new double[3];
        double total = ham_emails + spam_emails;
        double exists = -1;
        double isHam2 = -1;
        double isSpam2 = -1;
        double value = 0;

        if (ham.containsKey(word)) {
            double value1 = (double)(ham.get(word));
            value += value1;
            isHam2 = value1 / ham_emails; // <--
        } else {
            isHam2 = 1 / (ham_emails + 2);
        }

        if (spam.containsKey(word)) {
            double value2 = (double)(spam.get(word));
            value += value2;
            isSpam2 = value2 / spam_emails; // <--
        } else {
            isSpam2 = 1 / (spam_emails +2);
        }

        exists = value / total; // <--
        
        array[0] = exists;
        array[1] = isHam2;
        array[2] = isSpam2;

        /*System.out.println("X = " + word);
        System.out.println("P(X=1) : "+ exists);
        System.out.println("P(X=1|C=0) : "+ isHam);
        System.out.println("P(X=1|C=1) : "+ isSpam);*/

        return array;
    }

    public void accuracy(double newHam_emails, double ham_emails, double spam_emails, Map<String, Integer> ham, Map<String, Integer> spam, String type) {

        double ham_counter = 0;
        double spam_counter = 0;
        double totalEmails = ham_emails + spam_emails;
        double p_ham = ham_emails / totalEmails;
        double p_spam = spam_emails / totalEmails;

        for (int i = 0; i < newEmails.size(); i++) {
            double isHam = 1;
            double isSpam = 1;
            
            Map<String, Integer> n_email = newEmails.get(i);
            for (Map.Entry mapElement : ham.entrySet()) { 
                double[] array = P((String)mapElement.getKey(), ham_emails, spam_emails, ham, spam);
                
                if (n_email.containsKey(mapElement.getKey())) {
                    if (array[1] != 0) {
                        isHam *= array[1];
                    }
                    
                } else {
                    if ((1 - array[1]) != 0 ) {
                        isHam *= 1 - array[1];
                    }
                }
            }

            for (Map.Entry mapElement : spam.entrySet()) { 
                double[] array = P((String)mapElement.getKey(), ham_emails, spam_emails, ham, spam);
                
                if (n_email.containsKey(mapElement.getKey())) {
                    if (array[2] != 0) {
                        isSpam *= array[2];
                    }
                } else {
                    if (1 - array[2] != 0 ) {
                        isSpam *= 1 - array[2];
                    }
                    
                }
            }

            if ((isHam * ((p_ham ))) > (isSpam * ((p_spam)))) {
                //System.out.println((isHam * ((p_ham +1)/2))+" > "+(isSpam * ((p_spam + 1)/ 2)));
                ham_counter++;
            } else {
                //System.out.println((isHam * ((p_ham +1)/2))+" < "+(isSpam * ((p_spam + 1)/ 2)));
                spam_counter++;
            }
        }

        if (type.equals("ham")) {

            double h_a = (ham_counter / newEmails.size()) * 100;
            System.out.println("Accuracy: "+ h_a + " %" );

        } else if (type.equals("spam")) {

            double s_a = (spam_counter / newEmails.size()) * 100;
            System.out.println("Accuracy: "+ s_a + " %");

        }
        


        
    }


}