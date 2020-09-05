package vichshop.modeles;

import vichshop.model.User;

public final class UserSession {

    private static UserSession instance;

    private User user;

    private UserSession(User user) {
        this.user = user;
    }

    public static UserSession getInstace() {
        return instance;
    }

    public static void setSession(User user)
    {
        instance = new UserSession(user);
    }

    public User getUser() {
        return user;
    }

    public void cleanUserSession() {
        user = null;// or null
    }

    @Override
    public String toString() {
        return user.getNomComplet();
    }
}