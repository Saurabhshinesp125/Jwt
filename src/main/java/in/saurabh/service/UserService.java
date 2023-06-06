package in.saurabh.service;

import in.saurabh.entity.User;

public interface UserService {

	
	Integer saveUser(User user);
	
	User findByUsername(String username);
}
