package FxSocial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.collections.ObservableList;

public class SaveUsers {
	ObservableList<Object> userList;
	Alerts a = new Alerts();

	public SaveUsers(ObservableList<Object> userList) {
		this.userList = userList;
	}

	public void Display() {
		File file = new File("C:\\Users\\HP\\eclipse-workspace\\FxSocial\\updatedUsers.txt");
		try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
			for (Object o : userList) {
				User u = (User) o;
				writer.println(buildUserData(u));
			}
			a.InfoAlert("Success", "User data saved successfully!");
		} catch (IOException e) {
			a.ErrorAlert("Error", "Failed to save Users data.");
		}
	}

	private String buildUserData(User user) {
		return user.getUserId() + ", " + user.getName() + ", " + user.getAge();
	}

}
