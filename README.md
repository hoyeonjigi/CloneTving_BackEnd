# CloneTving 백엔드

# 프로젝트 소개
- 학습 목적으로 클론 코딩 구현한 OTT Tving 사이트 API 서버입니다
- <b> 스웨거 주소(API 명세) : https://hoyeonjigi.site/swagger-ui/index.html </b>
- <b> 사이트 링크 : https://clone-tving.vercel.app </b>
- <b> 테스트 계정 : 아이디: test003 ,비밀번호 : test003!

<br>

<div>
<h1>📚TECH STACKS</h1></div>
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <br>
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/jpa-003545?style=for-the-badge&logo=jpa&logoColor=white"/>
  <img src="https://img.shields.io/badge/springdatajpa-6DB33F?style=for-the-badge&logo=springdatajpa&logoColor=white"/>
  <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/>
  <br>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white"/>
  <img src="https://img.shields.io/badge/querydsl-4169E1?style=for-the-badge&logo=querydsl&logoColor=white"/>
  <br>
  <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"/>
  <img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazonec2&logoColor=white"/>
  <br>
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"/>
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"/>
  <br>
</div>

# API 기능 설명 
### 맴버
- 회원가입 : 맴버 정보를 입력받아 데이터베이스에 맴버 저장, 동일한 로그인ID 사용 불가
- 로그인 : 회원가입된 맴버의 아이디, 패스워드를 입력받아 해당 맴버 조회 후 일치하면 로그인 완료 (엑세스 토큰과 리프레시 토큰 반환)
- 삭제 : 회원가입된 맴버의 아이디를 입력받아 해당 맴버 조회 후 존재하면 삭제
- 아이디 중복 체크 : 회원가입할 아이디를 입력받아 해당 맴버 조회 후 존재여부 반환
- 토큰 재발급 : 엑세스 토큰의 유효기간이 만료됬을 경우, 리프레쉬 토큰으로 요청 시 만료여부를 확인하고 만료되지 않았을 경우 토큰 재발급 (새로운 엑세스 토큰과 리프레쉬 토큰을 반환)

### 프로필
- 등록 : 프로필 정보를 입력받아 데이터베이스에 프로필 저장, 계정 내에 동일한 프로필 이름 사용 불가
- 수정 : 프로필 아이디로 프로필 조회후 회원 액세스 토큰 인증 후 입력받은 정보로 프로필 정보 변경
- 삭제 : 프로필 아이디로 프로필 조회후 회원 액세스 토큰 인증 후 해당 프로필 삭제
- 조회(아이디) : 프로필 아이디로 프로필 정보 반환
- 조회(리스트) : 액세스 토큰으로 해당 유저의 프로필 리스트 반환

### 프로필 이미지
- 조회(리스트) : Amazon S3에 저장되어 있는 프로필 이미지 URL 반환

### 장르 
- 조회 : 장르 아이디로 장르 조회후 장르 정보 반환

### 콘텐츠
- 조회수 증가 : 콘텐츠 아이디로 해당 콘텐츠 조회수 증가
- 조회(아이디) : 콘텐츠 아이디로 해당 콘텐츠 정보를 반환
- 조회(리스트) : 콘텐츠 리스트 조회, 쿼리 파라미터 [type: 드라마 또는 영화], [sort: 인기순, 최신순, 이름순], [page: 페이지 번호], [genreName : 해당 장르가 포함된 콘텐츠], [title: 제목 이름]

### 평가
- 리뷰 전체 조회 : 해당 컨텐츠의 리뷰를 [최신순/인기순]으로 전체 조회하여 반환
- 특정 프로필 리뷰 조회 : 해당 컨텐츠의 리뷰들에서 특정 프로필의 리뷰를 조회하여 반환 
- 평균 별점 조회 : 해당 컨텐츠의 별점들의 평균을 조회하여 반환
- 리뷰 작성 : 리뷰 정보들을 입력받아 데이터베이스에 저장 
- 리뷰 수정 : 수정할 리뷰 정보들을 입력받아 데이터베이스 수정
- 리뷰 삭제 : 삭제할 평가ID를 입력받아 해당 리뷰가 존재하는지 확인 후, 존재하면 삭제
- 리뷰 추천 기능 : 리뷰의 좋아요, 싫어요 기능 API

<br/>

