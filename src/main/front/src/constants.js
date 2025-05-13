export const serverRootUrl =
  import.meta.env.MODE === "development" ? "/api" : "/api";

export const googleClientId =
  "542164017545-c6tsebe44kpkpk43b7u8njouir63oqq8.apps.googleusercontent.com";

// 식단 테이블 데이터
export const mealData = {
  아침: {
    메뉴항목: ["쌀밥", "계란찜", "미역국", "감자조림", "배추김치"],
  },
  점심: {
    메뉴항목: ["잡곡밥", "된장찌개", "닭갈비", "시금치나물", "깍두기"],
  },
  저녁: {
    메뉴항목: ["볶음밥", "유부장국", "돈까스", "양배추샐러드", "열무김치"],
  },
};

// 추천 피드 데이터
export const recommendedFeeds = [
  {
    id: 1,
    tag: "#동아리",
    title: "🔥Motion In Christ 남자 추가 리크루팅🔥",
    content:
      "안녕하세요! 25학번 새내기 여러분을 위한 MIC 동아리에서 남자 신입 회원을 추가 모집합니다.",
    description:
      "하나님께 영광을 돌리는 사역 단체이자 팝핀 기반의 동아리 MIC입니다.",
    year: "2025년 신입기수 MIC31기 남자 추가 모집!",
    buttonText: "> 한동피드에서 더 보기",
  },
  {
    id: 2,
    tag: "#동아리",
    title: "🔥Motion In Christ 남자 추가 리크루팅🔥",
    content:
      "MIC 동아리에서 2025년 신입 남자 회원을 기다리고 있습니다. 많은 지원 바랍니다!",
    description:
      "믿음과 춤을 함께하는 MIC 동아리에서 새로운 가족을 찾고 있습니다.",
    year: "2025년 MIC31기 남자 추가 모집",
    buttonText: "> 한동피드에서 더 보기",
  },
  {
    id: 3,
    tag: "#동아리",
    title: "🔥Motion In Christ 남자 추가 리크루팅3🔥",
    content:
      "MIC 동아리에서 열정 가득한 25학번 남자 신입을 모집합니다! 함께 성장해요.",
    description: "믿음과 열정이 넘치는 MIC 동아리에서 여러분을 기다립니다.",
    year: "2025년 MIC31기 남자 추가 모집",
    buttonText: "> 한동피드에서 더 보기",
  },
  {
    id: 4,
    tag: "#동아리",
    title: "🔥Motion In Christ 남자 추가 리크루팅4🔥",
    content:
      "MIC 동아리에서 새로운 남자 신입 회원을 모집합니다. 많은 관심 부탁드립니다!",
    description: "MIC 동아리와 함께 멋진 대학 생활을 시작해보세요!",
    year: "2025년 MIC31기 남자 추가 모집",
    buttonText: "> 한동피드에서 더 보기",
  },
];

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
