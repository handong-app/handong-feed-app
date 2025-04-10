package app.handong.feed.service.impl;

import app.handong.feed.dto.ExternalDto;
import app.handong.feed.dto.TbmessageDto;
import app.handong.feed.mapper.TbmessageMapper;
import app.handong.feed.service.ExternalFeedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalFeedServiceImpl implements ExternalFeedService {

    private final TbmessageMapper tbmessageMapper;

    public ExternalFeedServiceImpl(TbmessageMapper tbmessageMapper) {
        this.tbmessageMapper = tbmessageMapper;
    }

    public List<TbmessageDto.Detail> getFeedBetween(Long startTimestamp, Long endTimestamp, boolean isNew, Integer limit) {
        return tbmessageMapper.externalFeedGetAll(startTimestamp, endTimestamp, limit, isNew);
    }

    public List<TbmessageDto.Detail> getFeedBetween(ExternalDto.FeedReqDto dto) {
        return tbmessageMapper.externalFeedGetAll(dto.getStart(), dto.getEnd(), dto.getLimit(), dto.isFilterNew());
    }

}
