package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MatchDataDTO {

    private String matchId;
    private boolean win;
    private int championId;
    private String championName;
    private int goldEarned;
    private int goldSpent;
    private JSONObject json_itemList;
    private int kills;
    private int assists;
    private int deaths;

    private JSONObject json_spellList;
}
