export const serverRootUrl =
  import.meta.env.MODE === "development" ? "/api" : "/api";

export const googleClientId =
  "542164017545-c6tsebe44kpkpk43b7u8njouir63oqq8.apps.googleusercontent.com";

// 식단 테이블 데이터
export const mealData = {
  food: {
    koreantable: {
      breakfast: "운영없음2",
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
  },
  {
    id: 2,
    title: "[의료원] 2025-1학기 통증의학과 병원 비상대체원칙 및 회의 안내",
  },
];
