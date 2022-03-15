package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RankedSoloDTO {

    private String id;
    private String queueType;
    private String tier;
    private String tier_rank;
    private String summoner_name;
    private int leaguePoints;
    private int wins;
    private int losses;
    private double rate;

    private String rateRank;
}
