package cloudade.server.auth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cloudade.server.auth.mongo.user.MongoUserDetailsManager;
import cloudade.server.auth.mongo.user.domain.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired private MongoUserDetailsManager mongoUserDetailsManager;

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public UserDetails init() {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		authList.add(new SimpleGrantedAuthority("OAUTH_ADMIN"));
		

		User user = new User(null, null, "admin", "admin", authList, true, true, true, true);
		mongoUserDetailsManager.createUser(user);

		return user;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public UserDetails create(@ModelAttribute UserForm userForm) {

		UserDetails user = new User(null, null, userForm.getUsername(), userForm.getPassword(), userForm.getAuthorities(), true, true, true, true);
		mongoUserDetailsManager.createUser(user);

		return user;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public UserDetails retrieve(@PathVariable("username") String username) {

		UserDetails user = mongoUserDetailsManager.loadUserByUsername(username);

		return user;
	}

	public class UserForm {

		private String password;
		private String username;
		private Set<GrantedAuthority> authorities;

		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getUsername() {
			return username;
		}
		public Set<GrantedAuthority> getAuthorities() {
			return authorities;
		}


	}
}
