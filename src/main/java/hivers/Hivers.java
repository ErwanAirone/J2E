package hivers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Hivers {

    private ArrayList<ScopeInterface> scopeList = new ArrayList<>();
    private final Map<Class<? extends Extension>, Extension> extensionMap = new HashMap();

    public Hivers() {
        scopeList.add(new DefaultScope());
    }

    public Provider provider(Provider provider){
        scopeList.get(scopeList.size() - 1).addProvider(provider);
        return provider;
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

    public <T> Optional<T> instanceOf(Class<T> providerClass) {
        return Optional.ofNullable(scopeList.get(scopeList.size() - 1).getProvider(providerClass));
    }

    public void register(Extension extension)
    {
        extensionMap.put(extension.getClass(), extension);
    }
    public <T extends Extension> T extension(Class<T> extensionClass)
    {
        return (T) extensionMap.get(extensionClass);
    }
}