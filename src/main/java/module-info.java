module com.example.factorysimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.factorysimulation to javafx.fxml;
    exports com.example.factorysimulation;
}