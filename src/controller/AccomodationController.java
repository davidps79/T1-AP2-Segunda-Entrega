package controller;

import application.Main;
import exception.EmptyFieldException;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import model.Room;
import model.Seat;

public class AccomodationController {	
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	int selectedColumn = -1;
	int selectedRow = -1;
	
	@FXML
	private Button exportDataButton;
	
	@FXML
	private Button addSpectatorButton;

	@FXML
	private TextField identificationInput;

	@FXML
	private VBox mainContainer;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField rowInput;

    @FXML
    private TextField columnInput;

    @FXML
    private Button returnToRegistryButton;
    
    @FXML
    public void returnToRegistry() {
    	main.openRegistry();
    }
    
	GridPane gridPane = new GridPane();
	
	Room selectedRoom = null;
	
	Button selectedSeat = null;
	
	@FXML
	void initialize() {
		nameInput.setFocusTraversable(false);
		identificationInput.setFocusTraversable(false);
		rowInput.setFocusTraversable(false);
		columnInput.setFocusTraversable(false);
	}
	
	@FXML
    void addSpectator() {
		try {
            if (nameInput.getText().equals("")) {
                throw new EmptyFieldException("El nombre del espectador está vacío");
            }

            if (identificationInput.getText().equals("")) {
                throw new EmptyFieldException("La identificación del espectador está vacía");
            }
            
            if (rowInput.getText().equals("") || columnInput.getText().equals("")) {
                throw new EmptyFieldException("Debe seleccionar una ubicación en la sala");
            }
            
			if (selectedRow!=-1) {
				main.addSpectator(selectedRoom, nameInput.getText(), identificationInput.getText(), selectedColumn, selectedRow);
				Image img = new Image("/resources/occupiedSeat.png");
				ImageView view = new ImageView(img);
				view.setFitHeight(36);
				view.setFitWidth(51);
				selectedSeat.setGraphic(view);
				identificationInput.setText(null);
				nameInput.setText(null);
				rowInput.setText(null);
				columnInput.setText(null);
				selectedColumn = -1;
				selectedRow = -1;
				selectedSeat = null;
			}
		} catch(EmptyFieldException e) {
			
		}
    }
	
    public void loadData(Room room){
    	selectedRoom = room;
    	Seat[][] seats = room.getSeats();
		int adjust = 400;
		if (seats[0].length==4) {
			adjust = 270;
		} 
		
		mainContainer.setMaxSize(520, adjust);
		gridPane.setMinSize(520, adjust);
		gridPane.setMaxSize(520, adjust);
		gridPane.setPrefSize(520, adjust);
		
		for (int i = 0; i < 7; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHalignment(HPos.CENTER);
            colConst.setPercentWidth(100.0 / 7);
            gridPane.getColumnConstraints().add(colConst);
        }
		
        for (int i = 0; i < seats[0].length; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setValignment(VPos.CENTER);
            rowConst.setPercentHeight(100.0 / seats[0].length);
            gridPane.getRowConstraints().add(rowConst);         
        }
        
        for (int i=0; i<7; i++) {
           for (int j=0; j<seats[0].length; j++) {
               Button seatButton = new Button("");
               seatButton.setId("seat");
               seatButton.setMinSize(51, 36);
               
               seatButton.setOnAction(e -> {
            	   selectedRow = GridPane.getRowIndex((Node) e.getTarget());
            	   selectedColumn = GridPane.getColumnIndex((Node) e.getTarget());
            	   if (main.selectSeat(room, selectedColumn, selectedRow)) {
            		   if (selectedSeat != null) {
            			   Image img2 = new Image("/resources/availableSeat.png");
                		   ImageView view2 = new ImageView(img2);
                		   selectedSeat.setGraphic(view2);
            		   }
            		   rowInput.setText("Fila " + (selectedRow+1));
            		   columnInput.setText("Columna " + (selectedColumn+1));
            		   selectedSeat = (Button) e.getTarget();
            		   Image img = new Image("/resources/selectedSeat.png");
       				   ImageView view = new ImageView(img);
       				   view.setFitHeight(36);
       				   view.setFitWidth(51);
       				   selectedSeat.setGraphic(view);
            	   } else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setHeaderText(null);
						alert.setContentText("Este asiento está ocupado");
						alert.showAndWait();		
            	   }
               });
               
               if (!seats[i][j].isAvailable()) {
            	   Image img = new Image("/resources/occupiedSeat.png");
            	   ImageView view = new ImageView(img);
            	   view.setFitHeight(36);
            	   view.setFitWidth(51);
            	   seatButton.setGraphic(view);
               } else{
            	   Image img = new Image("/resources/availableSeat.png");
            	   ImageView view = new ImageView(img);
            	   view.setFitHeight(36);
            	   view.setFitWidth(51);
            	   seatButton.setGraphic(view);
               }
				gridPane.add(seatButton, i, j);
				
           }
       }
       mainContainer.getChildren().add(gridPane);
   }	
    
    @FXML
	void exportData() {
		main.writeReport();
	}
}