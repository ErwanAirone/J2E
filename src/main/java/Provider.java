public interface Provider<T> {
    public T Instantiate();
    public Class<T> ProviderClass();
}
