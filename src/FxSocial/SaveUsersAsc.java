package FxSocial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.collections.ObservableList;

public class SaveUsersAsc implements Comparator<User> {
	ObservableList<Object> userList;
	Alerts a = new Alerts();

	public SaveUsersAsc(ObservableList<Object> userList) {
		this.userList = userList;
	}

	public void Display() {
		File file = new File("C:\\Users\\HP\\eclipse-workspace\\FxSocial\\Users Ascending.txt");
		try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
			 List<User> users = new ArrayList<>();
			 for (Object o : userList) {
				 users.add((User)o);
			 }
			Collections.sort(users, this);
			for (User u  : users) {
				writer.println(buildUserData(u));
			}
			a.InfoAlert("Success", "User data Ascending saved successfully!");
		} catch (IOException e) {
			a.ErrorAlert("Error", "Failed to save Users data.");
		}
	}

	private String buildUserData(User user) {
		return user.getUserId() + ", " + user.getName() + ", " + user.getAge();
	}

	@Override
	public int compare(User o1, User o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
