module de.neuefische.frontendstudentdb {
    exports de.neuefische.frontendstudentdb.student to com.fasterxml.jackson.databind;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires lombok;


    opens de.neuefische.frontendstudentdb to javafx.fxml;
    exports de.neuefische.frontendstudentdb;
}
