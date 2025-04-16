import { authJwtAtom } from "../recoil/authAtom";
import { useRecoilValue } from "recoil";
import LoginPage from "../components/LoginPage";
import MainDisplay from "../components/MainDisplay";
import { Card, CardContent, Typography } from "@mui/material";
import { fetchBe } from "../tools/api";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import FeedCard from "../components/FeedCard";

function KafeedDetail() {
  const { messageId } = useParams();
  const [doc, setDoc] = useState();

  const jwtValue = useRecoilValue(authJwtAtom);

  useEffect(() => {
    if (!jwtValue) return;
    fetchBe(jwtValue, `/kafeed/get/${messageId}`).then((data) => setDoc(data));
  }, [jwtValue, messageId]);
  if (!jwtValue) return <LoginPage />;
  if (!doc)
    return (
      <MainDisplay>
        <FeedCard key="loading" loading />
      </MainDisplay>
    );
  return (
    <MainDisplay>
      {doc?.id ? (
        <FeedCard
          key={`detail-${doc.id}`}
          item={{
            author: "실명카톡방2",
            sentAtEpoch: doc.sentAt,
            createdAt: new Date(doc.sentAt * 1000),
            id: doc.id,
            content: doc.message,
            files: doc.files,
            img: doc.files[0], // Temp just for testing
            subjectId: doc.subjectId,
            like: doc.like,
            messageCount: doc.messageCount,
            tags: doc.tags,
          }}
        />
      ) : (
        <Card sx={{ my: 2 }}>
          <CardContent>
            <Typography variant="h5" component="div" align="center">
              {doc.status === 404 ? "Not Found" : "Error"}
            </Typography>
          </CardContent>
        </Card>
      )}
    </MainDisplay>
  );
}

export default KafeedDetail;
