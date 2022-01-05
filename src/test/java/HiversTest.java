import org.junit.Test;

import java.util.function.Function;

public class HiversTest {

    @Test
    public void testBasicHiversUserCase() {

        // Init.
        final var hivers = new Hivers();

        // Add providers
        hivers.provider(new Prototype<TestService>(TestService.class, PingService::new));
        hivers.provider(new Prototype<>(Nested.class, () -> new Nested(hivers.instanceOfOrThrow(TestService.class))));
        hivers.provider(new Singleton<>(Nested.class, new Nested(hivers.instanceOfOrThrow(TestService.class))));

        // Test instance resolution
        hivers.instanceOfOrThrow(TestService.class).ping();
        times(3, () -> System.out.println(hivers.instanceOfOrThrow(TestService.class)));

        // New scope and test instance resolution
        hivers.push(new DefaultScope());
        hivers.provider(new Singleton<>(TestService.class, new PongService()));
        times(3, () -> System.out.println(hivers.instanceOfOrThrow(TestService.class)));

        /*hivers.instanceOf(TestService.class).orElseThrow().ping();

        // Pop scope and test instance resolution
        hivers.pop();
        hivers.instanceOf(TestService.class).orElseThrow().ping();

        // Aspects
        hivers.push(new DefaultScope());
        /*hivers.provider(new Singleton<>(TestService.class, new PongService()))
                .withProxies(Proxy.around("ping", new LoggerAspect()), Proxy.init(() -> System.out.println("Service init.")));
        hivers.instanceOf(TestService.class).orElseThrow().ping();
        hivers.pop();

        // Extension
        hivers.push(new DefaultScope());
        hivers.register(new RestHivers());
        hivers.extension(RestHivers.class)
                .register(RestHivers.Method.GET, "/hello", context -> context.response(200, "Hello, world!"))
                .register(RestHivers.Method.DELETE, "/", context -> {
                    hivers.extension(RestHivers.class).shutdown();
                    context.response(204); })
                .start();*/
    }


    private void times(int i, Runnable o) {
        while (i > 0) {
            o.run();
            i--;
        }
    }

    public interface TestService {
        void ping();
    }

    public static class PingService implements TestService {
        @Override
        public void ping() { System.out.println("ping"); }
    }

    public static class PongService implements TestService {
        @Override
        public void ping() { System.out.println("pong"); }
    }

    public static class Nested {
        private final TestService testService;

        public Nested(final TestService testService) {
            this.testService = testService;
        }
    }

}