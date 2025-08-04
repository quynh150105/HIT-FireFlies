package com.example.hit_networking_base.repository;


import com.example.hit_networking_base.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Event, Long> {

    @Query(value =
            "SELECT * FROM ( " +
                    "    SELECT " +
                    "        e.event_id AS postId, " +
                    "        e.created_at AS createdAt, " +
                    "        'EVENT' AS targetType " +
                    "    FROM event e " +
                    "    WHERE e.deleted_at IS NULL " +

                    "    UNION ALL " +

                    "    SELECT " +
                    "        j.post_id AS postId, " +
                    "        j.created_at AS createdAt, " +
                    "        'JOB' AS targetType " +
                    "    FROM job_post j " +
                    "    WHERE j.deleted_at IS NULL " +
                    ") AS merged_posts " +
                    "ORDER BY createdAt DESC " +
                    "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<Object[]> getMergedPosts(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value =
            "SELECT COUNT(*) FROM ( " +
                    "    SELECT e.event_id FROM event e WHERE e.deleted_at IS NULL " +
                    "    UNION ALL " +
                    "    SELECT j.post_id FROM job_post j WHERE j.deleted_at IS NULL " +
                    ") AS total_posts",
            nativeQuery = true)
    Long countMergedPosts();


}
