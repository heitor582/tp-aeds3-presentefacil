package repository;

public final class GlobalMemory {
    private static int userId = -1;
    public static void setUserId(int userId) {
        GlobalMemory.userId = userId;
    }
    public static int getUserId() {
        return userId;
    }
    public static void logout() {
        setUserId(-1);
    }
    public static boolean isLogout(){
        return userId == -1;
    }
}
