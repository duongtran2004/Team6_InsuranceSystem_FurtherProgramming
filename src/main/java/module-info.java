module org.example.insurancemanagementapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.insurancemanagementapplication to javafx.fxml;
    exports org.example.insurancemanagementapplication;
}