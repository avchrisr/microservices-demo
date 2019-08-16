package com.chrisr.userservice.repository;

import com.chrisr.userservice.repository.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class RepositoryBase {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryBase.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RepositoryBase(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<User> getUsers() {

        List<User> users = new ArrayList<>();

        MapSqlParameterSource params = new MapSqlParameterSource();

        try {
            users = namedParameterJdbcTemplate.query(GET_USERS_QUERY, params, USER_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean insertUser() {

        User user = new User();
        user.setId(3);
        user.setUsername("bear_claw");

        try {
            String userString = objectMapper.writeValueAsString(user);

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("user", userString);

            int status = namedParameterJdbcTemplate.update(INSERT_USER_QUERY, params);  // 1 for success

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public Long getNextSequence() {
        return namedParameterJdbcTemplate.queryForObject(GET_NEXT_SEQUENCE, new MapSqlParameterSource(), Long.class);
    }

    // ---------------------------
    // SQL QUERIES AND MAPPERS
    // ---------------------------

    private static final String GET_NEXT_SEQUENCE = "SELECT nextval('sequence_number')";

    private static final String GET_USERS_QUERY = "SELECT data FROM users";
    private static final String INSERT_USER_QUERY = "INSERT INTO users (data) VALUES (:user::jsonb)";

    private final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
        User user = null;

        try {
            user = objectMapper.readValue(rs.getString("data"), User.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return user;
    };
}
