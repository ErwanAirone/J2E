package hivers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.Proxy;

public abstract class AbstractProvider<PROVIDED_T> implements Provider<PROVIDED_T> {
    
    protected final Class<PROVIDED_T> boundClass;
    protected final List<ProxyDefinition> proxyDefinitions = new ArrayList<>();

    public AbstractProvider(Class<PROVIDED_T> boundClass) {
        this.boundClass = boundClass;
    }

    protected PROVIDED_T getProxy(final PROVIDED_T value) {
        Object localValue = value;
        if (!proxyDefinitions.isEmpty() && !boundClass.isInterface()) {
            throw new RuntimeException("Cannot bind aspects on non-interface target class.");
        }

        var reverseAroundProxies = proxyDefinitions.stream().filter(def -> def.aspect() != null).toList();
        Collections.reverse(reverseAroundProxies);

        var classLoader = boundClass.getClassLoader();

        for (var definition : reverseAroundProxies) {
            localValue = Proxy.newProxyInstance(classLoader, new Class[]{boundClass}, new AspectInvocationHandler(localValue, definition));
        }

        proxyDefinitions.stream()
                .filter(def -> def.aspect() == null)
                .map(ProxyDefinition::onInit)
                .forEach(Runnable::run);

        return (PROVIDED_T) localValue;
    }

    @Override
    public Provider<PROVIDED_T> withProxies(final ProxyDefinition... definitions) {
        proxyDefinitions.addAll(Arrays.asList(definitions));
        return this;
    }
}
