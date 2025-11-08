package FxSocial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MostActive implements Comparator<User> {
	EnterSystem es = Main.es;
	PostM pm = Main.sharedListtt;
	TextArea t1, t2;
	Alerts a = new Alerts();
	List<User> users = new ArrayList<>();

	public void style(TextArea t) {
		t.setStyle("-fx-background-color: #1e1e1e;" + "-fx-text-fill: #000000;" + "-fx-font-size: 14px;"
				+ "-fx-font-family: 'Calibri', 'Segoe UI', sans-serif;" + "-fx-border-color: #3a3a3a;"
				+ "-fx-border-width: 1.5;" + "-fx-border-radius: 8;" + "-fx-background-radius: 8;" + "-fx-padding: 10;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.1, 0, 2);" + "-fx-opacity: 1;");

		t.setEditable(false);
		t.setMaxWidth(680);
		t.setPrefHeight(600);
	}

	public void MostActiveUsers1() {
		for (Object o : es.tableData) {
			User user = (User) o;
			if (user.getPostsCount() > 0) {
				users.add((User) o);
			}
		}
		Collections.sort(users, this);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Math.min(5, users.size()); i++) {
			User u = users.get(i);
			sb.append(u.getName()).append(" ,Number of Posts ").append(u.getPostsCount()).append("\n\n");
		}
		t1.setText(sb.toString());

	}

	public void MostActiveUsers2() {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, -21);
		Date threeWeeksAgo = cal.getTime();

		for (Object obj : es.tableData) {
			User u = (User) obj;
			LinkedList posts = u.getPostCreated();

			for (int i = 0; i < posts.getSize(); i++) {
				Posts p = (Posts) posts.get(i);
				try {
					Date postDate = sdf.parse(p.getCreationDate());
					if (!postDate.before(threeWeeksAgo) && !postDate.after(today)) {
						sb.append(u.getName()).append("\n\n");
						break;
					}
				} catch (Exception e) {
					a.ErrorAlert("Error", e.getMessage());
				}
			}
		}

		t2.setText(sb.toString());
	}

	public void Display() {

		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		HBox hb1 = new HBox();

		IconButton b1 = new IconButton("Back", "/FxSocial/icons8-back-50.png");
		b1.setOnAction(x -> {
			stage.close();
		});
		b1.setMaxSize(365, 365);

		hb1.getChildren().add(b1);
		hb1.setAlignment(Pos.CENTER);
		hb1.setPadding(new Insets(20, 0, 0, 0));

		TabPane tabPane = new TabPane();
		tabPane.setStyle("-fx-background-color: transparent;" + "-fx-tab-background-color: white;"
				+ "-fx-text-base-color: black;");

		tabPane.setTabMinWidth(200);

		t1 = new TextArea();
		t2 = new TextArea();

		style(t1);
		style(t2);

		Tab tab1 = new Tab("Top Users by Posts Count", t1);
		tab1.setStyle("-fx-background-color: white; -fx-text-fill: black;");

		Tab tab2 = new Tab("Users Who Shared Posts in Last 3 Weeks", t2);
		tab2.setStyle("-fx-background-color: white; -fx-text-fill: black;");

		tabPane.getTabs().addAll(tab1, tab2);

		MostActiveUsers1();
		MostActiveUsers2();

		BorderPane bp = new BorderPane();

		Main m1 = new Main();
		m1.setbackGround(bp);

		bp.setTop(m);
		bp.setCenter(tabPane);
		bp.setBottom(hb1);

		Scene scene = new Scene(bp, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/FxSocial/icons8-social-64.png"));
		stage.show();

	}

	@Override
	public int compare(User o1, User o2) {
		return Integer.compare(o2.getPostsCount(), o1.getPostsCount());
	}
}
