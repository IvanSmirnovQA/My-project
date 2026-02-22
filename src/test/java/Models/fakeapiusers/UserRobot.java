package Models.fakeapiusers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRobot{

	@JsonProperty("password")
	private String password;

	@JsonProperty("address")
	private Address address;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("__v")
	private int v;

	@JsonProperty("name")
	private Name name;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("email")
	private String email;

	@JsonProperty("username")
	private String username;
}