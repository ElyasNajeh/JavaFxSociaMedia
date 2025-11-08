package FxSocial;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadUsers {
	Alerts a = new Alerts();
	LinkedList s = Main.sharedList;
	EnterSystem es = Main.es;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Users File");
		fc.setInitialDirectory(new File("C:\\Users\\HP\\eclipse-workspace\\FxSocial"));
		Stage stage = new Stage();
		File f = fc.showOpenDialog(stage);
		if (f == null) {
			a.ErrorAlert("Error", "No file selected. Please select a file.");
			return;
		}

		try (Scanner scanner = new Scanner(f)) {

			s.clear();

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] data = line.split(",");

				try {
					int UserId = Integer.parseInt(data[0].trim());
					String Name = data[1].trim();
					int Age = Integer.parseInt(data[2].trim());

					User u = new User(UserId, Name, Age);
					s.addLast(u);
					es.tableData.add(u);

				} catch (Exception e) {
					a.ErrorAlert("Error", "Cannot read this line:" + line);
				}
			}

			a.InfoAlert("Success", "File read Successfully!");

			if (Main.es != null && Main.es.tableView1 != null) {
				Main.es.tableView1.refresh();
			}
		} catch (IOException e) {
			a.ErrorAlert("Error", "Error while reading the file.");
		}
	}
}
