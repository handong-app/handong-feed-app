package app.handong.feed.service;

import app.handong.feed.dto.TbadminDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TbadminService {
    public List<TbadminDto.UserDetail> adminGetUser(String userId, Map<String, String> param);

    public List<String> adminGetFirebaseStorageList(String userId);
}
