package in.saurabh.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

	
	private String token;
	private String note;
	 
}
