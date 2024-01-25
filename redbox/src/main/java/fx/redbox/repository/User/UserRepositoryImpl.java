package fx.redbox.repository.User;


import fx.redbox.entity.enums.Grade;
import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.repository.mappper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

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

    @Override
    public Optional<User> findByUserId(Long userId) {
        String sql = "SELECT * FROM users" +
                    " JOIN user_accounts ON users.account_id = user_accounts.account_id" +
                    " JOIN user_info ON users.user_info_id = user_info.user_info_id" +
                    " WHERE users.user_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{userId}, userMapper));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users" +
                " JOIN user_accounts ON users.account_id = user_accounts.account_id" +
                " JOIN user_info ON users.user_info_id = user_info.user_info_id";
        return jdbcTemplate.query(sql, userMapper);
    }

    @Override
    public Long update(Long userId, User updateUser) {
        // users 테이블에 대한 데이터는 변경할 수 없다, (이름, 생일, 성별, ....)
        String userAccountsSql = "UPDATE user_accounts SET password = ? WHERE account_id = ?";
        String userInfoSql = "UPDATE user_info SET phone = ?, address = ? WHERE user_info_id = ?";

        jdbcTemplate.update(userAccountsSql,
                updateUser.getUserAccount().getPassword(),
                updateUser.getUserId());
        jdbcTemplate.update(userInfoSql,
                updateUser.getUserInfo().getPhone(),
                updateUser.getUserInfo().getAddress(),
                updateUser.getUserId());
        return updateUser.getUserId();
    }
}
