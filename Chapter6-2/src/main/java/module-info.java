module se233.chapter2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.commons.io;
    requires org.json;
    requires org.apache.logging.log4j;


    opens se233.chapter2 to javafx.fxml;
    exports se233.chapter2;
}