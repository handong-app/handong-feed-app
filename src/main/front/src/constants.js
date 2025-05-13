export const serverRootUrl =
  import.meta.env.MODE === "development" ? "/api" : "/api";

export const googleClientId =
  "542164017545-c6tsebe44kpkpk43b7u8njouir63oqq8.apps.googleusercontent.com";

// 식단 테이블 데이터
export const mealData = {
  food: {
    koreantable: {
      breakfast: "운영없음",
      lunch: "잔치국수<br />추가밥<br />찐만두<br />요구르트<br />맛김치",
      dinner:
        "쌀밥<br />미역국<br />닭살간장볶음<br />데친두부<br />열무나물<br />깍두기",
    },
    fryfry: "운영없음",
    mixedrice: "돼지국밥<br />양념돈까스",
    lounge: "운영없음",
    momskitchen: {
      breakfast: "빵식",
      lunch: "잔치국수<br />추가밥<br />찐만두<br />요구르트<br />맛김치",
      dinner:
        "쌀밥<br />미역국<br />닭살간장볶음<br />데친두부<br />열무나물<br />깍두기",
    },
    gracetable: {
      lunch: "잔치국수<br />추가밥<br />찐만두<br />요구르트<br />맛김치",
      dinner:
        "쌀밥<br />미역국<br />닭살간장볶음<br />데친두부<br />열무나물<br />깍두기",
    },
  },
};

// 공지 데이터
export const notices = [
  {
    id: 1,
    title: "[학사/등록금] 2025-1학기 등록금 2차 분납 및 초과학기자 납부 안내",
    content:
      "2025-1학기 2차 분납 및 초과학기자 등록금 납부에 관한 안내입니다. 자세한 내용은 학교 홈페이지를 참고하세요.",
  },
  {
    id: 2,
    title: "[의료원] 2025-1학기 통증의학과 병원 비상대체원칙 및 회의 안내",
    content:
      "2025-1학기 통증의학과 병원 비상대체원칙 및 회의 일정에 대한 안내입니다. 자세한 내용은 첨부파일을 확인해 주세요.",
  },
  {
    id: 3,
    title: "[더미] 2025-1학기 더미 공지 제목",
    content:
      "이것은 더미 공지의 내용입니다. 실제 공지 데이터가 들어갈 자리입니다.",
  },
];
