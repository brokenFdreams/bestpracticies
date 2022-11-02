package com.brokenfdreams.skeleton.tests.dao.impl;

import com.brokenfdreams.skeleton.tests.dto.UpdateUserDTO;
import com.brokenfdreams.skeleton.tests.dto.UserDTO;
import com.brokenfdreams.skeleton.tests.dao.UserDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String SELECT_USERS = "SELECT\n" +
            "ID AS USER_ID,\n" +
            "LOGIN AS LOGIN,\n" +
            "FIRST_NAME AS FIRST_NAME,\n" +
            "LAST_NAME AS LAST_NAME,\n" +
            "AGE AS AGE\n" +
            "FROM USERS\n";
    private static final String SELECT_USER_BY_ID = SELECT_USERS + "WHERE ID = ?\n";
    private static final String CREATE_USER_AND_RETURN = "INSERT INTO USERS \n" +
            "(ID, LOGIN, FIRST_NAME, LAST_NAME, AGE)\n" +
            "VALUES (nextval('users_id_sequence'), ?, ?, ?, ?)\n";
    private static final String UPDATE_USER_BY_ID = "UPDATE USERS SET\n" +
            "LOGIN = ?, FIRST_NAME = ?, LAST_NAME = ?, AGE = ?\n" +
            "WHERE ID = ?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM USERS WHERE ID = ?";
    private static final String SELECT_USER_BY_LOGIN = SELECT_USERS + "WHERE LOGIN = ?\n";

    @NonNull
    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(@NonNull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    @Override
    public List<UserDTO> findAll() {
        return jdbcTemplate.query(SELECT_USERS, userDTOMapper());
    }

    @NonNull
    @Override
    public UserDTO getUserById(long userId) {
        return Objects.requireNonNull(jdbcTemplate.queryForObject(SELECT_USER_BY_ID, userDTOMapper(), userId));
    }

    @NonNull
    @Override
    public UserDTO createUserAndReturn(@NonNull UpdateUserDTO updateUserDTO) {
        jdbcTemplate.update(CREATE_USER_AND_RETURN,
                updateUserDTO.getLogin(),
                updateUserDTO.getFirstName(),
                updateUserDTO.getLastName(),
                updateUserDTO.getAge());
        return Objects.requireNonNull(jdbcTemplate.queryForObject(SELECT_USER_BY_LOGIN,
                userDTOMapper(),
                updateUserDTO.getLogin()));
    }

    @Override
    public boolean updateUser(long userId, @NonNull UpdateUserDTO updateUserDTO) {
        return jdbcTemplate.update(UPDATE_USER_BY_ID,
                updateUserDTO.getLogin(),
                updateUserDTO.getFirstName(),
                updateUserDTO.getLastName(),
                updateUserDTO.getAge(),
                userId) == 1;
    }

    @Override
    public boolean deleteUser(long userId) {
        return jdbcTemplate.update(DELETE_USER_BY_ID, userId) == 1;
    }

    @NonNull
    private RowMapper<UserDTO> userDTOMapper() {
        return (rs, rowNum) -> UserDTO.builder()
                .id(rs.getLong("USER_ID"))
                .login(rs.getString("LOGIN"))
                .firstName(rs.getString("FIRST_NAME"))
                .lastName(rs.getString("LAST_NAME"))
                .age(rs.getInt("AGE"))
                .build();
    }
}
