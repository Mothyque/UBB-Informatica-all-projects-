package com.ubb.controller;

import com.ubb.domain.Person;
import com.ubb.service.PersonService;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.StreamSupport;

public class PersonController extends PagingController<Person> {
    @FXML private TableColumn<Person, Integer> idColumn;
    @FXML private TableColumn<Person, String> firstNameColumn;
    @FXML private TableColumn<Person, String> lastNameColumn;
    @FXML private TableColumn<Person, String> usernameColumn;
    @FXML private TableColumn<Person, String> emailColumn;
    @FXML private TableColumn<Person, String> birthDateColumn;
    @FXML private TableColumn<Person, String> occupationColumn;
    @FXML private TableColumn<Person, Integer> empathyColumn;

    @FXML private TextField txtId;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private DatePicker datePickerBirth;
    @FXML private TextField txtOccupation;
    @FXML private TextField txtEmpathyLevel;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    protected void handleAddAction() {
        if (service instanceof PersonService personService) {
            try
            {
                int id = Integer.parseInt(txtId.getText());
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String username = txtUsername.getText();
                String email = txtEmail.getText();
                String password = txtPassword.getText();
                LocalDate birthDate = datePickerBirth.getValue();
                String occupation = txtOccupation.getText();
                int empathyLevel = Integer.parseInt(txtEmpathyLevel.getText());
                if(birthDate == null)
                {
                    throw new IllegalArgumentException("Birth date must be selected.");
                }
                String dataFormatted = birthDate.format(dateFormatter);
                personService.addPerson(id, username, email, password, firstName, lastName, dataFormatted, occupation, empathyLevel);
                clearInputs();
                showInfo("Person added successfully.");
            }
            catch (Exception ex)
            {
                showAlert("Error: " + ex.getMessage());
            }
        }
    }

    @Override
    protected void handleDeleteAction(Person itemToDelete)
    {
        if(service instanceof PersonService personService)
        {
            try
            {
                personService.deletePerson(itemToDelete.getId());
                showInfo("Person deleted successfully.");
            }
            catch(Exception ex)
            {
                showAlert("Error: " + ex.getMessage());
            }
        }
    }


    private void clearInputs()
    {
        txtId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
        datePickerBirth.setValue(null);
        txtOccupation.clear();
        txtEmpathyLevel.clear();
    }

    @Override
    protected void initModel()
    {
        Pageable pageable = new Pageable(currentPage, pageSize);
        Page<Person> personPage = service.findAllOnPage(pageable);
        totalNumberOfElements = personPage.getTotalElements();
        List<Person> list = StreamSupport.stream(personPage.getElementsOnPage().spliterator(), false)
                .toList();

        model.setAll(list);
        updatePageInfo();
    }

    @Override
    protected void initializeTableColumns()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        birthDateColumn.setCellValueFactory(cellData ->
        {
            String rawDate = cellData.getValue().getBirthDate();
            if(rawDate == null || rawDate.isEmpty())
            {
                return new SimpleStringProperty("");
            }
            else
            {
                LocalDate date = LocalDate.parse(rawDate, dateFormatter);
                String formattedDate = date.format(dateFormatter);
                return new SimpleStringProperty(formattedDate);
            }
        });
        occupationColumn.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        empathyColumn.setCellValueFactory(new PropertyValueFactory<>("empathyLevel"));
    }
}