package app.handong.feed.service;

import app.handong.feed.dto.TbmessageDto;

import java.util.List;
import java.util.Map;


public interface TbKaFeedService {
    public Map<String, Object> create(Map<String, Object> param);

    public Map<String, Object> getLastKaFeed();

    //    public Map<String, Object> update(Map<String, Object> param);
//    public Map<String, Object> get(String id);
//    public void delete(String id);
    public List<Map<String, Object>> getAll();

    List<TbmessageDto.Detail> scrollList(String type, String userId, String search, String[] searchTags);

    List<TbmessageDto.Detail> scrollList(String type, int afterSentAt, String userId, String search, String[] searchTags);

    int count(int afterSentAt, String userId);

    int count(String userId);

    TbmessageDto.Detail getOneHash(String hash);

    TbmessageDto.Detail getOne(String messageId, String userId);

}

