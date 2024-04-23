module org.example.insurancemanagementapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens org.example.insurancemanagementapplication to javafx.fxml;
    exports org.example.insurancemanagementapplication;
}