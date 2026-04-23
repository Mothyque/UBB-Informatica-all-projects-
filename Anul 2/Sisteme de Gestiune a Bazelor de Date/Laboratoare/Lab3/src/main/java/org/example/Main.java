package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.MainController;
import org.example.domain.CollegeClass;
import org.example.domain.Professor;
import org.example.domain.Student;
import org.example.repository.HibernateRepository;
import org.example.repository.Repository;
import org.example.service.Service;
import org.example.utils.HibernateUtil;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Repository<Integer, Student> studentRepo = new HibernateRepository<>(Student.class);
        Repository<Integer, CollegeClass> classRepo = new HibernateRepository<>(CollegeClass.class);
        Repository<Integer, Professor> professorRepo = new HibernateRepository<>(Professor.class);

        Service<Integer, Student> studentService = new Service<>(studentRepo);
        Service<Integer, Professor> professorService = new Service<>(professorRepo);
        Service<Integer, CollegeClass> classService = new Service<>(classRepo);

        initWindow(studentService, professorService, classService);
    }

    private void initWindow(Service<Integer, Student> studentService, Service<Integer, Professor> professorService, Service<Integer, CollegeClass> classService) throws Exception
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

    @Override
    public void stop() throws Exception
    {
        HibernateUtil.shutdown();
        super.stop();
    }
}