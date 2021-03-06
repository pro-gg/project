package Project.pro.gg.Service;

import java.util.HashMap;
import java.util.List;

import Project.pro.gg.DAO.TeamDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.RankedSoloDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;
import Project.pro.gg.Repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{


    private final TeamDAO teamRepository;

    @Override
    public TeamDTO selectTeam(TeamDTO teamDTO) {
        return teamRepository.selectTeam(teamDTO);
    }

    @Override
    public void insertTeamData(TeamDTO teamDTO, MemberDTO memberDTO) {
        teamRepository.insertTeamData(teamDTO, memberDTO);
    }

    @Override
    public List<TeamDTO> selectTeamList() {
        return teamRepository.selectTeamList();
    }

    @Override
    public TeamApplyDTO selectTeamApply_Join(String nickname) {
        return teamRepository.selectTeamApply_Join(nickname);
    }

    @Override
    public boolean tierCheck(String tier, String tier_rank, String[] tierArray) {
        // 리턴값은 가중치 비교 결과(제한 티어 가중치 보다 신청자의 티어 가중치가 높으면 true, 그렇지 않으면 false 반환)

        boolean check_result = false;
        int applierValue = teamRepository.selectTierValue(tier, tier_rank);
        int teamTierLimit = teamRepository.selectTierValue(tierArray[0], tierArray[1]);

        if (applierValue >= teamTierLimit) check_result = true;
        return check_result;
    }

    @Override
	public int tierCalculate(String tier, String tier_rank) {
		return teamRepository.selectTierValue(tier, tier_rank);
	}
    
	@Override
	public String getTier(int tier_value) {
		return teamRepository.selectTier(tier_value);
	}

	@Override
    public void insertApply(TeamApplyDTO teamApplyDTO) {
        teamRepository.insertApply(teamApplyDTO);
    }

    @Override
    public String selectLine(TeamApplyDTO teamApplyDTO) {
        return teamRepository.selectLine(teamApplyDTO);
    }

    @Override
    public List<TeamApplyDTO> selectApplyMemberList(String teamName) {
        return teamRepository.selectApplyMemberList(teamName);
    }

    @Override
    public void deleteApplyMember(TeamApplyDTO teamApplyDTO) {
        teamRepository.deleteApplyMember(teamApplyDTO);
    }

    @Override
    public void deleteTeam(TeamDTO teamDTO) {
        teamRepository.deleteTeam(teamDTO);
    }

    @Override
    public boolean selectOtherApply(String nickname) {
        boolean check_otherApply = false;

        TeamApplyDTO teamApplyDTO = teamRepository.selectApplyStatus(nickname);
        if (teamApplyDTO != null) check_otherApply = true;
        return check_otherApply;
    }

    @Override
    public TeamApplyDTO selectApplyStatus(String nickname) {
        return teamRepository.selectApplyStatus(nickname);
    }

    @Override
    public void updateTeamLine(TeamApplyDTO teamApplyDTO) {
        teamRepository.updateTeamLine(teamApplyDTO);
    }

	@Override
	public void updateTierAvg(TeamDTO teamDTO) {
		teamRepository.updateTierAvg(teamDTO);
	}

	@Override
	public List<TeamDTO> selectMatchList(HashMap<String,Integer> idx) {
		return teamRepository.selectMatchList(idx);
	}

    @Override
    public List<TeamDTO> selectDynamicSearch(TeamDTO teamDTO) {
        return teamRepository.selectDynamicSearch(teamDTO);
    }

    @Override
    public List<RankedSoloDTO> selectDynamicSearch_Crew(RankedSoloDTO rankedSoloDTO) {
        return teamRepository.selectDynamicSearch_Crew(rankedSoloDTO);
    }

    @Override
    public void updateTeam(TeamDTO teamDTO) {
        teamRepository.updateTeam(teamDTO);
    }


}
