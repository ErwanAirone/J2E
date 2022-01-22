package tfidf;

import org.jsoup.Jsoup;

import java.io.IOException;

public class ScrappingService {
    public static String scrap(String url)
    {
        try {
            return Jsoup.connect(url).get().body().text();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
