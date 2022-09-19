import com.example.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests
{
	String name = "Aaron";
	String email = "aaron@example.com";
	Set<User.Role> roles = new HashSet<>(Arrays.asList(User.Role.USER, User.Role.ADMIN));
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
		""";

		System.out.println(expectedJson);
		JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.LENIENT);

		DocumentContext jsonPath = JsonPath.parse(json);
		String jsonName = jsonPath.read("$.name");
		String jsonEmail = jsonPath.read("$.email");
		String jsonRoles = jsonPath.read("$.roles").toString();
		String jsonDateCreated = jsonPath.read("$.dateCreated");

		System.out.println("jsonName: " + jsonName);
		System.out.println("jsonEmail: " + jsonEmail);
		System.out.println("jsonRoles: " + jsonRoles);
		System.out.println("jsonDateCreated: " + jsonDateCreated);

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		assertThat(jsonName).isEqualTo(name);
		assertThat(jsonEmail).isEqualTo(email);
		assertThat(jsonRoles).contains(User.Role.USER.toString());
		assertThat(jsonRoles).contains(User.Role.ADMIN.name());
		assertThat(jsonDateCreated).isEqualTo(dateCreated.format(dateFormatter));
	}
}
