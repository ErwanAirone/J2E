package hivers;

import java.lang.reflect.Method;

public record ProxyDefinition(Runnable onInit,
                              String methodName,
                              AroundAspect aspect){


    public static ProxyDefinition around(final String methodName, final AroundAspect aspect) {
        return new ProxyDefinition(() -> {}, methodName, aspect);
    }
    public static ProxyDefinition init(final Runnable onInit) {
        return new ProxyDefinition(onInit, null, null);
    }
    public record Context(Object proxy, Method method, Object[] args)
    {

    }
    public interface AroundAspect {
        Object invokeProxy(final Context context) throws Throwable;
    }
}
