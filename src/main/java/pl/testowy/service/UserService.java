package pl.testowy.service;

import pl.testowy.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);

}
