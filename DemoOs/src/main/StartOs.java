package main;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
public class StartOs extends Application {
	private Stage rootStage;
    private BorderPane rootLayout;
    

    @Override
    public void start(Stage primaryStage) {
        this.rootStage = primaryStage;
        this.rootStage.setTitle("模拟操作系统");
        rootStage.setResizable(false);
        initUi();
    }

    /**
     * 加载Os.fxml
     */
    public void initUi() {
        try {
        	//将Os.fxml加载到rootLayout成员变量中
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartOs.class.getResource("/main/Os.fxml"));
            rootLayout = (BorderPane) loader.load();
            //用rootLayout初始化一个scene，放到stage上展示
            Scene scene = new Scene(rootLayout,1500,960);
            rootStage.setScene(scene);
            rootStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getRootStage() {
		return rootStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}



