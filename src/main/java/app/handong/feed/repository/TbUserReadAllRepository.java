package app.handong.feed.repository;

import app.handong.feed.domain.TbUserReadAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserReadAllRepository extends JpaRepository<TbUserReadAll, String> {

}
