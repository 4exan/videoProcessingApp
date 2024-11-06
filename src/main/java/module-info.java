module ua.kusakabe.opencvcamprocessing {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;


    opens ua.kusakabe.opencvcamprocessing to javafx.fxml;
    exports ua.kusakabe.opencvcamprocessing;

    opens ua.kusakabe.opencvcamprocessing.controller to javafx.fxml;
    exports ua.kusakabe.opencvcamprocessing.controller;
}