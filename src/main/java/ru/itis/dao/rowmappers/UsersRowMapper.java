package ru.itis.dao.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.models.Role;
import ru.itis.models.User;
import ru.itis.models.UserState;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UsersRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        do {
            String email = rs.getString("email");
            UserState userState = UserState.valueOf(rs.getString("state"));
            Long id = rs.getLong("id");
            String password = rs.getString("password");
            String confirmLink = rs.getString("confirm_link");
            String avaPath = rs.getString("ava_path");
            Integer roleId = rs.getInt("roleId");
            String rolName = rs.getString("roleName");
            Integer points = rs.getInt("points");

            user.setId(id);
            user.setEmail(email);
            user.setPassword(password);
            user.setAvaPath(avaPath);
            user.setConfirmLink(confirmLink);
            user.setUserState(userState);
            user.setPoints(points);
            user.getRoles().add(new Role(roleId, rolName));
        }
        while (rs.next());
        return user;
    }
}
