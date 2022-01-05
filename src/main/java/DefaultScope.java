import java.util.ArrayList;

public class DefaultScope implements ScopeInterface {

    private ArrayList<Provider> providers = new ArrayList<>();

    public void addProvider(Provider provider) {
        providers.add(provider);
    }

    public <T> T getProvider(Class<T> providerClass) {
        for (Provider provider : providers) {
            if (provider.ProviderClass() == providerClass) {
                var providerInstance = provider.Instantiate();
                return (T) providerInstance;
            }
        }
        return null;
    }


}
