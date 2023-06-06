package in.saurabh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.saurabh.entity.User;
import in.saurabh.repository.UserRepository;
import in.saurabh.service.UserService;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	
	private UserRepository repo;
	private BCryptPasswordEncoder encoder;
	
	
	
	

	public UserServiceImpl(UserRepository repo, BCryptPasswordEncoder encoder) {
		super();
		this.repo = repo;
		this.encoder = encoder;
	}

	@Override
	public Integer saveUser(User user) {
		String pwd=encoder.encode(user.getPassword());
		user.setPassword(pwd);
		return repo.save(user).getId();
	}

	@Override
	public User findByUsername(String username) {
		 java.util.Optional<User> opt = repo.findById(null);
		if(opt.isPresent())
		return opt.get();
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=findByUsername(username);
		if(null == user)
			throw new UsernameNotFoundException(username +"not exist");
		
		List<SimpleGrantedAuthority> list = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
	}

}
