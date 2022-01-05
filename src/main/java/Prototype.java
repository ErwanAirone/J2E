import java.util.function.Supplier;

public class Prototype<T> implements Provider{

    private Supplier<T> supplier;
    private Class<T> serviceClass;

    @Override
    public T Instantiate(){
        return supplier.get();
    }

    @Override
    public Class<T> ProviderClass(){
        return serviceClass;
    }

    public Prototype(Class<T> serviceClass, Supplier<T> supplier) {
        this.serviceClass = serviceClass;
        this.supplier = supplier;
    }
}
