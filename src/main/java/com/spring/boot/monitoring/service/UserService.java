package com.spring.boot.monitoring.service;

import com.spring.boot.monitoring.model.entity.User;
import com.spring.boot.monitoring.model.request.UserCreateRequest;
import com.spring.boot.monitoring.model.response.UserResponse;
import com.spring.boot.monitoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final String ORDER_OF_ONE_USER_FALLBACK_ID = "ORDER_OF_ONE_USER";

    @Transactional
    public UserResponse create(UserCreateRequest userCreateRequest) {
        User user = User.create(
                userCreateRequest.getEmail(),
                userCreateRequest.getLoginId(),
                userCreateRequest.getPassword(),
                userCreateRequest.getName()
        );

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public UserResponse readUser(Long userId) {
        return UserResponse.from(userRepository.findById(userId).orElse(null));
    }

    public List<UserResponse> readAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)//Entity > Dto
                .toList();
    }
}
