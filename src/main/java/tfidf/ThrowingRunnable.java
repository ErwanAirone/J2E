package tfidf;

public interface ThrowingRunnable<EXCEPTION_T extends Exception> {
    public void run() throws EXCEPTION_T;
}
