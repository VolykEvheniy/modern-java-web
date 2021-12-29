package com.example.project.dao;


import com.example.project.model.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoUnitTests {

    private UserDAO userDAO;

    @BeforeEach
    private void beforeEach() {
        userDAO = mock(UserDAO.class);
    }


}
