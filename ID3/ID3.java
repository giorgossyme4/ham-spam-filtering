import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ID3 {

    Map<String, Integer> ham;
    Map<String, Integer> spam;
    Map<String, Double> entropies;
    Map<String, Double> ig;

    public ID3() {
        this.ham = null;
        this.spam = null;
        this.entropies = new HashMap<String, Double>();
    }

    public void setMaps(Map<String, Integer> ham, Map<String, Integer> spam) {
        this.ham = ham;
        this.spam = spam;
    }

    public void Entropy() {

        double entropy = 0;

        for (Map.Entry<String, Integer> entry : ham.entrySet()) {
            
            String key = entry.getKey();
            double value1 = entry.getValue();

            if (spam.containsKey(key)) {
                double value2 = spam.get(key);
                entropy = - (value1/(value1+value2)) * (Math.log(value1/(value1+value2))/Math.log(2)) - (value2/(value1+value2)) * (Math.log(value2/(value1+value2))/Math.log(2));
            } else {
                entropy = 0;
            }

            entropies.put(key, entropy);
        }

        for (Map.Entry<String, Integer> entry : spam.entrySet()) {
            String key = entry.getKey();
            double value1 = entry.getValue();

            if (entropies.containsKey(key)) {
                continue;
            }

            if (ham.containsKey(key)) {
                double value2 = ham.get(key);
                entropy = - (value1/(value1+value2)) * (Math.log(value1/(value1+value2))/Math.log(2)) - (value2/(value1+value2)) * (Math.log(value2/(value1+value2))/Math.log(2));
            } else {
                entropy = 0;
            }

            entropies.put(key, entropy);
        }
    }

    public void informationGain() {
        double spamSize = spam.size();
        double hamSize = ham.size();

        double targetEntropy = - (hamSize/(hamSize+spamSize)) * (Math.log(hamSize/(hamSize+spamSize))/Math.log(2)) - (spamSize/(hamSize+spamSize)) * (Math.log(spamSize/(hamSize+spamSize))/Math.log(2));   //entropy of ham or spam
        
        ig = new HashMap<String, Double>();
        for (Map.Entry<String, Double> entry : entropies.entrySet()) {
            String key = entry.getKey();
            double value = entry.getValue();
            double igOfAttribute = targetEntropy - value;
            ig.put(key, igOfAttribute);
        }
        ig = sortInformationGains(500);
    }

    public Map<String, Double> sortInformationGains(int endpoint) {
        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(ig.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Double>>() {

            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                return (-1) * o1.getValue().compareTo(o2.getValue());
            } 
        });

        Map<String, Double> sorted = new HashMap<String, Double>();

        for (int i = 0; i < endpoint; i++) {
            sorted.put(list.get(i).getKey(), list.get(i).getValue());
        }
        
        return sorted;
    }

    public Map<String, Double> getIG() {
        return ig;
    }

    public Map<String, Double> getEntropies() {
        return entropies;
    }

    public void printIGs() {
        for (Map.Entry<String, Double> entry : ig.entrySet()) {
            String key = entry.getKey();
            double value1 = entry.getValue();

            System.out.println(key + " " + value1);
        }
    }

    public void printEntropies() {
        for (Map.Entry<String, Double> entry : entropies.entrySet()) {
            String key = entry.getKey();
            double value1 = entry.getValue();

            System.out.println(key + " " + value1);
        }
    }
}