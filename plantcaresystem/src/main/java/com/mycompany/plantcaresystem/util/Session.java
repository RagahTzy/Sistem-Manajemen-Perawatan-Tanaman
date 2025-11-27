package com.mycompany.plantcaresystem.util;

import com.mycompany.plantcaresystem.models.User;

public class Session {

    private static User currentUser;

    public static void setCurrentUser(User u) {
        currentUser = u;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}
