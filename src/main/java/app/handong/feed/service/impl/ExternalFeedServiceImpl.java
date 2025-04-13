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

    /**
     * 특정 시간 범위 내의 피드 메시지를 조회합니다.
     *
     * @param startTimestamp 조회 시작 시간(초)
     * @param endTimestamp   조회 종료 시간(초)
     * @param isNew          새 메시지만 필터링할지 여부
     * @param limit          반환할 최대 결과 수
     * @return 피드 메시지 목록
     * @throws IllegalArgumentException 시작 시간이 종료 시간보다 늦을 경우
     */
    public List<TbmessageDto.Detail> getFeedBetween(Long startTimestamp, Long endTimestamp, boolean isNew, Integer limit) {
        if (startTimestamp != null && endTimestamp != null && startTimestamp > endTimestamp) {
            throw new IllegalArgumentException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }
        return tbmessageMapper.externalFeedGetAll(startTimestamp, endTimestamp, limit, isNew);
    }

    public List<TbmessageDto.Detail> getFeedBetween(ExternalDto.FeedReqDto dto) {
        return tbmessageMapper.externalFeedGetAll(dto.getStart(), dto.getEnd(), dto.getLimit(), dto.isFilterNew());
    }

}
