package org.example.schedule.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.example.schedule.entity.UserEntity;
import org.example.schedule.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponseDto saveUser(String username,String email, String password){

        UserEntity userEntity = new UserEntity(username, email, password);

        UserEntity user = userRepository.save(userEntity);

        return new CreateUserResponseDto(user.getId(),user.getUsername(),user.getEmail(),user.getCreatedAt(),user.getModifiedAt());

    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    public UserResponseDto findById(Long id) {
        Optional<UserEntity> userRepositoryById = userRepository.findById(id);

        if (userRepositoryById.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id : "+ id);
        }

        UserEntity userEntity = userRepositoryById.get();
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getCreatedAt(),
                userEntity.getModifiedAt());
    }

    @Transactional
    public UpdateUserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        UserEntity user = userRepository.findByIdOrElseThrow(id);

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.updateUserInfo(requestDto.getUsername(), requestDto.getEmail());

        return new UpdateUserResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    public void delete(Long id) {
        UserEntity user = userRepository.findByIdOrElseThrow(id);
        userRepository.delete(user);

    }

    @Transactional
    public void login(LoginRequestDto requestDto, HttpServletRequest request) {
        UserEntity user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }


    /**
     * 로그아웃
     * @session.invalidate(); : 세션 삭제 (로그아웃)
     * */
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
