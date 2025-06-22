package booking.system.user.service.service;

import booking.system.user.service.exception.UserException;
import booking.system.user.service.modal.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUser(Long id) throws UserException;
    User updateUser(Long id,User user) throws UserException;
    User getUserFromJwt(String jwt) throws Exception;
}