<details>
    <summary> 사용자 요구사항 명세서</summary>
    <div>
      <ul style="font">
        <li>
          회원가입
          <ol style="list-style-type:upper-roman">
            <li>
              회원가입 정보는 다음과 같다
              <ul>
                <li>
                  아이디: 5~20자의 영문 소문자, 숫자만 사용가능
                </li>
                <li>
                  비밀번호 : 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용
                </li>
                <li>
                  기타정보 : 이메일, 성인여부, 서비스 이용여부, 마케팅 정보 SMS 수신 여부, 마케팅 정보 이메일 수신 여부
                </li>
              </ul>
            </li>
            <li>
              가입 절차
              <ul>
                <li>
                  중복 아이디 확인
                </li>
                <li>
                  비밀번호 입력 값과 비밀번호 확인 입력 값이 일치하는지 검증
                </li>
                <li>
                  모든 입력값 검증 수행 후 회원가입 로직 실행
                </li>
                <li>
                  회원가입이 완료되면 201응답 코드와 간단한 회원 정보 반환
                </li>
              </ul>
            </li>
          </ol>
        </li>
        <li>
          로그인
          <ol style="list-style-type:upper-roman">
            <li>
              로그인 시도
              <ul>
                <li>아이디 비밀번호 일치시 로그인 성공</li>
              <li>
              자동로그인 체크박스 선택시 자동로그인
            </li>
              </ul>
            </li>
          </ol>
        </li>
        <li>
          사용자 프로필
          <ol style="list-style-type:upper-roman">
            <li>
              프로필 등록
              <ul>
                <li>
                  사용자는 자신이 사용할 프로필을 등록 할 수 있음
                </li>
                <li>
                  프로필 등록 정보는 프로필 이름, 프로필 이미지, 성인 여부로 구성
                </li>
              </ul>
            </li>
            <li>
              프로필 수정 
              <ul>
                <li>
                  사용자는 프로필 이름과 이미지 그리고 성인여부를 수정 가능
                </li>
              </ul>
            </li>
            <li>
              프로필 삭제
              <ul>
                <li>
                  사용자는 등록된 프로필을 삭제 가능
                </li>
              </ul>
            </li>
          </ol>
        </li>
        <li>
          컨텐츠 조회
          <ol style="list-style-type:upper-roman">
            <li>
              장르별 컨텐츠 조회
                <ul>
                    <li>
                        사용자는 로맨스, 액션 등 장르별로 분류된 컨텐츠를 조회할 수 있다
                    </li> 
                </ul>
            </li>
            <li>
              인기 컨텐츠 조회
                <ul>
                    <li>
                        추천수가 많은 순으로 인기있는 컨텐츠를 조회할 수 있다
                    </li>
                </ul>
            </li>
            <li>
              제목 검색으로 조회
                <ul>
                    <li>
                        제목을 입력해 일치하는 컨텐츠를 조회할 수 있음
                    </li>
                </ul>
            </li>
          </ol>
        </li>
        <li>
          기타
          <ol style="list-style-type:upper-roman">
            <li>
              리뷰 등록
                <ul>
                    <li>
                        사용자는 컨텐츠마다 별점 및 한줄평 작성 가능
                    </li>
                </ul>
            </li>
            <li>
              찜 등록
                <ul>
                    <li>
                        자신이 원하는 컨텐츠를 찜 목록에 등록 가능
                    </li>
                </ul>
            </li>
          </ol>
        </li>
      </ul>
    </div>
</details>
<details>
  <summary>
      아키텍쳐 설계
  </summary> 
    <div>
        <br>
        <p align="center"><img src="https://github.com/hoyeonjigi/CloneTving_BackEnd/assets/105578140/c9141693-2896-426f-8361-81d8d1dcfde8"></p>
    </div>
</details>
<details>
  <summary>
      엔티티 관계 모형 기술
  </summary>
    <img width="814" alt="스크린샷 2024-07-12 오후 2 46 09" src="https://github.com/user-attachments/assets/8ad85571-5092-4951-be8d-aef0692782c6">
</details>
<details>
  <summary>
      데이터베이스 설계
  </summary>
    <img width="890" alt="스크린샷 2024-07-12 오후 2 40 11" src="https://github.com/user-attachments/assets/3675ced7-3631-4cba-9089-f36227493045">
</details>
<details>
  <summary>
      클래스 다이어그램
  </summary>
    <div>
        <br>
        <p align="center"><img src="https://github.com/user-attachments/assets/4a6b8a56-615c-41b3-a245-616074c85e2c"></p>
    </div>
</details>
<details>
  <summary>
      시퀀스 다이어그램
  </summary>
    <div>
        <br>
        <p align="center"><img src="https://github.com/user-attachments/assets/f6c56283-4a66-4780-a164-d64699ddb51c"></p>
    </div>
    <div>
        <br>
        <p align="center"><img src="https://github.com/user-attachments/assets/bf11b8c7-9707-47c6-b49a-7d7f5e0f176d"></p>
    </div>
    <div>
        <br>
        <p align="center"><img src="https://github.com/user-attachments/assets/0173b352-d0ae-432c-9930-836fdf4109e6"></p>
    </div>
    <div>
        <br>
        <p align="center"><img src="https://github.com/user-attachments/assets/ce27569d-d092-41d7-abe2-1fe47be918a1"></p>
    </div>
</details>
