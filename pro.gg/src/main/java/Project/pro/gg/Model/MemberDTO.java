package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {

    private String userid;
    private String passwd;
    private String name;
    private String email;
    private String nickname;

    private String summoner_name;
    private String teamName;

    private String tier;
    private double rate;
}
