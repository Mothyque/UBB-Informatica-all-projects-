module com.ubb.main {

    requires javafx.controls;
    requires javafx.fxml;

    requires atlantafx.base;

    requires java.sql;
    requires org.postgresql.jdbc;
    requires javafx.base;
    requires java.desktop;
    exports com.ubb;


    opens com.ubb to javafx.fxml;

    opens com.ubb.controller to javafx.fxml;

    opens com.ubb.domain.duck to javafx.base;
    opens com.ubb.domain.event to javafx.base;
    opens com.ubb.domain.flock to javafx.base;
    opens com.ubb.domain to javafx.base;
    opens com.ubb.domain.friendship to javafx.base;
}