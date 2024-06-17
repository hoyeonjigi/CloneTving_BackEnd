# CloneTving 백엔드

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
            </li>
            <li>
              인기 컨텐츠 조회
            </li>
            <li>
              제목 검색으로 조회
            </li>
          </ol>
        </li>
        <li>
          기타
          <ol style="list-style-type:upper-roman">
            <li>
              리뷰 등록
            </li>
            <li>
              찜 등록
            </li>
          </ol>
        </li>
      </ul>
    </div>
</details>
<details>
  <summary>
      인터페이스 설계
  </summary>
</details>
<details>
  <summary>
      아키텍쳐 설계
  </summary>

    <div align=center><h1>📚TECH STACKS</h1></div>
    
    <div align=center> 
      <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
      <br>
      
      <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
      <br>
    </div>
</details>
<details>
  <summary>
      엔티티 관계 모형 기술
  </summary>
</details>
<details>
  <summary>
      데이터베이스 설계
  </summary>
</details>

