package FxSocial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.collections.ObservableList;

public class SaveFriends {
	FriendShipsM fs = Main.sharedListt;
	EnterSystem es = Main.es;
	ObservableList<Object> friendList;
	Alerts a = new Alerts();

	public SaveFriends(ObservableList<Object> friendList) {
		this.friendList = friendList;
	}

	public void Display() {
		File file = new File("C:\\Users\\HP\\eclipse-workspace\\FxSocial\\updatedFiends.txt");
		try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
			for (Object o : friendList) {
				User u = (User) o;
				writer.println(buildFriendData(u));
			}
			a.InfoAlert("Success", "Friends data saved successfully!");
		} catch (IOException e) {
			a.ErrorAlert("Error", "Failed to save Friends data.");
		}
	}

	private String buildFriendData(User user) {
		StringBuilder s = new StringBuilder();
		s.append(user.getUserId());
		for (int i = 0; i < user.getFriend().getSize(); i++) {
			User friend = (User) user.getFriend().get(i);
			s.append(", ").append(friend.getUserId());
		}
		return s.toString();
	}
}
