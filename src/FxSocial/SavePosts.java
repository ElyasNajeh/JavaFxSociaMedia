package FxSocial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.collections.ObservableList;

public class SavePosts {
	EnterSystem es = Main.es;
	PostM pm = Main.sharedListtt;
	ObservableList<Object> postList;
	Alerts a = new Alerts();

	public SavePosts(ObservableList<Object> postList) {
		this.postList = postList;
	}

	public void Display() {
		File file = new File("C:\\Users\\HP\\eclipse-workspace\\FxSocial\\updatedPosts.txt");
		try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
			for (Object o : postList) {
				User u = (User) o;
				writer.println(buildFriendData(u));
			}
			a.InfoAlert("Success", "Posts data saved successfully!");
		} catch (IOException e) {
			a.ErrorAlert("Error", "Failed to save Posts data.");
		}
	}

	private String buildFriendData(User user) {
		StringBuilder s = new StringBuilder();
		Posts p = null;
		s.append(user.getUserId());
		for (int i = 0; i < user.getPostCreated().getSize(); i++) {
			p = (Posts) user.getPostCreated().get(i);
			s.append(", ").append(p.getPostId());
			s.append(", ").append(p.getContent());
			s.append(", ").append(p.getCreationDate());
		}
		return s.toString();
	}
}
