package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.AbstractController;
import org.example.controller.MainController;
import org.example.domain.Class;
import org.example.domain.Professor;
import org.example.domain.Student;
import org.example.repository.ClassRepository;
import org.example.repository.ProfessorRepository;
import org.example.repository.StudentRepository;
import org.example.service.Service;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        String url = "jdbc:postgresql://localhost:5432/college";
        String username = "postgres";
        String password = "mathy";

        StudentRepository studentRepo = new StudentRepository(url, username, password);
        ProfessorRepository professorRepo = new ProfessorRepository(url, username, password);
        ClassRepository classRepo = new ClassRepository(url, username, password);

        Service<Integer, Student> studentService = new Service<>(studentRepo);
        Service<Integer, Professor> professorService = new Service<>(professorRepo);
        Service<Integer, Class> classService = new Service<>(classRepo);

        initWindow(studentService, professorService, classService);
    }

    private void initWindow(Service<Integer, Student> studentService, Service<Integer, Professor> professorService, Service<Integer, Class> classService) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        loader.load();
        MainController ctrl = loader.getController();
        ctrl.setServices(studentService, professorService, classService);
        Scene scene = new Scene(loader.getRoot());
        Stage stage = new Stage();
        stage.setTitle("College Management");
        stage.setScene(scene);
        stage.show();
    }
}