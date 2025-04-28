package app.handong.feed.service;


import app.handong.feed.dto.TbsubjectDto;
import org.springframework.stereotype.Service;


public interface TbsubjectService {

    TbsubjectDto.DetailResDto getDetail(TbsubjectDto.DetailReqDto param);

}