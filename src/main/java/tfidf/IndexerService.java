package tfidf;

import lombok.Getter;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Getter
public class IndexerService {
    private Set<String> urls = new HashSet<>();
    private IndexObject index = new IndexObject();
    public IndexerService()
    {
        load();
    }

    public void addToIndexer(String url, Map<String, Tuple<Float, List<Integer>>> vector)
    {
        for (var key: vector.keySet())
        {
            var list = index.getIndex().containsKey(key) ? index.getIndex().get(key) : new ArrayList<Tuple<String, Float>>();
            var tuple = new Tuple<String, Float>(url, vector.get(key).getFirst());
            list.add(tuple);
            index.getIndex().put(key, list);
        }
        urls.add(url);
    }
    public void load()
    {
        try
        {
            ClassLoader classloader = this.getClass().getClassLoader();
            InputStreamReader inputStream =
                    new InputStreamReader(classloader.getResourceAsStream("urls.json"),
                            StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(inputStream);
            var content = br.readLine();
            if (content != null)
                urls = Json.deserialize(content, Set.class);
            br.close();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    classloader.getResourceAsStream("index.json"));
            br = new BufferedReader(inputStreamReader);
            content = br.readLine();
            if (content != null)
                index = Json.deserialize(content, IndexObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveFile(String resourceName, String content) throws Exception
    {
        URL url = this.getClass().getClassLoader().getResource(resourceName);
        if (url != null) {
            File f = new File(url.getFile());
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }
    public void save() {
        try {
            saveFile("urls.json", Json.serialize(urls));
            saveFile("index.json", Json.serialize(index));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void index(List<String> urls) {
        TokenService tokenService = new TokenService();
        for (var url: urls) {
            if (!this.urls.isEmpty() && this.urls.contains(url))
                continue;
            var document = ScrappingService.scrap(url);
            var slice = tokenService.getToken(document);
            var suppress = tokenService.suppressStopWord(slice);
            var stem = TokenService.stemmingToken(suppress);
            var synonym = tokenService.synonym(stem);
            var vector = VectorService.createVector(synonym);
            addToIndexer(url, vector);
        }
    }
    public List<String> query(String userInput) {
        TokenService tokenService = new TokenService();
        var slice = tokenService.getToken(userInput);
        var suppress = tokenService.suppressStopWord(slice);
        var stem = TokenService.stemmingToken(suppress);
        var synonym = tokenService.synonym(stem);
        var vector  = VectorService.createVector(synonym);
        var sites = new ArrayList<List<Tuple<String, Float>>>();
        var res = new ArrayList<>();
        vector.keySet().forEach((word) -> {
            if (index.getIndex().containsKey(word)) {
                sites.add(index.getIndex().get(word));
                var document = new HashSet<String>();
                index.getIndex().get(word).forEach((tuple) -> document.add(tuple.getFirst()));
                index.getIndex().get(word).forEach((tuple) ->
                {
                    res.add(tuple.getSecond() * idf((float) urls.size(), (float) document.size()));
                });
            }
        });
        System.out.println(res);
        return new ArrayList<>();
    }
    public float idf(float corpusSize, float numberOfMatchingTerm) {
        return (float) Math.log(corpusSize / (1f + numberOfMatchingTerm));
    }
}
