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


public class Main {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void main(String args[]) {

        long startTime = System.nanoTime();

        Data d = new Data();

        Map<String, Integer> ham_db = new HashMap<String, Integer>();
        Map<String, Integer> spam_db = new HashMap<String, Integer>();
        double endPoint = 1000;

        double ham_emails = d.load("enron1/ham",ham_db, false);
        System.out.println("Ham: "+ ham_emails);
        
        Map<String, Integer> ham = new HashMap<String, Integer>();
        if (ham_db.size() >= endPoint) {
            ham = d.dbSort(ham_db, ham_emails, endPoint);
        } else {
            ham = ham_db;
        }

        Data d2 = new Data();

        double spam_emails = d2.load("enron1/spam",spam_db, false);
        System.out.println("Spam : "+ spam_emails);
        
        Map<String, Integer> spam = new HashMap<String, Integer>();
        if (spam_db.size() >= endPoint) {
            spam = d2.dbSort(spam_db, spam_emails, endPoint);
        } else {
            spam = spam_db;
        }
        

        System.out.println();

        Data d3 = new Data();
        Data d4 = new Data();
        Map<String, Integer> newHam_db = new HashMap<String, Integer>();
        Map<String, Integer> newSpam_db = new HashMap<String, Integer>();

        double newHam_emails = d3.load("enron3/ham", newHam_db, true);
        System.out.println("New Ham: "+ newHam_emails);
        double newSpam_emails = d4.load("enron3/spam", newSpam_db, true);

        NaiveBayes nb2 = new NaiveBayes(d3.getArrayList());
        NaiveBayes nb = new NaiveBayes(d4.getArrayList());

        

        nb2.accuracy(newHam_emails, ham_emails, spam_emails, ham, spam, "ham");
        System.out.println();
        System.out.println("New Spam: "+ newSpam_emails);
        nb.accuracy(newSpam_emails, ham_emails, spam_emails, ham, spam, "spam");

        



        long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double seconds = (double)totalTime / 1_000_000_000.0;
		System.out.println();
        System.out.println("Run time: " + df2.format(seconds) + "s");
    }


}