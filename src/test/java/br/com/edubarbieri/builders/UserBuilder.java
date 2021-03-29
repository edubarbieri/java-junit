package br.com.edubarbieri.builders;

import br.com.edubarbieri.entities.User;

public class UserBuilder {

	private User user;
	
	private UserBuilder() {}
	
	public static UserBuilder oneUser() {
		UserBuilder builder = new UserBuilder();
		builder.user = new User();
		builder.user.setName("Test user");
		return builder;
	}
	
	public UserBuilder withName(String name) {
		user.setName(name);
		return this;
	}
	
	public User build() {
		return user;
	}
	
}
