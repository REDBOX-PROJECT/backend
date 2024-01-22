package fx.redbox.repository.User;


import fx.redbox.entity.enums.Grade;
import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User save(UserAccount userAccount, UserInfo userInfo, User user) {

        // user_accounts 테이블
        SimpleJdbcInsert userAccountJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_accounts")
                .usingGeneratedKeyColumns("account_id");
        Map<String, Object> userAccountParam = new ConcurrentHashMap<>();
        userAccountParam.put("email", userAccount.getEmail());
        userAccountParam.put("password", userAccount.getPassword());
//        user.setAccountId(userAccountJdbcInsert.executeAndReturnKey(userAccountParam).longValue());


        // user_info 테이블
        SimpleJdbcInsert userInfoJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_info")
                .usingGeneratedKeyColumns("user_info_id");
        Map<String, Object> userInfoParam = new ConcurrentHashMap<>();
        userInfoParam.put("phone", userInfo.getPhone());
        userInfoParam.put("address", userInfo.getAddress());
        userInfoParam.put("donation_count", 0); //DEFAULT 0
        userInfoParam.put("permission", Permission.USER.name()); //DEFAULT USER
//        user.setUserInfoId(userInfoJdbcInsert.executeAndReturnKey(userInfoParam).longValue());


        // users 테이블
        SimpleJdbcInsert userJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        Map<String, Object> userParam = new ConcurrentHashMap<>();
        userParam.put("name", user.getName());
        userParam.put("birth", user.getBirth());
        userParam.put("gender", user.getGender().name());
        userParam.put("blood_type", user.getBloodType().name());
        userParam.put("grade", Grade.BASIC.name());
        userParam.put("account_id", userAccountJdbcInsert.executeAndReturnKey(userAccountParam).longValue());
        userParam.put("user_info_id", userInfoJdbcInsert.executeAndReturnKey(userInfoParam).longValue());
        userJdbcInsert.executeAndReturnKey(userParam).longValue();

        return user;
    }

}
