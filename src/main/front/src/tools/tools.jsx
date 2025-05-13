import { format, formatDistanceToNowStrict } from "date-fns";
import { ko } from "date-fns/locale";
import { diffWords } from "diff";

export function formatTimestamp(timestamp, dayWeek = false) {
  const date = new Date(timestamp);
  const dateFormat = dayWeek
    ? "yyyy년 MM월 dd일 EEEE a h:mm"
    : "yyyy년 MM월 dd일 a h:mm";

  return format(date, dateFormat, { locale: ko });
}

export function formatRelativeOrAbsoluteTimestamp(timestamp) {
  const now = new Date();
  const date = new Date(timestamp);
  const diffMs = now - date;
  const diffHours = Math.floor(diffMs / (1000 * 60 * 60));

  if (diffHours < 24) {
    return formatDistanceToNowStrict(date, { addSuffix: true, locale: ko });
  } else {
    return formatTimestamp(timestamp);
  }
}

// Function to detect URLs and convert them to hyperlinks
export const convertTextToLinks = (text) => {
  // Regular expression to detect URLs
  const urlRegex = /(https?:\/\/[^\s]+)/g;

  // Split the text by URLs and map over the parts
  return text.split(urlRegex).map((part, index) => {
    // If the part matches the URL regex, return a hyperlink
    if (part.match(urlRegex)) {
      return (
        <a href={part} key={index} target="_blank" rel="noopener noreferrer">
          {part}
        </a>
      );
    }
    // Otherwise, return the text as it is
    return <span key={index}>{part}</span>;
  });
};

export const removeDuplicates = (arr, key) => {
  const seen = new Set();
  return arr.filter((item) => {
    const val = item[key]; // Get the property value to check
    if (seen.has(val)) {
      return false; // Skip if it's already in the set
    }
    seen.add(val); // Otherwise, add to set and keep the item
    return true;
  });
};

export const getExtensionFromUrl = (url) => {
  const cleanUrl = url.split("?")[0]; // Remove anything after '?'
  return cleanUrl.split(".").pop().toLowerCase(); // Get the file extension
};

// Function to check if the URL is an image
export const isImage = (url) => {
  const imageExtensions = [
    "jpg",
    "jpeg",
    "png",
    "gif",
    "webp",
    "bmp",
    "svg",
    "apng",
    "ico",
  ];

  const extension = getExtensionFromUrl(url); // Get cleaned extension
  return imageExtensions.includes(extension);
};

// Function to check if the URL is a video
export const isVideo = (url) => {
  const videoExtensions = ["mp4", "webm", "ogg", "mov", "avi"];
  const extension = getExtensionFromUrl(url); // Get cleaned extension
  return videoExtensions.includes(extension);
};

export const getDateString = () => {
  const date = new Date();
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are zero-based
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
};

export const calculateDiffChange = (oldValue, newValue) => {
  const changes = diffWords(oldValue, newValue);

  let added = 0;
  let removed = 0;

  // Iterate over the diff chunks to calculate additions and deletions
  changes.forEach((part) => {
    if (part.added) {
      added += part.value.length;
    } else if (part.removed) {
      removed += part.value.length;
    }
  });

  // Format the result with added and removed changes
  let result = "";
  if (added > 0) result += `+${added}`;
  if (removed > 0) result += (result.length > 0 ? " " : "") + `-${removed}`;

  const diff = added - removed;
  if (added === 0 && removed === 0) return "일치";
  if (diff === 0) return "0";
  return `${diff > 0 ? "+" : ""}${diff}`;
  // return result || "No changes";
};

export function parseMealString(str) {
  if (typeof str !== "string") return str;
  // "운영없음" 등은 그대로 반환
  if (str.includes("<br />")) {
    return str.split("<br />");
  }
  return [str];
}

export function getCurrentWeekdayString(date = new Date()) {
  // 2025년 3월 3일(월) 개강 기준
  const semesterStart = new Date(2025, 2, 3); // 월은 0부터 시작 (2=3월)
  const msPerDay = 1000 * 60 * 60 * 24;
  const daysPassed = Math.floor((date - semesterStart) / msPerDay);
  const week = Math.floor(daysPassed / 7) + 1;
  const weekdays = [
    "일요일",
    "월요일",
    "화요일",
    "수요일",
    "목요일",
    "금요일",
    "토요일",
  ];
  const weekday = weekdays[date.getDay()];
  return `${week}주차 ${weekday}`;
}
