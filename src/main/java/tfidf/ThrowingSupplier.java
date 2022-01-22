package tfidf;

public interface ThrowingSupplier<SUPPLY_Y, EXCEPTION_T extends Exception>  {
    public SUPPLY_Y get() throws EXCEPTION_T;
}
