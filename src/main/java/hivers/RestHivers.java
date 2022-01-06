package hivers;

import java.util.function.Consumer;

import static spark.Spark.*;

public class RestHivers implements Extension {
    public class Context{
        private Response response;

        public record Response(int status, Object message){}

        public Response response(int code, Object message) {
            this.response = new Response(code, message);
            return response;
        }

        public Response response(int code) {
            return response(code, null);
        }

        public Response getResponse() {
            return response;
        }
    }
    public enum Method {
        GET,
        POST,
        PUT,
        DELETE,
        PATCH
    }
    public RestHivers()
    {
        port(8080);
    }
    public RestHivers register(Method method, String path, Consumer<Context> consumer) {
        switch (method)
        {
            case GET ->
                    get(path, (req, res) -> {
                        var context = new Context();
                        consumer.accept(context);
                        return context.getResponse();
                    });
            case POST ->
                    post(path, (req, res) -> {
                        var context = new Context();
                        consumer.accept(context);
                        return context.getResponse();
                    });
            case PUT ->
                    put(path, (req, res) -> {
                        var context = new Context();
                        consumer.accept(context);
                        return context.getResponse();
                    });
            case DELETE ->
                    delete(path, (req, res) -> {
                        var context = new Context();
                        consumer.accept(context);
                        return context.getResponse();
                    });
            case PATCH ->
                    patch(path, (req, res) -> {
                        var context = new Context();
                        consumer.accept(context);
                        return context.getResponse();
                    });
        }
        return this;
    }

    @Override
    public void shutdown() {
        stop();
    }

    @Override
    public void start() {
    }
}
