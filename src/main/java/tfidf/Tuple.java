package tfidf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tuple<FIRST_T, SECOND_T> {
    private FIRST_T first;
    private SECOND_T second;
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        stringBuilder.append(first.toString());
        stringBuilder.append(", ");
        stringBuilder.append(second.toString());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}
