module lk.ijse.gdse.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires static lombok;
    requires mysql.connector.j;
    requires java.desktop;
    requires net.sf.jasperreports.core;
    requires java.mail;


    opens lk.ijse.gdse.demo.controller to javafx.fxml;
    opens lk.ijse.gdse.demo.dto to javafx.base;
    exports lk.ijse.gdse.demo.util;

}