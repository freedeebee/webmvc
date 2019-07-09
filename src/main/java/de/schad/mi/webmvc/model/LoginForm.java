package de.schad.mi.webmvc.model;

/**
 * LoginForm Model to represent the form input for Login
 */
public class LoginForm {

	private String name;
	private String password;

	public LoginForm(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}