package FxSocial;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadPosts {
	Alerts a = new Alerts();
	LinkedList s = Main.sharedList;
	PostM p = Main.sharedListtt;
	EnterSystem es = Main.es ;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Posts File");
		fc.setInitialDirectory(new File("C:\\Users\\HP\\eclipse-workspace\\FxSocial"));
		Stage stage = new Stage();
		File f = fc.showOpenDialog(stage);
		if (f == null) {
			a.ErrorAlert("Error", "No file selected. Please select a file.");
			return;
		}

		try (Scanner scanner = new Scanner(f)) {

			p.postData.clear();

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] data = line.split(",");

				try {
					int userId = Integer.parseInt(data[0].trim());
					int postId = Integer.parseInt(data[1].trim());
					String content = data[2].trim();
					String date = data[3].trim();
					for (int i = 0; i < s.getSize(); i++) {
						User current = (User) es.tableData.get(i);
						if (current.getUserId() == userId) {
							Posts p = new Posts(postId, current, content, date);
							current.createPost(p);
							break;
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
