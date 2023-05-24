package challenge18.hotdeal.common.Enum;

public enum UserRole {
    ROLE_USER(Authorization.USER),
    ROLE_ADMIN(Authorization.ADMIN);

    private final String authorization;

    private UserRole(String authorization){
        this.authorization = authorization;
    }
    public String getAuthorization(){
        return authorization;
    }

    private class Authorization{
        private static final String USER = "USER";
        private static final String ADMIN = "ADMIN";
    }
}
