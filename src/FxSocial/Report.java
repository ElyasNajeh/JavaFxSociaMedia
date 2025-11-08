package FxSocial;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Report {
	TextArea t1;
	User selectedUser;
	IconButton b1, b2, b3, b4, b5;
	int index;
	PostM pp = Main.sharedListtt;
	EnterSystem es = Main.es;
	Alerts a = new Alerts();

	public Report(User selectedUser, int index) {
		this.selectedUser = selectedUser;
		this.index = 0;
	}

	public void Display() {

		Stage stage = new Stage();
		MenuBar m = Main.createmenuBar(stage);

		HBox hb1 = new HBox(40);

		IconButton b1 = new IconButton("View Posts", "/FxSocial/icons8-view-50.png");
		b1.setOnAction(x -> {
			int selectedIndex = es.tableView1.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				User selectedUser = (User) es.tableData.get(selectedIndex);
				DisplayView d = new DisplayView(selectedUser, selectedIndex);
				d.Display();
			}
		});
		IconButton b2 = new IconButton("Back", "/FxSocial/icons8-back-50.png");
		b2.setOnAction(x -> {
			stage.close();
		});

		hb1.getChildren().addAll(b2, b1);
		hb1.setAlignment(Pos.CENTER);

		HBox hb2 = new HBox(30);
		hb2.setPadding(new Insets(20, 0, 0, 0));
		b3 = new IconButton("Next", "/FxSocial/icons8-next-50.png");
		pp.b4 = b3;
		b3.setOnAction(x -> {
			if (index < pp.postData.size() - 1) {
				index++;
				pp.index = index;
				pp.loadPost(selectedUser);
			}
		});

		b4 = new IconButton("Previous", "/FxSocial/icons8-previous-50.png");
		pp.b5 = b4;
		b4.setOnAction(x -> {
			if (index > 0) {
				index--;
				pp.index = index;
				pp.loadPost(selectedUser);
			}
		});
		b5 = new IconButton("Null", "/FxSocial/icons8-previous-50.png");
		pp.b3 = b5;
		hb2.getChildren().addAll(b4, b3);
		hb2.setAlignment(Pos.CENTER);

		VBox vb1 = new VBox(30);
		vb1.getChildren().addAll(hb2, hb1);
		vb1.setAlignment(Pos.CENTER);

		VBox vb2 = new VBox(20);
		CustomLabel l = new CustomLabel("My Posts!");
		t1 = new TextArea();
		pp.t1 = t1;
		t1.setStyle("-fx-background-color: #1e1e1e;" + "-fx-text-fill: #000000;" + "-fx-font-size: 14px;"
				+ "-fx-font-family: 'Calibri', 'Segoe UI', sans-serif;" + "-fx-border-color: #3a3a3a;"
				+ "-fx-border-width: 1.5;" + "-fx-border-radius: 8;" + "-fx-background-radius: 8;" + "-fx-padding: 10;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.1, 0, 2);" + "-fx-opacity: 1;");

		t1.setEditable(false);
		t1.setMaxWidth(680);
		t1.setPrefHeight(600);

		vb2.getChildren().addAll(l, t1);
		vb2.setAlignment(Pos.CENTER);

		pp.loadPost(selectedUser);

		BorderPane bp = new BorderPane();

		Main m1 = new Main();
		m1.setbackGround(bp);

		bp.setTop(m);
		bp.setCenter(vb2);
		bp.setBottom(vb1);

		Scene scene = new Scene(bp, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Social Media Program");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/FxSocial/icons8-social-64.png"));
		stage.show();
	}
}
