package com.example.project.controller.command;

import com.example.project.controller.command.impl.OpenIndexCommand;
import com.example.project.controller.command.impl.OpenLoginCommand;
import com.example.project.controller.command.impl.OpenRegisterCommand;
import com.example.project.controller.command.impl.activitiesManaging.*;
import com.example.project.controller.command.impl.auth.LoginCommand;
import com.example.project.controller.command.impl.auth.LogoutCommand;
import com.example.project.controller.command.impl.auth.RegisterCommand;
import com.example.project.controller.command.impl.categoriesManaging.CreateCategoryCommand;
import com.example.project.controller.command.impl.categoriesManaging.DeleteCategoryCommand;
import com.example.project.controller.command.impl.categoriesManaging.ShowAllCategoriesCommand;
import com.example.project.controller.command.impl.usersManaging.*;

public enum CommandList {
    OPEN_INDEX(new OpenIndexCommand()),
    OPEN_REGISTER(new OpenRegisterCommand()),
    OPEN_LOGIN(new OpenLoginCommand()),
    REGISTER_USER(new RegisterCommand()),
    LOGIN_USER(new LoginCommand()),
    LOGOUT_USER(new LogoutCommand()),
    SHOW_ALL_USERS(new OpenUsersPageCommand()),
    DELETE_USER(new DeleteUserCommand()),
    RESTORE_USER(new RestoreUserCommand()),
    SHOW_ALL_CATEGORIES(new ShowAllCategoriesCommand()),
    CREATE_CATEGORY(new CreateCategoryCommand()),
    DELETE_CATEGORY(new DeleteCategoryCommand()),
    SHOW_ACTIVITIES(new ShowActivitiesCommand()),
    INSERT_ACTIVITY(new CreateActivityCommand()),
    DELETE_ACTIVITY(new DeleteActivityCommand()),
    SHOW_ACTIVITY_MANAGEMENT(new ShowActivityManagementCommand()),
    PIN_ACTIVITY(new PinActivityCommand()),
    UNPIN_ACTIVITY(new UnpinActivityCommand()),
    SHOW_REPORT(new OpenUsersReportCommand()),
    SHOW_ALL_USER_ACTIVITIES(new OpenUserActivitiesCommand()),
    SET_TIME_SPENT(new SetTimeSpentCommand());

    private Command command;

    CommandList(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
