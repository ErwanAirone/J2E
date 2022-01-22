package tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VectorService {
    public static Map<String, Tuple<Float, List<Integer>>> createVector(List<String> tokenList)
    {
        var res = new HashMap<String, Tuple<Float, List<Integer>>>();
        for (int i = 0; i < tokenList.size(); i++)
        {
            var token = tokenList.get(i);
            if (res.containsKey(token)) {
                res.get(token).getSecond().add(i);
            }
            else {
                float freq = (float) tokenList.stream().filter(t -> t.equals(token)).count() / tokenList.size();
                var list = new ArrayList<Integer>();
                list.add(i);
                var tuple = new Tuple<Float, List<Integer>>(freq, list);
                res.put(token, tuple);
            }
        }
        return res;
    }
}
