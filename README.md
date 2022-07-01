# LOL 매칭 및 커뮤니티 프로젝트
Riot 개발사 Leage of Legends 게임의 플레이어간 대전 매칭 및 커뮤니티 웹 사이트 프로젝트 입니다.

![메인화면](https://user-images.githubusercontent.com/48443312/131247698-82a1bf98-0102-4f78-83da-bb2113faa161.png)

## 프로젝트 설명
Riot 개발사 IP인 Leage of Legends 의 플레이어간 팀 구성 및 대전 매칭, 커뮤니티 웹 사이트 입니다.
  - 자신의 소환사 명을 등록하여 이번 시즌 최근 전적, 현재 승률, 티어 등의 정보를 팀 구성 및 대전매칭에서 활용할 수 있습니다.
  - 팀 검색 메뉴에서 팀 생성의 경우 지원 제한 티어를 설정 할 수 있고, 이에 맞는 팀원만을 받아들 일 수 있는 기능을 제공합니다.
  - 조건 별 회원검색 메뉴에서 자신이 모집하고자 하는 팀원에 대한 조건을 만족하는 회원들을 검색해 볼 수 있습니다.
  - 대전 매칭의 경우 팀원들의 평균 티어를 산출 한 후 해당 평균과 비슷한 팀간의 매칭이 이루어집니다.
  - 챔피언 정보 메뉴를 통해 각 챔피언들의 간략한 정보들을 탐색해 볼 수 있습니다. 
  - 자유게시판, 팀원 모집 게시판, 팁 게시판을 통해 각 게시판의 주제에 맞는 글을 자유롭게 작성하고, 댓글로 다른 회원들과 소통하는 등의 커뮤니티 활등을 할 수 있습니다.

## 링크
- https://progg.cf
  -> 도메인 에러 발생시 : http://101.101.208.116
- IP 주소를 통한 직접 접근의 경우 https 연결이 제공되지 않습니다.
- 현재는 비용문제로 인해 서버를 운영하지 않고 있습니다. 

## 프로젝트 참여자
- 서호준 
  - 이메일 : ghwns6659@gmail.com
  - 깃허브 : https://github.com/HoJun-Seo
  - 작업 내역 :
    - 회원 및 소환사 정보, 팀, 게시판 DB 설계,구축 및 각 객체별 연관관계 매핑
    - 회원 가입, 로그인, 마이페이지 에서의 회원 정보 변경, 탈퇴 등의 각종 회원 관리 기능 구현
    - 소환사 명을 기준으로 한 소환사 전적 및 현재 티어 관련 정보 크롤링 및 소환사 큐 타입별 티어 및 승패 횟수, 승률 출력 기능 구현
    - 팀 생성 및 수정 기능, 팀 신청 수락, 거절 및 소환사 명 변경 시 팀 추방 기능 구현
    - 소환사 최근 전적 이미지 처리 작업 및 팀 검색 기능 구현
    - 페이스북, 구글 로그인 기능 구현
    - 게시글 및 댓글 작성 기능을 위한 기초적인 객체 설계
    - CKEditor 를 이용한 게시글 작성 시 이미지 업로드 오류 문제 해결
    - 게시판 글 작성일시 수집 및 작성한 글 찾아보기 기능 구현
    - 게시글 클릭한 조회 수 및 추천, 비추천 기능 구현 
    - 게시글에 대한 댓글 작성 기능 구현
    - 네이버 클라우드 플랫폼 서버 및 공인 IP 할당, freenom 사이트를 통한 도메인 주소 할당 및 WAR 파일 웹 배포
    - 외부 서버 배포로 인하여 발생된 각종 경로 참조와 같은 버그 해결
    - HTTPS 보안 연결  

- 박승진
  - 이메일 : jin12352@naver.com
  - 깃허브 : https://github.com/jin12352
  - 작업 내역 :
    - 부트스트랩, css 등을 활용하여 웹 사이트 시멘틱 구조 디자인 등의 프론트엔드 작업
    - 회원 가입 시 약관 동의 및 아이디, 닉네임 중복 체크 기능 구현
    - 리그 오브 레전드 각종 챔피언 정보 크롤링 및 정보 표시 기능 구현
    - 각 팀의 평균 티어 및 평균 승률 산출 및 출력 기능 구현
    - 각 팀의 평균 티어 별 상대 팀 매칭 기능 구현
    - 네이버, 카카오 로그인 기능 구현
    - CKEditor 를 통한 게시글 작성 내용 데이터베이스 저장 및 출력 기능 구현
    - CKEditor 를 이용한 게시글 작성 시 이미지 업로드 오류 문제 해결
    - 게시판에 작성된 게시글들 출력 및 페이징 기능 구현
    - 게시글에 작성된 댓글에 대한 답글 작성 기능 구현
    - css 오류 처리 및 기능 흐름상 발견된 각종 버그 해결

## 기술 스택
- 프론트엔드(Front-End)
  - Javascript
  - HTML/CSS
  - BootStrap
  - JSP/Servlet
  - jQuery
  - Ajax

- 백엔드(Back-End)
  - JAVA(JDK >= 11)
  - MySQL
  - Mybatis
  - Spring boot Framework(Gradle)

- 서버
  - 네이버 클라우드 서버 플랫폼
  - Apache Tomcat 8.5.70

- 사용 툴
  - IntelliJ IDEA Community Version
  - Eclipse IDEA
  - Visual Studio Code
  - Heidi SQL

- 협업 툴
  - GitHub

## 사용한 외부 API
- LOL API
  - 소환사 명 검색 API : 
    - 소환사 명을 검색하여 해당되는 소환사의 계정 정보를 받아오는 API 입니다.
    - 참조 링크 : https://developer.riotgames.com/apis#summoner-v4/GET_getBySummonerName
  
  ~~~
  /lol/summoner/v4/summoners/by-name/{summonerName}
  ~~~
  - 소환사 솔로 랭크 및 자유 랭크 전적 검색 API :
    - 수집한 소환사 id 값을 변수로 하여 소환사의 솔로 랭크 및 자유 랭크 티어, 전적을 받아오는 API 입니다.
    - 참조 링크 : https://developer.riotgames.com/apis#league-v4/GET_getLeagueEntriesForSummoner
  
  ~~~
  /lol/league/v4/entries/by-summoner/{encryptedSummonerId}
  ~~~
  - 소환사 최근 매치 id 값 갯수별 검색 API :
    - 소환사의 최근 경기부터 지정한 숫자의 매치 횟수 만큼의 각 매치별 id 값을 리스트 형태로 받아오는 API 입니다.
    - 참조 링크 : https://developer.riotgames.com/apis#match-v5/GET_getMatchIdsByPUUID

  ~~~
   /lol/match/v5/matches/by-puuid/{puuid}/ids
  ~~~
  - 소환사 매치 별 상세 정보 검색 API :
    - 각 매치 별 id 값을 변수로 하여 해당하는 매치의 상세 정보를 받아오는 API 입니다.(선택한 챔피언, 구매한 아이템, 사용한 소환사 스펠, K/D/A 정보 등등)
    - 참조 링크 : https://developer.riotgames.com/apis#match-v5/GET_getMatch
  
  ~~~
  /lol/match/v5/matches/{matchId}
  ~~~
- LOL 각종 리소스 경로
  - 챔피언 이미지 : 
    - LOL 챔피언들의 프로필 이미지 데이터를 호출해 올 수 있습니다.
    - 예시) http://ddragon.leagueoflegends.com/cdn/11.12.1/img/champion/Aatrox.png

  ~~~java
  String championImagePath = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/champion/"+championName+".png";
  ~~~
    
  - 챔피언 상세 정보 :
    - LOL 챔피언들의 상세 정보를 포함하고 있는 json 파일의 데이터를 호출해 올 수 있습니다.
    - 예시) https://ddragon.leagueoflegends.com/cdn/11.12.1/data/ko_KR/champion/Aatrox.json
  
  ~~~java
  String apiURL = "https://ddragon.leagueoflegends.com/cdn/11.12.1/data/ko_KR/champion/"+championName+".json";
  ~~~
  
  - 각종 아이템 이미지 :
    - LOL 에서 매치 중에 상점에서 구매할 수 있는 아이템 들의 이미지 데이터를 호출해 올 수 있습니다.
    - 예시) http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/***.png
  
  ~~~java
  String item0 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item0")+".png";
  ~~~
  
  - 각종 소환사 스펠 이미지 :
    - LOL 에서 매치 전에 지정하여 사용할 수 있는 소환사 스펠에 대한 이미지 데이터를 호출 해 올 수 있습니다.
    - 예시) http://ddragon.leagueoflegends.com/cdn/11.12.1/img/spell/***.png

  ~~~java
  String spell1 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/spell/"+spellDTO1.getSpellName()+".png";
  ~~~

- SNS 로그인 API
  - Naver Login API :
    - Naver 계정을 통해 간편하게 회원가입 및 로그인을 할 수 있습니다.
 
  - Kakao Login API : 
    - Kakao 계정을 통해 간편하게 회원가입 및 로그인을 할 수 있습니다.
			
  - Facebook Login API
    - Facebook 계정을 통해 간편하게 회원가입 및 로그인을 할 수 있습니다.
    - 현재는 서버의 https 보안 처리가 되지 않아 이용이 불가능 합니다.
    - (문제 해결) https 보안 연결이 성공하여 현재 정상적으로 페이스북 로그인 기능을 이용할 수 있습니다.(2022.03.14)
  
  - Google Login API 
    - Google 계정을 통해 간편하게 회원가입 및 로그인을 할 수 있습니다.
    - 현재는 서버의 https 보안 처리가 되지 않아 이용이 불가능 합니다.
    - (문제 해결) https 보안 연결이 성공하여 현재 정상적으로  로그인 기능을 이용할 수 있습니다.(2022.03.14)

## ERD
![proggERD](https://user-images.githubusercontent.com/48443312/158855002-cbff0598-4851-4bd2-beee-bc9e24988240.png)
## 화면별 기능 보기
- [메인 화면 및 회원가입, 로그인](https://evan-development.tistory.com/238)
- [회원 정보변경 및 회원 탈퇴](https://evan-development.tistory.com/239)
- [소환사 정보 등록 및 출력, 각종 챔피언에 대한 정보 출력](https://evan-development.tistory.com/240)
- [팀 생성 및 팀 검색](https://evan-development.tistory.com/241)
- [팀 상세 및 팀 신청](https://evan-development.tistory.com/242)
- [팀 수정 및 팀 탈퇴](https://evan-development.tistory.com/243)
- [조건별 회원 검색](https://evan-development.tistory.com/244)
- [게시판](https://evan-development.tistory.com/245)

## 개발 현황
### 2021/05/10
- 메인 페이지, 로그인, 회원가입, 마이페이지 등 기초적인 시멘틱 구조 뷰 페이지 구성
- 회원가입 및 로그인, 마이페이지 회원 정보 출력, 소환사 명 등록 및 변경 기능 구현
--------------------
### 2021/05/14
- 관리자 계정 생성 및 로그인 기능 구현
- 관리자 페이지 회원 닉네임 검색 기능 구현
  - 관리자 기능은 현재 실효성이 없다고 판단 제거됨(2022.03.16)
- 회원 가입 및 로그인 기능 단위 테스트 및 통합 테스트 수행
--------------------
### 2021/05/18
- 아이디, 비밀번호 찾기 기능 구현
- 회원 닉네임, 이름, 이메일 변경 기능 구현
- 회원 가입시 약관동의 및 아이디, 비밀번호, 이메일 중복 및 형식 유효성 검사 기능 구현
--------------------
### 2021/05/20
- 소환사 최근 전적 데이터 수집 및 저장, 출력 기능 구현
- 소환사 큐 타입별 티어 및 승패 횟수, 승률 저장 및 출력 기능 구현
- 부트스트랩, css 등을 활용한 각종 시멘틱 태그 프론트 엔드 기초작업
- 팀 매칭 게시판 개설 및 상세 정보 페이지 생성
--------------------
### 2021/05/25
- 팀 이름, 플레이 가능 시간, 티어 제한 등의 정보가 포함된 팀 생성 기능 
--------------------
### 2021/05/31
- 외관 수정 및 로그인 이후 마이페이지, 로그아웃 드롭다운 메뉴 추가
--------------------
### 2021/06/12
- 각 티어별 이미지 출력 및 챔피언 정보 완성
- 팀 신청 및 수락, 거절 기능 구현
--------------------
### 2021/06/17
- 팀 관련 기초 작업 및 팀당 평균티어 처리 구현
- 소환사 최근 전적 이미지 처리 구현
--------------------
### 2021/06/26
- 평균티어 기준으로 대전 상대 찾기 기능 구현
- 조건별 팀 검색 및 회원 검색 기능 구현
--------------------
### 2021/07/07
- 소셜로그인(네이버, 카카오, 페이스북, 구글) 구현
--------------------
### 2021/07/16
- 게시판 구조 설계 및 기초작업
- 게시판 개행처리 문제 해결
--------------------
### 2021/07/23
- 이미지 처리 에러현상 해결
--------------------
### 2021/08/03
- 게시글 자세히 보기 및 수정 기능 구현
- 게시글 조회수 및 마이페이지에서 게시글 삭제 기능 구현
--------------------
### 2021/08/05
- 5,10,20줄별 게시판 페이지 확인기능 구현
- 게시글 추천, 비추천 기능 구현
--------------------
### 2021/08/10
- 기초적인 댓글 작성기능 구현
--------------------
### 2021/08/15
- 댓글 답글기능 구현
--------------------
### ~ 2021/09/14
- 웹 서버 배포로 인해 환경이 달라져 발생한 각종 오류 해결
- 메인 화면에서 소환사 명 직접 검색 기능 

## 필요한 개선 사항
- https 보안 연결을 통한 기본적인 웹 데이터 보안 처리
- https 보안 처리를 통한 Facebook, Google 로그인 기능 정상화
- 소환사 명을 등록할 때 소환사명 사칭에 대한 대책
- 게시판 글 작성 시 이미지 업로드 오류 문제 해결

## 개선된 사항
- 게시판 글 작성 시 이미지 업로드 오류 문제 해결(해결 완료)
  - 로컬에서 개발 할 때 사용했던 이미지 저장 경로가 수정되어 있지 않았음
  - 로컬에서 개발 할 때 필요했던 반복문 로직이 서버 상에서는 불필요하게 문자열 인덱스 오류를 발생시키는 원인이 되어 제거함
  - 결과 : 이미지 정상 업로드 및 저장, 불러오기 성공
- https 보안 연결 성공 (2022.03.14)
  - Let's Encrypt 를 활용하여 서버에 https 보안 연결을 성공함
  - 이를 토대로 기존에 보안 연결 문제로 인해 정상적으로 기능을 제공하지 못했던 구글, 페이스북 로그인을 다시 정상적으로 동작시킴
