import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public final class Singleton<PROVIDED_T, VALUE_T extends PROVIDED_T> extends AbstractProvider<PROVIDED_T> {

    private PROVIDED_T instance;
    private final Supplier<VALUE_T> supplier;

    public Singleton(Class<PROVIDED_T> boundClass, Supplier<VALUE_T> supplier) {
        super(boundClass);
        this.supplier = supplier;
    }

    public Singleton(Class<PROVIDED_T> boundClass, VALUE_T instance) {
        this(boundClass, () -> instance);
    }

    @Override
    public PROVIDED_T instantiate(){
        if (instance == null) {
            instance = getProxy(supplier.get());
        }
        return instance;
    }

    @Override
    public Class<PROVIDED_T> providerClass(){
        return boundClass;
    }
}
