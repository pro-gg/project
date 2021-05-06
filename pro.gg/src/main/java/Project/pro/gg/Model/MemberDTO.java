package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {

    private String id;
    private String passwd;
    private String name;
    private String email;
    private String nickname;
    private SummonerDTO summonerDTO;
}
