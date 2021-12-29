package com.example.project.model.dao;

import com.example.project.domain.Entity;

import java.util.UUID;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public AbstractDAO<UUID, ? extends Entity> getDAO(DaoClass daoClass) {
        switch (daoClass) {
            case USER_DAO: return UserDAO.getInstance();
            case USER2ACTIVITY_DAO: return User2ActivityDAO.getInstance();
            case ACTIVITY_DAO: return ActivityDAO.getInstance();
            case CATEGORY_DAO: return CategoryDAO.getInstance();
            case ROLE_DAO: return RoleDAO.getInstance();
            case ADD_ACTIVITY_REQUEST_DAO: return AddActivityRequestDAO.getInstance();
            default:
                throw new EnumConstantNotPresentException(DaoClass.class, daoClass.name());
        }
    }
}
