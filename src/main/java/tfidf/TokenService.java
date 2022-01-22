package tfidf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
public class TokenService {
    public static List<String> getToken(String document)
    {
        return Arrays.stream(document.replaceAll("[^A-Za-z0-9]+", " ").toLowerCase(Locale.ROOT)
                            .split(" ")).collect(Collectors.toList());
    }
    public List<String> suppressStopWord(List<String> slice)
    {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            InputStreamReader inputStream =
                    new InputStreamReader(classloader.getResourceAsStream("tf-idf/englishStopWords.json"),
                                            StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(inputStream);
            StopWord stopWord = Json.deserialize(br.readLine(), StopWord.class);
            slice.removeAll(stopWord.getStopWords());
            return slice;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> stemmingToken(List<String> tokenList)
    {
        for (int i = 0; i < tokenList.size(); i++)
        {
            var stemToken = tokenList.get(i).replaceFirst("(ies)$", "y");
            stemToken = stemToken.replaceFirst("(ed|s|ing)$", "");
            tokenList.set(i, stemToken);
        }
        return tokenList;
    }
    public List<String> synonym(List<String> tokenList)
    {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            InputStreamReader inputStream =
                    new InputStreamReader(classloader.getResourceAsStream("tf-idf/englishSynonymList.jsonl"),
                            StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(inputStream);
            List<SynonymObject> synonymObjectList = new ArrayList<>();
            var line = br.readLine();
            while (line != null)
            {
                SynonymObject synonymObject = Json.deserialize(line, SynonymObject.class);
                synonymObjectList.add(synonymObject);
                line = br.readLine();
            }
            br.close();
            for (int i = 0; i < tokenList.size(); i++)
            {
                var token = tokenList.get(i);
                for (var synonymObject: synonymObjectList)
                {
                    if (synonymObject.getSynonyms().contains(token))
                    {
                        token = synonymObject.getWord();
                        break;
                    }
                }
                tokenList.set(i, token);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tokenList;
    }
}
