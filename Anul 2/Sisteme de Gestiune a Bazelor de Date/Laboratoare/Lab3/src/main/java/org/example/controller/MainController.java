package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.domain.CollegeClass;
import org.example.domain.Professor;
import org.example.domain.Student;
import org.example.domain.exception.ValidationException;
import org.example.service.Service;

import java.util.Optional;

/**
 * Controller-ul principal al interfeței grafice (JavaFX).
 * Gestionează interacțiunile utilizatorului cu tabelele, formularele de adăugare/modificare
 * și se asigură că vizualizarea este sincronizată cu baza de date prin intermediul Service-urilor.
 */
public class MainController extends AbstractController
{
    private Service<Integer, Student> studentService;
    private Service<Integer, Professor> professorService;
    private Service<Integer, CollegeClass> classService;

    // Reține profesorul selectat curent pentru a realiza relația Master-Detail (Părinte-Copil)
    private Professor selectedProfessor;

    // Modele de date observabile (ObservableList) care populează automat tabelele vizuale
    private final ObservableList<Student> studentsModel = FXCollections.observableArrayList();
    private final ObservableList<Professor> professorsModel = FXCollections.observableArrayList();
    private final ObservableList<CollegeClass> classesModel = FXCollections.observableArrayList();

    @FXML
    TableView<Student> tblStudents;
    @FXML
    TableView<Professor> tblProfessors;
    @FXML
    TableView<CollegeClass> tblClasses;

    @FXML
    TextField txtNameStudent;
    @FXML
    TextField txtAgeStudent;
    @FXML
    TextField txtNameProfessor;
    @FXML
    TextField txtAgeProfessor;
    @FXML
    TextField txtNameClass;
    @FXML
    TextField txtCreditsClass;

    @FXML
    Button btnAddStudent;
    @FXML
    Button btnAddProfessor;
    @FXML
    Button btnAddClass;
    @FXML
    Button btnDeleteStudent;
    @FXML
    Button btnDeleteProfessor;
    @FXML
    Button btnDeleteClass;
    @FXML
    Button btnUpdateStudent;
    @FXML
    Button btnUpdateProfessor;
    @FXML
    Button btnUpdateClass;
    @FXML
    Button btnRefresh;

    /**
     * Injectează instanțele de Service și înregistrează controller-ul ca Observer.
     * Orice modificare în baza de date declanșată de Service va notifica acest controller.
     */
    public void setServices(Service<Integer, Student> studentService, Service<Integer, Professor> professorService, Service<Integer, CollegeClass> classService)
    {
        this.studentService = studentService;
        this.professorService = professorService;
        this.classService = classService;

        studentService.addObserver(this);
        professorService.addObserver(this);
        classService.addObserver(this);

        initData();
    }

    /**
     * Metodă declanșată de pattern-ul Observer când datele se modifică.
     */
    @Override
    public void update()
    {
        initData();
    }

    /**
     * Inițializează coloanele tabelelor (dacă nu au fost deja create) folosind Reflection (PropertyValueFactory)
     * și încarcă toate datele din baza de date în interfața grafică.
     */
    @Override
    public void initData()
    {
        tblStudents.setItems(studentsModel);
        tblProfessors.setItems(professorsModel);
        tblClasses.setItems(classesModel);

        Iterable<Student> students = studentService.findAll();
        Iterable<Professor> professors = professorService.findAll();

        if(tblStudents.getColumns().isEmpty())
        {
            TableColumn<Student, String> nameColStudent = new TableColumn<>("Name");
            nameColStudent.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Student, Integer> ageColStudent = new TableColumn<>("Age");
            ageColStudent.setCellValueFactory(new PropertyValueFactory<>("age"));

            tblStudents.getColumns().addAll(nameColStudent, ageColStudent);
        }

        if(tblProfessors.getColumns().isEmpty())
        {
            TableColumn<Professor, String> nameColProfessor = new TableColumn<>("Name");
            nameColProfessor.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Professor, Integer> ageColProfessor = new TableColumn<>("Age");
            ageColProfessor.setCellValueFactory(new PropertyValueFactory<>("age"));

            tblProfessors.getColumns().addAll(nameColProfessor, ageColProfessor);
        }

        if(tblClasses.getColumns().isEmpty())
        {
            TableColumn<CollegeClass, String> nameColClass = new TableColumn<>("Name");
            nameColClass.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<CollegeClass, Integer> creditsColClass = new TableColumn<>("Credits");
            creditsColClass.setCellValueFactory(new PropertyValueFactory<>("credits"));

            tblClasses.getColumns().addAll(nameColClass, creditsColClass);
        }

        studentsModel.clear();
        professorsModel.clear();
        classesModel.clear();

        students.forEach(studentsModel::add);
        professors.forEach(professorsModel::add);
        refreshClasses();

    }

