package org.example.schedule.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.example.schedule.entity.ScheduleEntity;
import org.example.schedule.entity.UserEntity;
import org.example.schedule.repository.ScheduleRepository;
import org.example.schedule.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    @Transactional
    public CreateScheduleResponseDto createSchedule(CreateScheduleRequestDto requestDto) {
        // 1. 유저 조회
        UserEntity user = userRepository.findByIdOrElseThrow(requestDto.getUserId());

        // 2. 비밀번호 검증
        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }

        // 3. 스케줄 생성 및 저장
        ScheduleEntity schedule = new ScheduleEntity(
                requestDto.getTitle(),
                requestDto.getContent(),
                user
        );
        ScheduleEntity savedSchedule = scheduleRepository.save(schedule);

        // 4. 응답 DTO 반환
        return new CreateScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getUser().getId(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    public List<ScheduleResponseDto> findAllByUser(Long userId) {
        List<ScheduleEntity> schedules = scheduleRepository.findAllByUserId(userId);
        return schedules.stream().map(ScheduleResponseDto::toDto).toList();
    }


    public ScheduleResponseDto findById(Long id) {
        Optional<ScheduleEntity> byId = scheduleRepository.findById(id);
        if (byId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id : "+ id);
        }
        ScheduleEntity schedule = byId.get();
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
                );
    }

    @Transactional
    public UpdateScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto) {

        ScheduleEntity schedule = scheduleRepository.findByIdOrElseThrow(id);

        UserEntity user = userRepository.findByIdOrElseThrow(requestDto.getUserId());


        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        if (!schedule.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "스케줄 작성자만 수정할 수 있습니다.");
        }

        schedule.updateSchedule(requestDto.getTitle(), requestDto.getContent());

        return new UpdateScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void deleteSchedule(Long id, DeleteScheduleRequestDto requestDto) {
        // 1. 삭제하려는 스케줄 찾기
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 스케줄이 존재하지 않습니다."));

        // 2. 요청을 보낸 사용자를 조회
        UserEntity user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 사용자가 존재하지 않습니다."));

        // 3. 비밀번호 검증
        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 4. 스케줄을 작성한 사용자와 요청 사용자가 일치하는지 검증
        if (!schedule.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "스케줄 작성자만 삭제할 수 있습니다.");
        }

        // 5. 스케줄 삭제
        scheduleRepository.delete(schedule);
    }
}

