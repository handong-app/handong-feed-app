package app.handong.feed.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface TbfeedService {
    public Map<String, Object> create(Map<String, Object> param);
    public Map<String, Object> update(Map<String, Object> param);
    public Map<String, Object> get(String id);
    public void delete(String id);
    public List<Map<String, Object>> getAll();

}
