import com.example.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class User_Tests
{
	String name = "Aaron";
	String email = "aaron@example.com";
	Set<User.Role> roles = new HashSet<User.Role>(Arrays.asList(User.Role.USER, User.Role.ADMIN));
	LocalDate dateCreated = LocalDate.now();

	@Test
	public void can_create_user()
	{
		User user = new User(name, email, roles, dateCreated);
		System.out.println(user);

		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getRoles().size()).isEqualTo(2);
		assertThat(user.getDateCreated()).isEqualTo(dateCreated);
	}

	@Test
	public void can_convert_user_to_json() throws JsonProcessingException, JSONException
	{
		User user = new User(name, email, roles, dateCreated);

		String json = user.toJson();
		System.out.println(json);

		assertThat(json).isNotEmpty();

		String expectedJson = """
   		{
			"name" : "Aaron",
			"email" : "aaron@example.com",
			"dateCreated" : "2022-09-18",
			"roles" : [ "ADMIN", "USER" ]
		}
		""".trim();

		JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.LENIENT);
	}

}
