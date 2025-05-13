package app.handong.feed.repository;

import app.handong.feed.domain.TbTagReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbTagReportRepository extends JpaRepository<TbTagReport, String> {
}