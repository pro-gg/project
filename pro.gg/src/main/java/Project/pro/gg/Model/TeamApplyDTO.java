package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamApplyDTO {

    private String nickname;
    private String summoner_name;
    private String line;
    private String tier;
    private String tier_rank;
    private double rate;
    private String teamName;
}
