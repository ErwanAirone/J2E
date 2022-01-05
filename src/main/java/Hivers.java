import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

public class Hivers {

    private ArrayList<ScopeInterface> scopeList = new ArrayList<>();

    public Hivers() {
        scopeList.add(new DefaultScope());
    }

    public void provider(Provider provider){
        scopeList.get(scopeList.size() - 1).addProvider(provider);
    }

    public <T> T instanceOfOrThrow(Class<T> providerClass) {
        var res = scopeList.get(scopeList.size() - 1).getProvider(providerClass);
        if (res != null) {
            return res;
        }
        else {
            throw new RuntimeException("No provider class found in scope");
        }
    }

    public void push(ScopeInterface scope) {
        scopeList.add(scope);
    }

    public void pop() {
        scopeList.remove(scopeList.size() - 1);
    }

    public <T> T instanceOf(Class<T> providerClass) {
        return scopeList.get(scopeList.size() - 1).getProvider(providerClass);
    }
}