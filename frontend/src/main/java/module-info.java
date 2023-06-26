module de.neuefische.frontendstudentdb {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens de.neuefische.frontendstudentdb to javafx.fxml;
    exports de.neuefische.frontendstudentdb;
}