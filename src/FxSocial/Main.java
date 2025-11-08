package FxSocial;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	public static LinkedList sharedList = new LinkedList();
	public static FriendShipsM sharedListt = new FriendShipsM();
	public static PostM sharedListtt = new PostM();
	public static EnterSystem es = new EnterSystem();
	public static ViewPosts vp = new ViewPosts () ;
	public static Posts p = new Posts ();
	static Alerts a = new Alerts();

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) {
		BorderPane bp = new BorderPane();

		setbackGround(bp);

		MenuBar m = createmenuBar(stage);
		bp.setTop(m);

		VBox vb1 = vbox(stage);
		bp.setCenter(vb1);

		HBox hb = hbox(stage);
		bp.setBottom(hb);

		Scene scene = new Scene(bp, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/FxSocial/icons8-social-64.png"));
		stage.show();

	}

	public static MenuBar createmenuBar(Stage stage) {
		MenuBar m = new MenuBar();
		m.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);" + "-fx-background-insets: 0;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);" + "-fx-padding: 5 10 5 10;"
				+ "-fx-background-radius: 10;");

		Menu m1 = new Menu();
		style1(m1);
		MenuItem m11 = new MenuItem("Add User");
		m11.setOnAction(x -> {
			AddUser a = new AddUser();
			a.Display();
		});
		m11.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		m11.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		m1.getItems().add(m11);
		Image i1 = new Image("/FxSocial/icons8-add-user-50.png");
		ImageView iv1 = new ImageView(i1);
		m1.setGraphic(iv1);

		Menu m2 = new Menu("|");
		style1(m2);
		m2.setStyle("-fx-font-size: 38px; -fx-text-fill: black;");

		Menu m3 = new Menu();
		style1(m3);
		MenuItem m33 = new MenuItem("Display Statistics");
		m33.setOnAction(x -> {
			int selectedIndex = es.tableView1.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				User selectedUser = (User) es.tableData.get(selectedIndex);
				Report r = new Report(selectedUser, selectedIndex);
				r.Display();
			} else {
				a.ErrorAlert("Error", "Please Select User First");
			}
		});
		m33.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
		m33.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		m3.getItems().add(m33);
		Image i2 = new Image("/FxSocial/icons8-statistics-30.png");
		ImageView iv2 = new ImageView(i2);
		iv2.setFitHeight(50);
		iv2.setFitWidth(50);
		m3.setGraphic(iv2);

		Menu m4 = new Menu("|");
		style1(m4);
		m4.setStyle("-fx-font-size: 38px; -fx-text-fill: black;");

		Menu m5 = new Menu();
		style1(m5);
		MenuItem m55 = new MenuItem("Load Users");
		m55.setOnAction(x -> {
			LoadUsers l = new LoadUsers();
			l.Display();
		});
		m55.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));
		m55.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		MenuItem m66 = new MenuItem("Load Posts");
		m66.setOnAction(x -> {
			if (es.tableData.isEmpty()) {
				a.ErrorAlert("Error", "Please Uploade The Users First ..");
				return;
			}
			LoadPosts l = new LoadPosts();
			l.Display();
		});
		m66.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
		m66.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		MenuItem m88 = new MenuItem("Load Friends");
		m88.setOnAction(x -> {
			if (es.tableData.isEmpty()) {
				a.ErrorAlert("Error", "Please Uploade The Users First ..");
				return;
			}
			LoadFriends l = new LoadFriends();
			l.Display();
		});
		m88.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
		m88.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		m5.getItems().addAll(m55, m66, m88);
		Image i3 = new Image("/FxSocial/icons8-upload-50.png");
		ImageView iv3 = new ImageView(i3);
		m5.setGraphic(iv3);

		Menu m6 = new Menu("|");
		style1(m6);
		m6.setStyle("-fx-font-size: 38px; -fx-text-fill: black;");

		Menu m7 = new Menu();
		style1(m7);
		MenuItem m77 = new MenuItem("Sava Users Ascending");
		m77.setOnAction(x -> {
			if (es.tableData.isEmpty()) {
				a.ErrorAlert("Error", "Please Uploade The Users First ..");
				return;
			}
			SaveUsersAsc sa = new SaveUsersAsc(es.tableData);
			sa.Display();
		});
		m77.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		m77.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");

		MenuItem m10 = new MenuItem("Save Users Descending");
		m10.setOnAction(x -> {
			if (es.tableData.isEmpty()) {
				a.ErrorAlert("Error", "Please Uploade The Users First ..");
				return;
			}
			SaveUsersDesc sd = new SaveUsersDesc(es.tableData);
			sd.Display();
		});
		m10.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
		m10.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");

		MenuItem m12 = new MenuItem("Save Users");
		m12.setOnAction(x -> {
			if (es.tableData.isEmpty()) {
				a.ErrorAlert("Error", "Please Uploade The Users First ..");
				return;
			}
			SaveUsers u = new SaveUsers(es.tableData);
			u.Display();
		});
		m12.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		m12.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");

		MenuItem m13 = new MenuItem("Save Posts");
		m13.setOnAction(x -> {
			if (es.tableData.isEmpty()) {
				a.ErrorAlert("Error", "Please Uploade The Users First ..");
				return;
			}
			SavePosts p = new SavePosts(es.tableData);
			p.Display();
		});
		m13.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		m13.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");

		MenuItem m14 = new MenuItem("Save Friends");
		m14.setOnAction(x -> {
			if (es.tableData.isEmpty()) {
				a.ErrorAlert("Error", "Please Uploade The Users First ..");
				return;
			}
			SaveFriends u = new SaveFriends(es.tableData);
			u.Display();
		});
		m14.setAccelerator(KeyCombination.keyCombination("Ctrl+G"));
		m14.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");

		Image i4 = new Image("/FxSocial/icons8-save-50.png");
		ImageView iv4 = new ImageView(i4);
		m7.setGraphic(iv4);
		m7.getItems().addAll(m77, m10, m12, m13, m14);

		Menu m8 = new Menu("|");
		style1(m8);
		m8.setStyle("-fx-font-size: 38px; -fx-text-fill: black;");

		Menu m9 = new Menu();
		style1(m9);
		MenuItem m99 = new MenuItem("Close");
		m99.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		m99.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		m9.getItems().add(m99);
		Image i5 = new Image("/FxSocial/icons8-exit-50.png");
		ImageView iv5 = new ImageView(i5);
		m9.setGraphic(iv5);
		m9.setOnAction(x -> {
			stage.close();
		});

		m.getMenus().addAll(m1, m2, m3, m4, m5, m6, m7, m8, m9);
		return m;
	}

	public static VBox vbox(Stage stage) {
		VBox vb = new VBox(40);

		CustomLabel l1 = new CustomLabel("Welcome to Advanced Social Network System – COMP242");
		style2(l1);
		IconButton b1 = new IconButton("Enter to System", "/FxSocial/icons8-enter-50.png");
		b1.setOnAction(x -> {
			es.Display();
		});
		vb.getChildren().addAll(l1, b1);
		vb.setAlignment(Pos.CENTER);
		return vb;

	}

	public static HBox hbox(Stage stage) {
		HBox hb = new HBox(25);
		CustomLabel l1 = new CustomLabel(
				"We are Always looking to Improve our Social Network .. ! Share your Ideas  with us At  --> :");
		style2(l1);
		CustomLabel l2 = new CustomLabel("Elyasnajeh5@gmail.com");
		style2(l2);
		hb.getChildren().addAll(l1, l2);
		hb.setAlignment(Pos.CENTER);
		return hb;

	}

	public static void style1(Menu m) {
		m.setStyle("-fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);" + "-fx-text-fill: white;"
				+ "-fx-font-size: 26px;" + "-fx-font-family: 'Segoe UI', sans-serif;" + "-fx-font-weight: bold;"
				+ "-fx-padding: 18px 26px;" + "-fx-border-radius: 6px;" + "-fx-background-radius: 6px;"
				+ "-fx-border-color: transparent;" + "-fx-cursor: hand;");
	}

	public static void style2(Label l) {
		l.setStyle("-fx-text-fill: white;" + "-fx-font-size: 24px;" + "-fx-font-weight: bold;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.8), 5, 0, 0, 2);"
				+ "-fx-font-family: 'Arial Rounded MT Bold', sans-serif;" + "-fx-background-color: rgba(0, 0, 0, 0.7);"
				+ "-fx-padding: 5px 15px;" + "-fx-background-radius: 10px;");
	}

	public void setbackGround(BorderPane bp) {
		bp.setStyle("-fx-background-image: url('/FxSocial/Network.jpg');" + "-fx-background-repeat: no-repeat;"
				+ "-fx-background-position: center;" + "-fx-background-size: cover;");
	}

}
