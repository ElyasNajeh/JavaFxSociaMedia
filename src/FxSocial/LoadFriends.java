package FxSocial;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadFriends {
	Alerts a = new Alerts();
	LinkedList s = Main.sharedList;
	PostM p = Main.sharedListtt;
	FriendShipsM fs = Main.sharedListt;
	EnterSystem es = Main.es;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Friends File");
		fc.setInitialDirectory(new File("C:\\Users\\HP\\eclipse-workspace\\FxSocial"));
		Stage stage = new Stage();
		File f = fc.showOpenDialog(stage);
		if (f == null) {
			a.ErrorAlert("Error", "No file selected. Please select a file.");
			return;
		}

		try (Scanner scanner = new Scanner(f)) {

			fs.friendsData.clear();

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] data = line.split(",");
				try {
					User current1 = null;
					User current2 = null;
					int userId = Integer.parseInt(data[0].trim());
					for (int i = 0; i < s.getSize(); i++) {

						current1 = (User) es.tableData.get(i);
						if (current1.getUserId() == userId) {
							break;
						}
					}

					for (int j = 1; j < data.length; j++) {
						int friendId = Integer.parseInt(data[j]);
						for (int k = 0; k < s.getSize(); k++) {
							current2 = (User) es.tableData.get(k);
							if (current2.getUserId() == friendId) {
								current1.addFriend(current2);
								break;
							}
						}
					}

				} catch (Exception e) {
					a.ErrorAlert("Error", "Cannot read this line:" + line);
				}
			}
			if (Main.es != null && Main.es.tableView1 != null) {
				Main.es.tableView1.refresh();
			}
			a.InfoAlert("Success", "File read Successfully!");
		} catch (IOException e) {
			a.ErrorAlert("Error", "Error while reading the file.");
		}
	}
}
