package app.handong.feed.service;

import app.handong.feed.dto.ExternalDto;
import app.handong.feed.dto.TbmessageDto;

import java.util.List;


public interface ExternalFeedService {
    List<TbmessageDto.Detail> getFeedBetween(Long startTimestamp, Long endTimestamp, boolean isNew, Integer limit);
    List<TbmessageDto.Detail> getFeedBetween(ExternalDto.FeedReqDto dto);
}
