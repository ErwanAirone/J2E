package tfidf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;

/**
 * Interface / toolbox for json serialization/parsing.
 */
public interface Json {

    Logger LOGGER = LoggerFactory.getLogger(Json.class);

    /**
     * Singular ObjectMapper instance.
     */
    ObjectMapper OBJECT_MAPPER = initMapper(new ObjectMapper());

    /**
     * Static helper for parsing of any object from json.
     *
     * @param expectedClass
     *         The expected resulting class.
     * @param json
     *         The json to parse.
     * @param <EXPECTED_T>
     *         The type of class.
     *
     * @return The parsed object.
     */
    static <EXPECTED_T> @NotNull EXPECTED_T deserialize(@NotNull final String json,
                                                        @NotNull final Class<EXPECTED_T> expectedClass) {
        // FIXME: 29/07/2020 nullity check

        return Exceptions.wrap(() -> OBJECT_MAPPER.readValue(json, expectedClass));
    }

    /**
     * Static helper for parsing of any object from json.
     *
     * @param expectedClass
     *         The expected resulting class.
     * @param json
     *         The json to parse.
     * @param <EXPECTED_T>
     *         The type of class.
     *
     * @return The parsed object.
     */
    static <EXPECTED_T> @NotNull EXPECTED_T deserialize(@NotNull final String json,
                                                        @NotNull final JavaType expectedClass) {
        // FIXME: 29/07/2020 nullity check

        return Exceptions.wrap(() -> OBJECT_MAPPER.readValue(json, expectedClass));
    }

    /**
     * Static helper for parsing of any object from json tree.
     *
     * @param expectedClass
     *         The expected resulting class.
     * @param json
     *         The json tree to parse.
     * @param <EXPECTED_T>
     *         The type of class.
     *
     * @return The parsed object.
     */
    static <EXPECTED_T> @NotNull EXPECTED_T deserialize(@NotNull final JsonNode json,
                                                        @NotNull final Class<EXPECTED_T> expectedClass) {
        // FIXME: 29/07/2020 nullity check

        return Exceptions.wrap(() -> OBJECT_MAPPER.treeToValue(json, expectedClass));
    }

    /**
     * Initialization block for Object mappers. Make sure to use this for all the global mappers in the system.
     *
     * @param objectMapper
     *         The mapper to setup.
     *
     * @return The updated mapper.
     */
    static ObjectMapper initMapper(final ObjectMapper objectMapper) {
        objectMapper.setDateFormat(new StdDateFormat());
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return objectMapper;
    }

    /**
     * Reads a json string into a json tree.
     *
     * @param input
     *         The string to parse.
     *
     * @return The parsed tree.
     */
    static JsonNode readTree(final String input) {
        return Exceptions.wrap(() -> OBJECT_MAPPER.readTree(input));
    }

    /**
     * Static helper for json serialization of any object.
     *
     * @param source
     *         The object to serialize.
     *
     * @return The json representation, as a String.
     */
    static @NotNull String serialize(@NotNull final Object source) {
        return Exceptions.wrap(() -> OBJECT_MAPPER.writeValueAsString(source));
    }

    /**
     * Static helper for json serialization of any object.
     *
     * @param source
     *         The object to serialize.
     *
     * @return The json representation, as a String.
     */
    static @NotNull String serializePretty(@NotNull final Object source) {
        return Exceptions.wrap(() -> OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(source));
    }

    /**
     * Serialize `this` to json.
     *
     * @return Json representation of this object.
     */
    default @NotNull String serialize() {
        return Json.serialize(this);
    }

    /**
     * Serialize `this` to json.
     *
     * @return Json representation of this object.
     */
    default @NotNull String serializePretty() {
        return Json.serializePretty(this);
    }
}
