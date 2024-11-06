package ua.kusakabe.opencvcamprocessing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.opencv.core.Core;

import java.io.IOException;

public class VideoProcessing extends Application {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VideoProcessing.class.getResource("video-output.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Video processing!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
