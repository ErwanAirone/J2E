public interface ScopeInterface {
    public void addProvider(Provider provider);
    public <T> T getProvider(Class<T> providerClass);
}
