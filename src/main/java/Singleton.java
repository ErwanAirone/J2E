import java.util.function.Supplier;

public final class Singleton<T> implements Provider {

    private T instance;

    private Supplier<T> supplier;
    private Class<T> serviceClass;

    @Override
    public T Instantiate(){
        if (instance == null) {
            instance = supplier.get();
        }
        return instance;
    }

    @Override
    public Class<T> ProviderClass(){
        return serviceClass;
    }

    public Singleton(Class<T> serviceClass, Supplier<T> supplier) {
        this.serviceClass = serviceClass;
        this.supplier = supplier;
    }

    public Singleton(Class<T> serviceClass, T instance) {
        this.serviceClass = serviceClass;
        this.instance = instance;
    }
}
