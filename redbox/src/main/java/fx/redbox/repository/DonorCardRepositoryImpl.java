package fx.redbox.repository;

import fx.redbox.entity.donorCards.DonorCard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DonorCardRepositoryImpl implements DonorCardRepository{
    private JdbcTemplate jdbcTemplate;

}
