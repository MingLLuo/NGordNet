package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.proj2b_testing.AutograderBuddy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestSmallSynsets {
    @Test
    public void testFileOpen() {
        HyponymsData hyp = new HyponymsData("./data/wordnet/synsets11.txt", "./data/wordnet/hyponyms11.txt");
    }
    @Test
    public void testSyn11TrueAns() {
        HyponymsData hyp = new HyponymsData("./data/wordnet/synsets11.txt", "./data/wordnet/hyponyms11.txt");
        System.out.println(hyp.findSynset("action"));
        System.out.println(hyp.findSynset("descent"));
    }
    @Test
    public void testSyn16TrueAns() {
        HyponymsData hyp = new HyponymsData("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");
        System.out.println(hyp.findSynset("change"));
        //System.out.println(hyp.findSynset("event"));
    }
    @Test
    public void testSyn16List1Ans() {
        HyponymsData hyp = new HyponymsData("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");
        List<String> lst = new ArrayList<>();
        lst.add("change");
        lst.add("alteration");
        System.out.println(hyp.findSynset(lst));
        //System.out.println(hyp.findSynset("event"));
    }
    @Test
    public void testSyn16List2Ans() {
        HyponymsData hyp = new HyponymsData("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");
        List<String> lst = new ArrayList<>();
        lst.add("change");
        lst.add("occurrence");
        lst.add("transition");
        System.out.println(hyp.findSynset(lst));
        //System.out.println(hyp.findSynset("event"));
    }
    @Test
    public void testSynList1Ans() {
        HyponymsData hyp = new HyponymsData("./data/wordnet/synsets.txt", "./data/wordnet/hyponyms.txt");
        List<String> lst = new ArrayList<>();
        lst.add("female");
        lst.add("leader");
        System.out.println(hyp.findSynset(lst));
        //System.out.println(hyp.findSynset("event"));
    }
    @Test
    public void testSynList2Ans() {
        HyponymsData hyp = new HyponymsData("./data/wordnet/synsets.txt", "./data/wordnet/hyponyms.txt");
        List<String> lst = new ArrayList<>();
        lst.add("female");
        lst.add("animal");
        System.out.println(hyp.findSynset(lst));
        //System.out.println(hyp.findSynset("event"));
    }
    public static final String WORDS_FILE = "data/ngrams/top_49887_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    @Test
    public void testk() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = new ArrayList<>();
        words.add("food");
        words.add("cake");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        String actual = studentHandler.handle(nq);
        System.out.println(actual);
    }
}
