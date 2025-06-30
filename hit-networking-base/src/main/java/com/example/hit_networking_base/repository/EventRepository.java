package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
