package com.entain.events_api.events.sport_events.repository;

import com.entain.events_api.events.sport_events.model.EventStatus;
import com.entain.events_api.events.sport_events.model.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportEventsRepository extends JpaRepository<SportEvent, Long> {

    @Query("SELECT e FROM SportEvent e WHERE (:status IS NULL OR e.status = :status) AND (:sport IS NULL OR e.sport = :sport)")
    List<SportEvent> findByStatusOrSport(@Param("status") EventStatus status, @Param("sport") String sport);

}
