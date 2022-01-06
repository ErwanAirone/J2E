import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Prototype<PROVIDED_T, VALUE_T extends PROVIDED_T> extends AbstractProvider<PROVIDED_T> {

    private Supplier<VALUE_T> supplier;

    public Prototype(Class<PROVIDED_T> boundClass, Supplier<VALUE_T> supplier) {
        super(boundClass);
        this.supplier = supplier;
    }

    @Override
    public PROVIDED_T instantiate() {
        var value = supplier.get();
        return getProxy(value);
    }

    @Override
    public Class<PROVIDED_T> providerClass(){
        return boundClass;
    }
}
