package in.saurabh.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.saurabh.entity.User;
import in.saurabh.payload.UserRequest;
import in.saurabh.payload.UserResponse;
import in.saurabh.service.UserService;
import in.saurabh.utils.JwtUtils;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService service;
	
	@Autowired
	private JwtUtils util;
	
	


   

   @PostMapping("/save")
public ResponseEntity<String> saveUser(@RequestBody User user)
	{
	 service.saveUser(user);
		return ResponseEntity.ok("User Created");
	}
	
   @PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest )
	{
	   System.out.println(userRequest.getUsername());
	   System.out.println(userRequest.getPassword());
	   authenticationManager.authenticate(
			   new UsernamePasswordAuthenticationToken(
			   userRequest.getUsername(),
			   userRequest.getPassword()));
	   System.out.println("Good");
	   
	   String token =util.generateToken(userRequest.getUsername());
		return ResponseEntity.ok(new UserResponse(token,"Genrated BY Mr.SAURABH"));
	}
   
   @PostMapping("/welcome")
   public ResponseEntity<String> showUserData(Principal p)
   	{
   	
   		return ResponseEntity.ok("Hello :"   + p.getName());
   	}
}
