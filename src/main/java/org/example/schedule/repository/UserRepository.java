package org.example.schedule.repository;

import org.example.schedule.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    default UserEntity findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Does not exist id : "+ id));
    }

    Optional<UserEntity> findByEmail(String email);
}
