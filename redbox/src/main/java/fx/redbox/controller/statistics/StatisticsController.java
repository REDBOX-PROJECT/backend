package fx.redbox.controller.statistics;

import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.statistics.form.StatisticsForm;
import fx.redbox.service.statistics.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class StatisticsController {

    StatisticsService statisticsService;

    @GetMapping("/statistics")
    public ResponseApi<StatisticsForm> showStatistics() throws SQLException {
        StatisticsForm statisticsForm = statisticsService.showStatistics();
        return ResponseApi.success("성공", statisticsForm);
    }

}
