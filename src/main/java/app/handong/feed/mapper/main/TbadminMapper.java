package app.handong.feed.mapper.main;

import app.handong.feed.dto.TbadminDto;

import java.util.List;

public interface TbadminMapper {
    List<TbadminDto.UserDetail> allUsers();
}
