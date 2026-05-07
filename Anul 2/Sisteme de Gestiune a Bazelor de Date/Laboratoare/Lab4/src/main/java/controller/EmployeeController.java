package controller;

import domain.Employee;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.builder.SpringApplicationBuilder;
import tests.Main;

import java.util.List;

public class EmployeeController extends Application {

    private ConfigurableApplicationContext springContext;
    private EntityManager em;

    private TableView<Employee> table = new TableView<>();
    private Label pageLabel = new Label("Page: 1");
    private Label totalLabel = new Label("Total: 0");
    private ComboBox<Integer> sizeSelector = new ComboBox<>();

    private int currentPage = 0;
    private int pageSize = 10;
    private long totalRecords = 0;

    @Override
    public void init()
    {
        if (tests.Main.ctx != null)
        {
            this.em = tests.Main.ctx.getBean(EntityManager.class);
        }
        else
        {
            throw new RuntimeException("Spring Context not found! Ensure Main.ctx is initialized.");
        }
    }

    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Task 3: Pagination Implementation (SQLite)");

        TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.getColumns().addAll(idCol, nameCol, emailCol);

        sizeSelector.setItems(FXCollections.observableArrayList(10, 25, 50, 100));
        sizeSelector.setValue(10);
        sizeSelector.setOnAction(e -> {
            pageSize = sizeSelector.getValue();
            currentPage = 0;
            refreshData();
        });

        Button prevBtn = new Button("Previous");
        prevBtn.setOnAction(e -> {
            if (currentPage > 0) {
                currentPage--;
                refreshData();
            }
        });

        Button nextBtn = new Button("Next");
        nextBtn.setOnAction(e -> {
            if ((currentPage + 1) * pageSize < totalRecords) {
                currentPage++;
                refreshData();
            }
        });

        HBox controls = new HBox(15, new Label("Rows:"), sizeSelector, prevBtn, nextBtn, pageLabel, totalLabel);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(table);
        root.setBottom(controls);

        refreshData(); // Initial load

        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    private void refreshData() {

        int offset = currentPage * pageSize;
        List<Employee> employees = em.createQuery("SELECT e FROM Employee e ORDER BY e.id", Employee.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();

        totalRecords = em.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult();

        table.setItems(FXCollections.observableArrayList(employees));
        pageLabel.setText("Page: " + (currentPage + 1));
        totalLabel.setText("Total Records: " + totalRecords);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}