package roizot.com.outerspacemanager.outerspacemanager;

/**
 * Created by roizotf on 06/03/2017.
 */

public final class User {
    private String username;
    private String token;
    private String expires;

    public User(String username, String token, String expires) {
        this.username = username;
        this.token = token;
        this.expires = expires;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
    public String getExpires() {
        return expires;
    }
}
