package app.handong.feed.service;

import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TbuserDto;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface TbuserService {


    TbuserDto.DetailResDto detail(DefaultDto.DetailReqDto param);

    Map<String, Object> create(Map<String, Object> params);

    TbuserDto.CreateResDto access(String param) throws Exception;

    //    TbuserDto.CreateResDto access(String param) throws Exception;

    TbuserDto.CreateResDto loginWithGoogle(String credential);
}