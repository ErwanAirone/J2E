package tfidf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Exceptions {
    ;

    private static final Logger LOGGER = LoggerFactory.getLogger(Exceptions.class);

    public static <EXCEPTION_T extends Exception> void wrap(final ThrowingRunnable<EXCEPTION_T> code) {
        try {
            code.run();
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static <SUPPLY_Y, EXCEPTION_T extends Exception> SUPPLY_Y wrap(final ThrowingSupplier<SUPPLY_Y, EXCEPTION_T> code) {
        try {
            return code.get();
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static <EXCEPTION_T extends Exception> void trySilently(final ThrowingRunnable<EXCEPTION_T> code) {
        try {
            code.run();
        } catch (final Exception exception) {
            LOGGER.warn("tried and failed, ignoring: ", exception);
        }
    }

    public static <SUPPLY_Y, EXCEPTION_T extends Exception> SUPPLY_Y tryOr(final ThrowingSupplier<SUPPLY_Y, EXCEPTION_T> code, final SUPPLY_Y backup) {
        try {
            return code.get();
        } catch (final Exception exception) {
            LOGGER.warn("tried and failed, ignoring: ", exception);
            return backup;
        }
    }

    public static <SUPPLY_Y, EXCEPTION_T extends Exception> SUPPLY_Y tryOrNull(final ThrowingSupplier<SUPPLY_Y, EXCEPTION_T> code) {
        return tryOr(code, null);
    }
}
