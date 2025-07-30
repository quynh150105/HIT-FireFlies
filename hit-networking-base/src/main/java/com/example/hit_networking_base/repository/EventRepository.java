package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.deletedAt IS NULL")
    Page<Event> findByDeletedAtIsNull(Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.eventId = :eventId AND e.deletedAt IS NULL")
    Optional<Event> findByEventIdAndDeletedAtIsNull(Long eventId);

}
