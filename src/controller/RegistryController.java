package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import application.Main;
import exception.EmptyFieldException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Show;

public class RegistryController {
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	Button cancelButton;
	
	@FXML
	void cancel() {
		main.openLogin();
	}
	
	@FXML
	private ComboBox<String> movieRoomInput;
	
	@FXML
    private DatePicker movieDateInput;

    @FXML
    private TextField movieDurationInput;

    @FXML
    private TextField movieHourInput;

    @FXML
    private TextField movieMinuteInput;

    @FXML
    private TextField movieNameInput;
	
	@FXML
	private TableView<Show> showTable;

	@FXML
    private TableColumn<Show,String> showNameColumn;

    @FXML
    private TableColumn<Show,String> showRoomColumn;
    
    @FXML
    private TableColumn<Show, LocalDate> showDateColumn;
    
    @FXML
    private  TableColumn<Show,LocalTime> showStartColumn;
    
    @FXML
    private TableColumn<Show, LocalTime> showEndColumn;
    
	@FXML
	private Button openAccomodationButton;
	
	@FXML
	void openAccomodation() {
		Show show = showTable.getSelectionModel().getSelectedItem();
		if (show != null) {
			main.openAccomodation(show.getRoom());
		}
	}
    
    private ObservableList<String> roomOptions = FXCollections.observableArrayList("Sala Mini", "Sala Mediana");
    
    @FXML
	public void initialize() {
    	movieDateInput.setFocusTraversable(false);
    	showTable.setFocusTraversable(false);
		movieDurationInput.setFocusTraversable(false);
		movieHourInput.setFocusTraversable(false);
		movieMinuteInput.setFocusTraversable(false);
		movieNameInput.setFocusTraversable(false);
		showDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		showNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
	    showRoomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
	    showStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
	    showEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
		movieRoomInput.setItems(roomOptions);
		movieDateInput.setValue(LocalDate.now());             
    }
    
    @FXML
    void registerShow() {
        try {
        	if (movieNameInput.getText().equals("")) {
                throw new EmptyFieldException("El nombre de la pelicula está vacío");
            }
        	
            if (movieDurationInput.getText().equals("")) {
                throw new EmptyFieldException("La duración de la pelicula está vacía");
            }
            
            if (movieHourInput.getText().equals("")) {
                throw new EmptyFieldException("La hora de inicio de la pelicula está vacía");
            }
            
            if (movieMinuteInput.getText().equals("")) {
                throw new EmptyFieldException("La hora de inicio de la pelicula está incompleta");
            }
            
            if (movieRoomInput.getValue()==null) {
                throw new EmptyFieldException("Debe seleccionar una sala para la función");
            }
            
            if (main.addShow(movieNameInput.getText(), Integer.parseInt(movieDurationInput.getText()), movieDateInput.getValue(), Integer.parseInt(movieHourInput.getText()), Integer.parseInt(movieMinuteInput.getText()),  movieRoomInput.getValue())) {
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Operación exitosa");
	            alert.setHeaderText(null);
	            alert.setContentText("Se ha agregado la función");
	            alert.showAndWait();
	            movieRoomInput.setValue(null);;
				movieDateInput.setValue(LocalDate.now());
				movieNameInput.setText(null);
				movieDurationInput.setText(null);
				movieHourInput.setText(null);
				movieMinuteInput.setText(null);
            } else {
            	Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Espacio ocupado");
                alert.setHeaderText(null);
                alert.setContentText("La sala no está disponible en ese horario");
                alert.showAndWait();
            }
        } catch(EmptyFieldException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campo vacío");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    public void loadShowData() {
    	showTable.setItems(main.getShowsData());
    }
}
