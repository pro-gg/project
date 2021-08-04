package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.configurationprocessor.json.JSONArray;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO{

    private String userid;
    private String passwd;
    private String name;
    private String email;
    private String nickname;

    private String summoner_name;
    private String teamName;

    private String tier;
    private double rate;

    private String recommendpost;
    private String not_recommendpost;

    private String recommendreply;
    private String not_recommendreply;
}
