package ua.kusakabe.opencvcamprocessing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class VideoOutputController {

    @FXML
    private ImageView imageOutput;

    @FXML
    private Button startCameraBtn;

    @FXML
    private Button stopCameraBtn;

    @FXML
    private Label actionLog;

    @FXML
    private Button defaultModeBtn;

    @FXML
    private Button faceRecognitionBtn;

    private boolean isCameraOn = false;
    Thread mainThread;
    Thread faceRecognitionThread;
    VideoCapture cam;
    WritableImage mainInput;
    WritableImage faceDetectionInput;
    Mat frame;

    private String currentOutput = "";

    @FXML
    void startCamera(ActionEvent event) {
        cam = new VideoCapture("/dev/video0");
        if(!cam.isOpened()){
            actionLog.setText("Camera not opened!");
        } else {
            actionLog.setText("Camera opened!");
        }

        isCameraOn = true;
        frame = new Mat();

        launchAllThreads();

    }

    private void launchAllThreads() {
        mainThread = new Thread(() -> {
            while (cam.isOpened()){
                if(cam.read(frame)){
                    mainInput = matToImage(frame);
                    imageOutput.setImage(mainInput);
                }
            }
        });

        faceRecognitionThread = new Thread(() -> {
            while (cam.isOpened()){
                if(cam.read(frame)){
                    faceDetectionInput = faceDetection(frame);
                    imageOutput.setImage(faceDetectionInput);
                }
            }
        });
    }


    @FXML
    public void startDefaultMode(ActionEvent event) {
        mainThread.start();
        currentOutput = "default";
        actionLog.setText("Default mode");
    }

    @FXML
    public void startFaceRecognition(ActionEvent event) {
        faceRecognitionThread.start();
        currentOutput = "faceRecognition";
        actionLog.setText("Face recognition mode");
    }

    public WritableImage faceDetection(Mat frame){
        CascadeClassifier faceDetector = new CascadeClassifier("src/main/resources/ua/kusakabe/opencvcamprocessing/detection/haarcascade_frontalface_default.xml");
        MatOfRect faceDetection = new MatOfRect();
        faceDetector.detectMultiScale(frame, faceDetection);
        for(Rect rect : faceDetection.toArray()){
            Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 3);
        }
        return matToImage(frame);
    }

    private WritableImage matToImage(Mat frame) {
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
        WritableImage image = new WritableImage(frame.width(), frame.height());
        PixelWriter pw = image.getPixelWriter();
        byte[] buffer = new byte[(int)(frame.total()) * frame.channels()];
        frame.get(0, 0, buffer);

        for (int y = 0; y < frame.height(); y++) {
            for (int x = 0; x < frame.width(); x++) {
                int index = (y * frame.width() + x) * frame.channels();
                int r = buffer[index] & 0xFF;
                int g = buffer[index + 1] & 0xFF;
                int b = buffer[index + 2] & 0xFF;
                pw.setArgb(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return image;
    }

    @FXML
    void stopCamera(ActionEvent event) {
        isCameraOn = false;
        cam.release();
        mainThread.interrupt();
        faceRecognitionThread.interrupt();
        actionLog.setText("Camera stopped!");
    }

}
