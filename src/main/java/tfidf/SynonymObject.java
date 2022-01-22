package tfidf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SynonymObject {
    private String word;
    private String key;
    private String pos;
    private List<String> synonyms;
}
