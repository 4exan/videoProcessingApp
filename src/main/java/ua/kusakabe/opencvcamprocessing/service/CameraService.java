package ua.kusakabe.opencvcamprocessing.service;

import javafx.application.Platform;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class CameraService {

    private VideoCapture cam;

//    public void startCamera(){
//        cam = new VideoCapture(0);
//        if(!cam.isOpened()){
//            System.out.println("Camera not opened!");
//        }
//
//        new Thread(() -> {
//            Mat frame = new Mat();
//            while (cam.isOpened()){
//                if(cam.read(frame)){
//                    WritableImage image = matToImage(frame);
//                }
//            }
//        });
//    }
//
//    public void stopCamera(){
//        cam.release();
//    }

//    private WritableImage matToImage(Mat frame) {
////        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
////        WritableImage image = new WritableImage(frame.width(), frame.height());
////        PixelWriter pw = image.getPixelWriter();
////        byte[] buffer = new byte[(int)(frame.total()) * frame.channels()];
////        frame.get(0, 0, buffer);
////
////        for (int y = 0; y < frame.height(); y++) {
////            for (int x = 0; x < frame.width(); x++) {
////                int index = (y * frame.width() + x) * frame.channels();
////                int r = buffer[index] & 0xFF;
////                int g = buffer[index + 1] & 0xFF;
////                int b = buffer[index + 2] & 0xFF;
////                pw.setArgb(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
////            }
////        }
////        return image;
//    }

}
