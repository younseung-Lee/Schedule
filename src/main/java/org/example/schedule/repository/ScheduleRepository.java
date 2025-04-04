package org.example.schedule.repository;

import org.example.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    default ScheduleEntity findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }
    // 유저 ID로 스케줄 필터링
    List<ScheduleEntity> findAllByUserId(Long userId);
}

