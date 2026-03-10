package scs.map.sem9_paging.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import scs.map.sem9_paging.domain.Movie;

import scs.map.sem9_paging.observer.Observer;
import scs.map.sem9_paging.service.MovieService;
import scs.map.sem9_paging.util.event.EntityChangeEvent;
import scs.map.sem9_paging.util.event.EntityChangeEventType;
import scs.map.sem9_paging.util.paging.Page;
import scs.map.sem9_paging.util.paging.Pageable;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieController implements Observer<EntityChangeEvent> {
    private MovieService movieService;
    private ObservableList<Movie> model = FXCollections.observableArrayList();
    @FXML
    private TableView<Movie> tableView;
    @FXML
    private TableColumn<Movie, Long> tableColumnId;
    @FXML
    private TableColumn<Movie, String> tableColumnTitle;
    @FXML
    private TableColumn<Movie, String> tableColumnDirector;
    @FXML
    private TableColumn<Movie, Integer> tableColumnYear;
    @FXML
    private Button buttonNext;
    @FXML
    private Button buttonPrevious;
    @FXML
    private Label labelPage;
    @FXML
    private ComboBox<Integer> comboBoxfilterYear;
    @FXML
    private TextField textFieldfilterYearAfter;
    @FXML
    private TextField textFieldfilterDirector;
    @FXML
    private TextField textFieldfilterTitle;
    
    private int pageSize = 2;
    private int currentPage = 0;
    private int totalNumberOfElements = 0;



    public void setMovieService(MovieService movieService)
    {
        this.movieService = movieService;
        movieService.addObserver(this);
        initYearsCombo();
        initModel();
    }

    private void initYearsCombo()
    {
        int currentYear = java.time.Year.now().getValue();
        List<Integer> years = java.util.stream.IntStream.rangeClosed(1900, currentYear)
                .boxed()
                .toList();
        comboBoxfilterYear.getItems().addAll(years);
    }

    @FXML
    public void initialize()
    {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Movie, Long>("id"));
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        tableColumnDirector.setCellValueFactory(new PropertyValueFactory<Movie, String>("director"));
        tableColumnYear.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        tableView.setItems(model);
    }



    private void initModel()
    {
        Pageable pageable = new Pageable(currentPage, pageSize);
        Page<Movie> moviePage = movieService.findAllOnPage(pageable);
        totalNumberOfElements = moviePage.getTotalNumberOfElements();
        List<Movie> movies = StreamSupport.stream(moviePage.getElementsOnPage().spliterator(),
                false).collect(Collectors.toList());
        model.setAll(movies);
        handlePaginationControls();
    }

    public void onDelete(ActionEvent actionEvent) {
        Movie selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Optional<Movie> deleted = movieService.delete(selected.getId());
            if (deleted.isPresent()) {
                //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Movie has been deleted");
                // initModel();
            }
        } else {
            MessageAlert.showErrorMessage(null, "Select a movie first!");
        }
    }

    @Override
    public void update(EntityChangeEvent event) {
        if (event.getType() == EntityChangeEventType.DELETE) {
            currentPage = 0;
            initModel();
        }

    }

    public void onNextPage(ActionEvent actionEvent) {
        currentPage ++;
        initModel();
    }

    public void onPreviousPage(ActionEvent actionEvent) {
        currentPage --;
        initModel();
    }

    private void handlePaginationControls() {
        int totalPages = (int) Math.ceil((double) totalNumberOfElements / pageSize);
        labelPage.setText("Page " + (currentPage + 1) + " of " + totalPages);
        buttonPrevious.setDisable(currentPage == 0);
        buttonNext.setDisable(currentPage >= totalPages - 1);
        labelPage.setText("Page " + (currentPage + 1) + " of " + totalPages);
    }
}


