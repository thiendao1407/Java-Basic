package entity;

public class User {
	private String user_name;
	private String password;
	private Permission permission;

	public enum Permission {
		reader, admin;

		public static Permission getPermission(String permission) {
			switch (permission) {
			case "admin":
				return admin;
			case "reader":
				return reader;
			default:
				throw new IllegalArgumentException("Invalid permission" + permission);
			}
		}

	}

	public boolean isReader() {
		return this.permission == Permission.reader;
	}

	public boolean isAdmin() {
		return this.permission == Permission.admin;
	}

	public User(String user_name, String password, Permission permission) {
		this.user_name = user_name;
		this.password = password;
		this.setPermission(permission);
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "Username: " + this.user_name + ", Permission: " + this.permission;
	}
}
