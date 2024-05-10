package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private HyponymsData hyp;
    private NGramMap ngm;
    private ArrayList<Node> node;

    private class Node {
        private int index;
        private double weight;

        Node(int index, double weight) {
            this.index = index;
            this.weight = weight;
        }
    }

    public HyponymsHandler(HyponymsData hyp, NGramMap ngm) {
        this.hyp = hyp;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        ArrayList<String> word = hyp.findSynset(words);
        node = new ArrayList<>();
        if (word == null) {
            return "[]";
        }
        if (k > 0) {
            int counter = 0;
            for (String w : word) {
                TimeSeries d;
                double sum = 0;
                List<Double> dou = null;
                try {
                    d = ngm.countHistory(w, startYear, endYear);
                    dou = d.data();
                } catch (NullPointerException e) {
                    d = null;
                }
                if (d == null || dou.size() == 0) {
                    node.add(new Node(counter++, 0));
                    continue;
                }

                for (double weigh : d.data()) {
                    sum += weigh;
                }

                node.add(new Node(counter++, sum));
            }
            node.sort((o1, o2) -> Double.compare(o2.weight, o1.weight));

            ArrayList<String> newLst = new ArrayList<>();
            for (int i = 0; i < k && node.size() >= i + 1; i++) {
                try {
                    int I = node.get(i).index;
                    if (node.get(i).weight == 0) {
                        break;
                    }
                    newLst.add(word.get(I));

                } catch (IndexOutOfBoundsException ignored) {
                    continue;
                }

            }

            word = newLst;
            Collections.sort(word);
        }
        return word.toString();
    }
}
