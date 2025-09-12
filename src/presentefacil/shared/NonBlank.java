package shared;

public class NonBlank {
    public static String require(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("cannot be null or blank");
        }
        return value;
    }
    public static boolean isValid(final String value) {
        return !(value == null || value.isBlank());
    }
    public static boolean isNotValid(final String value) {
        return (value == null || value.isBlank());
    }
}
