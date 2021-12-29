package com.example.project.controller.util;

import com.example.project.controller.exception.NotAllowedException;
import com.example.project.controller.exception.UserNotFoundException;
import com.example.project.domain.enums.RoleEnum;
import com.example.project.domain.User;

public class ValidationUtils {
    public static boolean isLoginCorrect(String login) {
        return login != null && login.length() >= 3;
    }

    public static boolean isPasswordCorrect(String password) {
        return password != null && password.length() >= 4;
    }

    public static void verifyUserNotNull(User user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

    public static boolean areUserHasRole(User user, RoleEnum role) {
        String roleName = user.getRole().getName();
        return roleName.equals(role.toString());
    }

    public static void verifyUserHasRole(User user, RoleEnum role) {
        if (!areUserHasRole(user, role)) {
            throw new NotAllowedException();
        }
    }
}
