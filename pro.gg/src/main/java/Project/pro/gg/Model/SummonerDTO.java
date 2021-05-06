package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SummonerDTO {

    private String summonerName;
    private String id;
    private String accountId;
    private String puuid;
    private int profileiconId;
    private int summonerLevel;
    private Date revisionDate;

}
