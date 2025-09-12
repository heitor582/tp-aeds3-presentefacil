package shared;

public class NonBlank {
    public static String require(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("cannot be null or blank");
        }
        return value;
    }
}
