package com.example.project.controller.util.constants;

public enum ViewJsp {
    INDEX_JSP("/index.jsp"),
    LOGIN_JSP("/login.jsp"),
    REGISTER_JSP("/register.jsp"),
    USERS_ADMIN_JSP("/WEB-INF/views/admin_users.jsp"),
    CATEGORIES_ADMIN_JSP("/WEB-INF/views/admin_categories.jsp"),
    ACTIVITIES_ADMIN_JSP("/WEB-INF/views/admin_activities.jsp"),
    ACTIVITY_MANAGEMENT_ADMIN_JSP("/WEB-INF/views/admin_activity_management.jsp"),
    REPORT_ADMIN_JSP("/WEB-INF/views/admin_report.jsp"),
    USER_HOME_JSP("/WEB-INF/views/user_home.jsp"),
    ERROR_JSP("/error.jsp");

    private String path;

    ViewJsp(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
