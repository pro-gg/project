package Project.pro.gg.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummonerDTO {

    private String summoner_name;
    private String id;
    private String accountId;
    private String puuid;
    private int profileiconId;
    private long summonerLevel;
    private long revisionDate;

    private String userid;

}
