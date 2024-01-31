package fx.redbox.repository.user;


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

import java.sql.Date;
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
        userInfoParam.put("permission", userInfo.getPermission().name()); //DEFAULT USER
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

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users" +
                " JOIN user_accounts ON users.account_id = user_accounts.account_id" +
                " JOIN user_info ON users.user_info_id = user_info.user_info_id" +
                " WHERE user_accounts.email = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{email}, userMapper));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users" +
                " JOIN user_accounts ON users.account_id = user_accounts.account_id" +
                " JOIN user_info ON users.user_info_id = user_info.user_info_id";
        return jdbcTemplate.query(sql, userMapper);
    }

    @Override
    public void update(Long userId, Date birth, String phone, String address) { //생일, 전화번호, 주소 변경
        String userSql = "UPDATE users SET birth = ? WHERE user_id = ?";
        String userInfoSql = "UPDATE user_info SET phone = ?, address = ? WHERE user_info_id = ?";
        jdbcTemplate.update(userSql, birth, userId);
        jdbcTemplate.update(userInfoSql, phone, address, userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        String userAccountSql = "DELETE FROM user_accounts WHERE account_id = ?";
        String userInfoSql = "DELETE FROM user_info WHERE user_info_id = ?";
        String userSql = "DELETE FROM users WHERE user_id = ?";

        jdbcTemplate.update(userSql, userId);
        jdbcTemplate.update(userAccountSql, userId);
        jdbcTemplate.update(userInfoSql, userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM user_accounts WHERE email = ?";
        int cnt = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return cnt > 0;
    }

    @Override
    public Optional<User> findEmail(String name, String phone) {
        String sql = "SELECT *" +
                " FROM users" +
                " JOIN user_info ON users.user_info_id = user_info.user_info_id" +
                " JOIN user_accounts ON users.account_id = user_accounts.account_id" +
                " WHERE users.name = ? AND user_info.phone = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{name, phone}, userMapper));
    }

    @Override
    public boolean existsByNameAndEmail(String name, String email) {
        String sql ="SELECT COUNT(*) FROM users" +
                " JOIN user_accounts ON users.account_id = user_accounts.account_id" +
                " WHERE users.name = ? AND user_accounts.email = ?";
        int cnt = jdbcTemplate.queryForObject(sql, Integer.class, name, email);
        return cnt > 0;
    }

    public void insertPassword(String email, String password) {
        String sql = "UPDATE user_accounts" +
                " SET password = ?" +
                " WHERE email = ?";
        jdbcTemplate.update(sql, password, email);
    }

}
