package org.example.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(@RequestBody CreateScheduleRequestDto createScheduleRequestDto){
        CreateScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(createScheduleRequestDto);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll(){
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();
        return new ResponseEntity<>(scheduleResponseDtoList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id){
        ScheduleResponseDto byId = scheduleService.findById(id);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateScheduleResponseDto> update(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto requestDto
    ){
        UpdateScheduleResponseDto updateScheduleResponseDto = scheduleService.updateSchedule(id, requestDto);
        return new ResponseEntity<>(updateScheduleResponseDto,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,@RequestBody DeleteScheduleRequestDto deleteScheduleRequestDto){
        scheduleService.deleteSchedule(id, deleteScheduleRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
