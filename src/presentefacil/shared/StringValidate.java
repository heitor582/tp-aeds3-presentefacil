package shared;

public final class StringValidate {
    public static String requireNonBlank(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("cannot be null or blank");
        }
        return value;
    }
    public static boolean isNotBlank(final String value) {
        return !(value == null || value.isBlank());
    }
    public static boolean isBlank(final String value) {
        return (value == null || value.isBlank());
    }
    public static String requireMinSize(final String value, final int minSize) {
        if (value == null || value.isBlank() || value.length() < minSize) {
            throw new IllegalArgumentException("cannot be null or blank or dont have the minimun size");
        }
        return value;
    }
}
