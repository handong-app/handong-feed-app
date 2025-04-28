package app.handong.feed.service;


import app.handong.feed.dto.TbsubjectDto;


public interface TbsubjectService {

    TbsubjectDto.DetailResDto getDetail(TbsubjectDto.DetailReqDto param);

}