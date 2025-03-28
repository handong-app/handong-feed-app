package app.handong.feed.service.impl;

import app.handong.feed.dto.TbadminDto;
import app.handong.feed.exception.auth.NoAuthorizationException;
import app.handong.feed.id.UserPermId;
import app.handong.feed.mapper.TbadminMapper;
import app.handong.feed.repository.TbUserPermRepository;
import app.handong.feed.service.FirebaseService;
import app.handong.feed.service.TbadminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TbadminServiceImpl implements TbadminService {
    private final TbadminMapper tbadminMapper;
    private final TbUserPermRepository tbUserPermRepository;
    private final FirebaseService firebaseService;

    public TbadminServiceImpl(TbadminMapper tbadminMapper, TbUserPermRepository tbUserPermRepository, FirebaseService firebaseService) {
        this.tbadminMapper = tbadminMapper;
        this.tbUserPermRepository = tbUserPermRepository;
        this.firebaseService = firebaseService;
    }

    public List<TbadminDto.UserDetail> adminGetUser(String userId, Map<String, String> param) {

        if (tbUserPermRepository.findById(new UserPermId(userId, "adminGetUser")).isEmpty())
            throw new NoAuthorizationException("No Admin Permission");
        return tbadminMapper.allUsers();
    }

    public List<String> adminGetFirebaseStorageList(String userId) {

        if (tbUserPermRepository.findById(new UserPermId(userId, "adminFirebaseFiles")).isEmpty())
            throw new NoAuthorizationException("No Admin Permission");
        return firebaseService.listAllFiles("KaFile");
    }

}
