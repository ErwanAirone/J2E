public interface Provider<T> {
    <VALUE_T extends T> VALUE_T instantiate();
    Class<T> providerClass();
    Provider<T> withProxies(final ProxyDefinition...proxies);
}
