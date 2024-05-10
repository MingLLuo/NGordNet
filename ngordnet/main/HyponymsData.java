package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class HyponymsData {
    private class Synonym {
        private int id;
        private LinkedList<String> word;
        private String info;

        Synonym(int id, LinkedList<String> word, String info) {
            this.id = id;
            this.word = word;
            this.info = info;
        }
    }

    private class WordIndex {
        private String word;
        private ArrayList<Integer> id = new ArrayList<>();

        WordIndex(String word, int i) {
            this.word = word;
            id.add(i);
        }
    }

    private int setSize = 0;
    private Graph idGraph;
    private ArrayList<Synonym> synonymData;
    private HashMap<String, WordIndex> wordSet;

    public HyponymsData(String synsetFile, String hyponymFile) {
        synonymData = new ArrayList<>();
        wordSet = new HashMap<>();
        setSynset(synsetFile);
        idGraph = new Graph(setSize);
        setHyponym(hyponymFile);
    }

    private void setSynset(String synsetFile) {
        In syn = new In(synsetFile);
        if (!syn.exists()) {
            throw new NoSuchElementException();
        }
        while (!syn.isEmpty()) {
            String line = syn.readLine();
            String[] data = line.split(",");
            int idParse = Integer.parseInt(data[0]);
            String[] word = data[1].split(" ");
            LinkedList<String> words = new LinkedList<>(List.of(word));
            for (String w : words) {
                if (!wordSet.containsKey(w)) {
                    wordSet.put(w, new WordIndex(w, idParse));
                } else {
                    WordIndex tmp = wordSet.get(w);
                    tmp.id.add(idParse);
                }
            }
            // construct synonym word
            String info = data[2];
            Synonym adder = new Synonym(idParse, words, info);
            synonymData.add(adder);
            setSize++;
        }
        syn.close();
    }

    private void setHyponym(String hyponymFile) {
        In hyp = new In(hyponymFile);
        if (!hyp.exists()) {
            throw new NoSuchElementException();
        }
        while (!hyp.isEmpty()) {
            String line = hyp.readLine();
            String[] data = line.split(",");
            int parent = Integer.parseInt(data[0]);
            int counter = 1;
            while (counter < data.length) {
                int child = Integer.parseInt(data[counter]);
                idGraph.addEdge(parent, child);
                counter++;
            }
        }
        hyp.close();
    }

    public ArrayList<String> findSynset(String word) {
        Stack<Integer> s = new Stack<>();
        if (!wordSet.containsKey(word)) {
            return null;
        }
        WordIndex now = wordSet.get(word);
        for (int I : now.id) {
            s.push(I);
        }

        ArrayList<String> lst = new ArrayList<>();
        while (!s.empty()) {
            int idNow = s.pop();
            for (int idAdj : idGraph.adj(idNow)) {
                s.push(idAdj);
            }
            lst.addAll(synonymData.get(idNow).word);
        }
        return lst;

    }

    public ArrayList<String> findSynset(List<String> words) {
        ArrayList<String> lst = null;
        for (String word : words) {
            ArrayList<String> tmp1 = findSynset(word);
            if (tmp1 == null) {
                return null;
            }
            if (lst == null) {
                lst = tmp1;
                continue;
            }

            lst.retainAll(tmp1); // del not same
        }
        if (lst == null || lst.size() == 0) {
            return null;
        }
        Collections.sort(lst);

        ArrayList<String> ret = new ArrayList<>(lst.size());

        String start = lst.get(0);
        ret.add(start);
        for (String w : lst) {
            if (!w.equals(start)) {
                start = w;
                ret.add(w);
            }
        } // only give one
        return ret;
    }
}
