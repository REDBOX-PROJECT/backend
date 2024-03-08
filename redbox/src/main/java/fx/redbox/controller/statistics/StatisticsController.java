package fx.redbox.controller.statistics;

import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.api.StatisticsResponseMessage;
import fx.redbox.controller.statistics.form.StatisticsForm;
import fx.redbox.service.statistics.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@Tag(name = "STATISTICS API", description = "통계 관리 API")
@RestController
@AllArgsConstructor
public class StatisticsController {

    StatisticsService statisticsService;

    @GetMapping("/statistics")
    @Operation(summary = "헌혈 통계",
            description = "헌혈종류, 성별, 나이대 통계를 나타냅니다."
    )
    @ApiResponse(responseCode = "200", description = "통계 성공")
    @ApiResponse(responseCode = "400", description = "통계값을 불러올 수 없습니다.")
    public ResponseApi showStatistics() throws SQLException {
        StatisticsForm statisticsForm = statisticsService.showStatistics();
        return ResponseApi.success(StatisticsResponseMessage.STATISTICS_SUCCESS.getMessage(), statisticsForm);
    }

}
