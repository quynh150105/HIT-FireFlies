package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

//    @Query("SELECT e FROM Event e WHERE e.deletedAt IS NULL")
//    Page<Event> findAll(Pageable pageable);

    Page<Event> findByDeletedAtIsNull(Pageable pageable);


}
