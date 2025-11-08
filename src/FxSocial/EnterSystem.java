package FxSocial;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EnterSystem {
	boolean columnsAdded = false;
	static TableView<Object> tableView1 = new TableView<>();
	ObservableList<Object> tableData = FXCollections.observableArrayList();
	Alerts a = new Alerts();
	LinkedList s = Main.sharedList;
	TextField t1;
	IconButton b2, b3, b4, b5, b6;

	public void Search(String search) {
		ObservableList<Object> results = FXCollections.observableArrayList();
		if (search == null || search.trim().isEmpty()) {
			a.ErrorAlert("Error", "Please enter ID or Name to Search");
			return;
		}
		boolean Num = true;
		for (int i = 0; i < search.length(); i++) {
			if (!Character.isDigit(search.charAt(i))) {
				Num = false;
				break;
			}
		}
		for (int j = 0; j < s.getSize(); j++) {
			User current = (User) tableData.get(j);
			if (Num && current.getUserId() == Integer.parseInt(search)) {
				results.add(current);
			} else if (current.getName().equalsIgnoreCase(search)) {
				results.add(current);
			}
		}
		if (results.isEmpty()) {
			a.ErrorAlert("Error", "No User Found with this ID or Name");
		} else {
			tableView1.setItems(results);
		}
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		if (!columnsAdded) {
			TableColumn<Object, Integer> idCol = new TableColumn<>("UserID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

			TableColumn<Object, String> nCol = new TableColumn<>("Name");
			nCol.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Object, Integer> ageCol = new TableColumn<>("Age");
			ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

			TableColumn<Object, String> frCol = new TableColumn<>("Number of Friends");
			frCol.setCellValueFactory(new PropertyValueFactory<>("friendsCount"));

			TableColumn<Object, Integer> poCol = new TableColumn<>("Number of Posts Created");
			poCol.setCellValueFactory(new PropertyValueFactory<>("postsCount"));

			TableColumn<Object, Integer> posCol = new TableColumn<>("Number of Posts Shared");
			posCol.setCellValueFactory(new PropertyValueFactory<>("postsSharedCount"));

			tableView1.getColumns().addAll(idCol, nCol, ageCol, frCol, poCol, posCol);
			tableView1.setStyle("-fx-background-color: white;" + "-fx-border-color: transparent;"
					+ "-fx-table-cell-border-color: transparent;" + "-fx-font-family: 'Segoe UI';"
					+ "-fx-font-size: 14px;");
			tableView1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tableView1.setItems(tableData);
			columnsAdded = true;
		}

		HBox hb1 = new HBox(10);
		IconButton b = new IconButton("Search", "/FxSocial/icons8-search-50.png");
		b.setOnAction(x -> {
			Search(t1.getText());

		});
		b.setPrefHeight(35);
		b.setPrefWidth(130);
		t1 = new TextField();
		t1.setStyle("-fx-background-color: white;" + "-fx-border-color: #6a11cb;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 14px;"
				+ "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;" + "-fx-text-fill: #333;"
				+ "-fx-padding: 14 4 14 18;");
		t1.setPrefWidth(150);
		t1.setPromptText("Search here ..");
		hb1.getChildren().addAll(b, t1);
		hb1.setAlignment(Pos.CENTER);

		VBox vb1 = new VBox(15);
		IconButton b0 = new IconButton("Refresh", "/FxSocial/icons8-refresh-50.png");
		b0.setMaxSize(365, 365);
		b0.setOnAction(x -> {
			if (tableData.isEmpty()) {
				a.ErrorAlert("Error", "No Data in Table to Refresh");
			} else {
				tableView1.setItems(tableData);
			}
		});
		IconButton b1 = new IconButton("Add User", "/FxSocial/icons8-add-50.png");
		b1.setOnAction(x -> {
			AddUser a = new AddUser();
			a.Display();
		});
		b1.setMaxSize(365, 365);

		IconButton b2 = new IconButton("Update User", "/FxSocial/icons8-update-64.png");
		b2.setDisable(true);
		b2.setOnAction(x -> {
			int selected = tableView1.getSelectionModel().getSelectedIndex();
			if (selected >= 0) {
				Update u = new Update(tableData, selected);
				u.Display();
			}
		});
		b2.setMaxSize(365, 365);

		tableView1.setOnMouseClicked(e -> {
			if (tableView1.getSelectionModel().getSelectedIndex() >= 0) {
				b2.setDisable(false);
				b3.setDisable(false);
				b4.setDisable(false);
				b5.setDisable(false);
				b6.setDisable(false);
			}
		});

		b3 = new IconButton("Remove User", "/FxSocial/icons8-remove-60.png");
		b3.setDisable(true);
		b3.setOnAction(x -> {
			int selected = tableView1.getSelectionModel().getSelectedIndex();
			if (selected >= 0) {
				Delete d = new Delete(tableData, selected);
				d.Display();
			}
			if (tableData.isEmpty()) {
				b2.setDisable(true);
				b3.setDisable(true);
				b4.setDisable(true);
				b5.setDisable(true);
				b6.setDisable(true);
			}

		});
		b3.setMaxSize(365, 365);

		b4 = new IconButton("Posts Management", "/FxSocial/icons8-post-53.png");
		b4.setDisable(true);
		b4.setOnAction(x -> {
			int selectedIndex = tableView1.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				User selectedUser = (User) tableData.get(selectedIndex);
				PostM pm = new PostM(selectedUser, selectedIndex);
				pm.Display();
			}
		});
		b4.setMaxSize(365, 365);

		b5 = new IconButton("FriendShips Management", "/FxSocial/icons8-crowd-50.png");
		b5.setDisable(true);
		b5.setOnAction(x -> {
			int selectedIndex = tableView1.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				User selectedUser = (User) tableData.get(selectedIndex);
				FriendShipsM f = new FriendShipsM(selectedUser);
				f.Display();
			}
		});
		b5.setMaxSize(365, 365);

		b6 = new IconButton("Most Active", "/FxSocial/icons8-check-mark-50.png");
		b6.setDisable(true);
		b6.setOnAction(x -> {
			MostActive ma = new MostActive();
			ma.Display();
		});

		b6.setMaxSize(365, 365);

		vb1.getChildren().addAll(hb1, b0, b1, b2, b3, b4, b5, b6);
		vb1.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		bp.setTop(m);
		bp.setCenter(tableView1);
		bp.setRight(vb1);

		Scene scene = new Scene(bp, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/FxSocial/icons8-social-64.png"));
		stage.show();
	}
}
