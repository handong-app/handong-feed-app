package app.handong.feed.service;

import app.handong.feed.dto.TbSubjectTagDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TbSubjectTagService {
    TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto);
    List<TbSubjectTagDto.ReadResDto> readSubjectTags(int subjectId);
    TbSubjectTagDto.DeleteResDto deleteSubjectTag(int id);
    TbSubjectTagDto.DeleteResDto deleteSubjectTag(TbSubjectTagDto.DeleteReqDto dto);

}
