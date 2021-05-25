package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {

    private String teamName;
    private String week_input;
    private String startTime;
    private String endTime;
    private String tier_limit;
    private String team_description;

    private String captinName;
    private String top;
    private String middle;
    private String jungle;
    private String bottom;
    private String suppoter;

    private String tier_average;
    private double rate_average;
}
