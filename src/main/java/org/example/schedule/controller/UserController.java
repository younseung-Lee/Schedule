package org.example.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.example.schedule.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

   // 유저 생성
    @PostMapping
    public ResponseEntity<CreateUserResponseDto> saveUser(@RequestBody CreateUserRequestDto requestDto){
        CreateUserResponseDto createUserResponseDto =
                userService.saveUser(
                        requestDto.getUsername(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );
        return new ResponseEntity<>(createUserResponseDto, HttpStatus.CREATED);
    }
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
    ) {
        userService.login(requestDto, request);
        return ResponseEntity.ok(new LoginResponseDto("로그인 성공"));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("로그아웃 성공");
    }


    // 전체 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        List<UserResponseDto> userResponseDtoList = userService.findAll();
        return new ResponseEntity<>(userResponseDtoList,HttpStatus.OK);
    }

    // 유저 단 건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        UserResponseDto userResponseDto = userService.findById(id);
        return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateUserResponseDto> update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto requestDto
    ){
        UpdateUserResponseDto updateUserResponseDto = userService.updateUser(id, requestDto);
        return new ResponseEntity<>(updateUserResponseDto, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }



}
