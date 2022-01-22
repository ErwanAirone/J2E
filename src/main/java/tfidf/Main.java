package tfidf;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        var indexer = new IndexerService();
        var urls = List.of("https://en.wikipedia.org/", "https://en.wikipedia.org/wiki/Portal:Mathematics", "https://fr.wikipedia.org/wiki/TF-IDF");
        indexer.index(urls);
        indexer.save();
        indexer.query("german wikidata explosive");
    }
}