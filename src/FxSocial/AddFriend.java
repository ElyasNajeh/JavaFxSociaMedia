package FxSocial;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddFriend {
	boolean columnsAdded = false;
	CustomTextField t1;
	Alerts a = new Alerts();
	AddUser ad = new AddUser();
	FriendShipsM friend;
	EnterSystem es = Main.es;
	LinkedList s = Main.sharedList;
	TableView<Object> tableView3;
	ObservableList<Object> copyList;

	public AddFriend(FriendShipsM friend) {
		this.friend = friend;
		this.tableView3 = new TableView<>();
		this.copyList = FXCollections.observableArrayList(es.tableData);
	}

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
			User current = (User) copyList.get(j);
			if (Num && current.getUserId() == Integer.parseInt(search)) {
				results.add(current);
			} else if (current.getName().equalsIgnoreCase(search)) {
				results.add(current);
			}
		}
		if (results.isEmpty()) {
			a.ErrorAlert("Error", "No User Found with this ID or Name");
		} else {
			tableView3.setItems(results);
		}
	}

	public boolean uniqueFriend() {
		User selectedFriend = (User) tableView3.getSelectionModel().getSelectedItem();
		LinkedList friends = friend.selectedUser.getFriend();
		for (int i = 0; i < friends.getSize(); i++) {
			User current = (User) friend.friendsData.get(i);
			if (current.getUserId() == selectedFriend.getUserId()) {
				a.ErrorAlert("Error", "This User is Already Friend, Sorry !");
				return true;
			}
		}
		return false;

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

			TableColumn<Object, String> frCol = new TableColumn<>("Friends");
			frCol.setCellValueFactory(new PropertyValueFactory<>("friendsCount"));

			TableColumn<Object, Integer> poCol = new TableColumn<>("Posts");
			poCol.setCellValueFactory(new PropertyValueFactory<>("postsCount"));

			tableView3.getColumns().addAll(idCol, nCol, ageCol, frCol, poCol);
			columnsAdded = true;

		}
		tableView3.setStyle("-fx-background-color: white;" + "-fx-border-color: transparent;"
				+ "-fx-table-cell-border-color: transparent;" + "-fx-font-family: 'Segoe UI';"
				+ "-fx-font-size: 14px;");
		tableView3.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.tableView3.setItems(copyList);

		HBox hb1 = new HBox(40);
		IconButton b1 = new IconButton("Back", "/FxSocial/icons8-back-50.png");
		b1.setOnAction(x -> {
			stage.close();
		});
		IconButton b = new IconButton("Search", "/FxSocial/icons8-search-50.png");
		b.setOnAction(x -> {
			Search(t1.getText());

		});
		t1 = new CustomTextField();
		t1.setStyle("-fx-background-color: white;" + "-fx-border-color: #6a11cb;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 14px;"
				+ "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;" + "-fx-text-fill: #333;"
				+ "-fx-padding: 14 4 14 18;");
		t1.setPromptText("Search here ..");

		IconButton b2 = new IconButton("Add Friend", "/FxSocial/icons8-add-50.png");
		b2.setOnAction(x -> {

			if (tableView3.getSelectionModel().getSelectedItem() == null) {
				a.ErrorAlert("Error", "Please select a User to add as a Friend");
				return;
			}
			User u1 = (User) tableView3.getSelectionModel().getSelectedItem();

			boolean Valid = uniqueFriend();
			if (Valid) {
				return;
			}
			if (friend.selectedUser.getUserId() == u1.getUserId()) {
				a.ErrorAlert("Error", "Cannot Add yourself as a Friend!");
				return;
			}

			boolean confirmed = a.ConfiramtionAlert("Confirmation",
					"Are you sure you want to add this Friend to the User?");
			if (!confirmed) {
				return;
			}

			friend.selectedUser.addFriend(u1);
			friend.loadFriends(friend.selectedUser);

			a.InfoAlert("Success", "Friend Added Succesfully, Thanks!");
			Main.es.tableView1.refresh();

		});
		IconButton b3 = new IconButton("Refresh", "/FxSocial/icons8-refresh-50.png");
		b3.setOnAction(x -> {
			this.tableView3.setItems(copyList);
		});
		hb1.getChildren().addAll(b1, b, t1, b2, b3);
		hb1.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();

		bp.setTop(m);
		bp.setCenter(tableView3);
		bp.setBottom(hb1);

		Scene scene = new Scene(bp, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/FxSocial/icons8-social-64.png"));
		stage.show();
	}

}
