package org.example.rest.services;

import org.example.rest.repository.UserRepository;

public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Boolean login(Long userId, String pswrd) {
    return userRepository.login(userId, pswrd);
  }
  public Boolean logout(Long userId) {
    return userRepository.logout(userId);
  }
}