    /**
     * Extrage datele din interfață, le validează și adaugă un nou Student prin intermediul Service-ului.
     */
    @FXML
    public void onAddStudent()
    {
        try
        {
            String name = txtNameStudent.getText();
            String ageText = txtAgeStudent.getText();
            validateNameAndAge(name, ageText);
            Student student = new Student(name, Integer.parseInt(ageText));
            studentService.add(student);
            clearInputFields();
            showInfoMessage("Student added successfully.");
        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Extrage datele din interfață, le validează și adaugă un nou Profesor.
     */
    @FXML
    public void onAddProfessor()
    {
        try
        {
            String name = txtNameProfessor.getText();
            String ageText = txtAgeProfessor.getText();
            validateNameAndAge(name, ageText);
            Professor professor = new Professor(name, Integer.parseInt(ageText));
            professorService.add(professor);
            clearInputFields();
            showInfoMessage("Professor added successfully.");
        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Adaugă o nouă materie (Copil) asociată profesorului (Părinte) selectat.
     */
    @FXML
    public void onAddClass()
    {
        try
        {
            String name = txtNameClass.getText();
            String creditsText = txtCreditsClass.getText();
            validateNameAndCredits(name, creditsText);
            CollegeClass cls = new CollegeClass(name, Integer.parseInt(creditsText), selectedProfessor);
            classService.add(cls);
            showInfoMessage("CollegeClass added successfully.");
        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Șterge studentul selectat curent, solicitând în prealabil confirmarea utilizatorului.
     */
    @FXML
    public void onDeleteStudent()
    {
        try
        {
            Student selected = tblStudents.getSelectionModel().getSelectedItem();
            if(selected != null)
            {
                Optional<ButtonType> result = confirmDeletion();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                    studentService.delete(selected.getId());
                    showInfoMessage("Student deleted successfully.");
                }
            }
            else
            {
                throw new ValidationException("Please select a student to delete.");
            }
        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteProfessor()
    {
        try
        {
            Professor selected = tblProfessors.getSelectionModel().getSelectedItem();
            if(selected != null)
            {
                Optional<ButtonType> result = confirmDeletion();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                    professorService.delete(selected.getId());
                    showInfoMessage("Professor deleted successfully.");
                }
            }
            else
            {
                throw new ValidationException("Please select a professor to delete.");
            }
        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteClass()
    {
        try
        {
            CollegeClass selected = tblClasses.getSelectionModel().getSelectedItem();
            if(selected != null)
            {
                Optional<ButtonType> result = confirmDeletion();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                    classService.delete(selected.getId());
                    showInfoMessage("CollegeClass deleted successfully.");
                }
            }
            else
            {
                throw new ValidationException("Please select a class to delete.");
            }
        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Actualizează detaliile unui student existent cu datele introduse în câmpurile text.
     */
    @FXML
    public void onUpdateStudent()
    {
        try
        {
            Student selected = tblStudents.getSelectionModel().getSelectedItem();
            if(selected != null)
            {
                String name = txtNameStudent.getText();
                String ageText = txtAgeStudent.getText();
                validateNameAndAge(name, ageText);
                selected.setName(name);
                selected.setAge(Integer.parseInt(ageText));
                studentService.update(selected);
                showInfoMessage("Student updated successfully.");
            }
            else
            {
                throw new ValidationException("Please select a student to update.");
            }
        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    public void onUpdateProfessor()
    {
        try
        {
            Professor selected = tblProfessors.getSelectionModel().getSelectedItem();
            if(selected != null)
            {
                String name = txtNameProfessor.getText();
                String ageText = txtAgeProfessor.getText();
                validateNameAndAge(name, ageText);
                selected.setName(name);
                selected.setAge(Integer.parseInt(ageText));
                professorService.update(selected);
                showInfoMessage("Professor updated successfully.");
            }
            else
            {
                throw new ValidationException("Please select a professor to update.");
            }

        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    public void onUpdateClass()
    {
        try
        {
            CollegeClass selected = tblClasses.getSelectionModel().getSelectedItem();
            if(selected != null)
            {
                String name = txtNameClass.getText();
                String creditsText = txtCreditsClass.getText();
                validateNameAndCredits(name, creditsText);
                selected.setName(name);
                selected.setCredits(Integer.parseInt(creditsText));
                selected.setProfessor(selectedProfessor);
                classService.update(selected);
                showInfoMessage("CollegeClass updated successfully.");
            }
            else
            {
                throw new ValidationException("Please select a class to update.");
            }

        }
        catch(ValidationException e)
        {
            showErrorMessage(e.getMessage());
        }
        catch(Exception e)
        {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Declanșată la selectarea unui profesor în tabel.
     * Actualizează starea selecției și reîncarcă materiile corespunzătoare.
     */
    @FXML
    public void onSelectedProfessor()
    {
        selectedProfessor = tblProfessors.getSelectionModel().getSelectedItem();
        refreshClasses();
    }

    /**
     * Implementează vizualizarea Master-Detail.
     * Filtrează și afișează în tabelul de materii doar cursurile predate de profesorul selectat.
     */
    private void refreshClasses()
    {
        classesModel.clear();
        if(selectedProfessor != null)
        {
            Iterable<CollegeClass> classes = classService.findAll();
            classes.forEach(cls -> {
                if(cls.getProfessor() != null && cls.getProfessor().getId().equals(selectedProfessor.getId()))
                {
                    classesModel.add(cls);
                }
            });
        }
    }

    /**
     * Golește câmpurile de text din interfață după o operație reușită.
     */
    private void clearInputFields()
    {
        txtNameStudent.clear();
        txtAgeStudent.clear();
        txtNameProfessor.clear();
        txtAgeProfessor.clear();
    }

    /**
     * Validează regulile de business specifice pentru entitățile Student și Profesor (ex: vârstă pozitivă).
     * @throws ValidationException dacă datele sunt invalide.
     */
    private void validateNameAndAge(String name, String ageText) throws ValidationException
    {
        if(name == null || name.isEmpty())
        {
            throw new ValidationException("Name cannot be empty.");
        }
        if(ageText == null || ageText.isEmpty())
        {
            throw new ValidationException("Age cannot be empty.");
        }
        int age;
        try
        {
            age = Integer.parseInt(ageText);
        }
        catch(NumberFormatException e)
        {
            throw new ValidationException("Age must be a valid integer.");
        }
        if(age < 0)
        {
            throw new ValidationException("Age cannot be negative.");
        }
    }

    /**
     * Validează regulile de business pentru entitatea CollegeClass (ex: credite între 0 și 30).
     * @throws ValidationException dacă datele sunt invalide.
     */
    private void validateNameAndCredits(String name, String creditsText) throws ValidationException
    {
        if(name == null || name.isEmpty())
        {
            throw new ValidationException("CollegeClass name cannot be empty.");
        }
        if(creditsText == null || creditsText.isEmpty())
        {
            throw new ValidationException("Credits cannot be empty.");
        }
        int credits;
        try
        {
            credits = Integer.parseInt(creditsText);
        }
        catch(NumberFormatException e)
        {
            throw new ValidationException("Credits must be a valid integer.");
        }
        if(credits < 0 || credits > 30)
        {
            throw new ValidationException("Credits should be between 1 and 30.");
        }
    }

    /**
     * Afișează un dialog pentru confirmarea ștergerii, asigurându-se că utilizatorul nu șterge date accidental.
     * @return Răspunsul utilizatorului (OK sau Cancel) sub formă de Optional.
     */
    private Optional<ButtonType> confirmDeletion()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this item?");
        alert.setContentText("This action cannot be undone.");

        return alert.showAndWait();
    }

    /**
     * Reîncarcă manual toate datele din baza de date și reîmprospătează interfața.
     */
    @FXML
    public void onRefresh()
    {
        initData();
        showInfoMessage("Data refreshed successfully.");
    }
}