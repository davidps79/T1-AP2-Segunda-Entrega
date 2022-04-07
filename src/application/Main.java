package application;
	
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import controller.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.CinemaSystem;
import model.Room;
import model.Show;
import model.Spectator;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	private CinemaSystem backend;
	private Stage currentStage;
	private PrintWriter writer;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Cine Icesi");
		primaryStage.getIcons().add(new Image("resources/appLogo.png"));
		primaryStage.setOnCloseRequest(event -> {
			try {
				File file = new File(".\\files\\data.txt");
	        	FileOutputStream fos = new FileOutputStream(file);
		        ObjectOutputStream oos = new ObjectOutputStream(fos);
		        oos.writeObject(backend.getShowsData2());
		        oos.close();
		        fos.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		});
		
		 try {
			 backend = new CinemaSystem();
			 File file = new File(".\\files\\data.txt");
			 if(!file.exists()) {
	        	FileOutputStream fos = new FileOutputStream(file);
		        ObjectOutputStream oos = new ObjectOutputStream(fos);
		        
		        oos.writeObject(backend.getShowsData2());
		        oos.close();
		        fos.close();
			 }
		        
		    }
		    catch (IOException ioe) {
		    	ioe.printStackTrace();
		    }
		 
		try {
			backend.addAdmin("12", "12");
			backend.addAdmin("1006352045", "QuieroEstudiarGastroMejor");
			backend.addAdmin("1107875251", "Holamundo");
			
			saveAdmins();
			backend.loadAdmins();
			currentStage = primaryStage;
			openLogin();
			currentStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void openLogin() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/Login.fxml"));
			BorderPane root = (BorderPane) loader.load();
			LoginController controller = loader.getController();
			controller.setMain(this);
			Scene scene = new Scene(root,1008,720);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			currentStage.setScene(scene);
		} catch (Exception e) {
		}
	}
	
	public void openRegistry() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/Registry.fxml"));
			BorderPane root = (BorderPane) loader.load();
			RegistryController controller = loader.getController();
			controller.setMain(this);
			controller.loadShowData();
			Scene scene = new Scene(root,1008,720);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAdmins()
	    {  
        try {
	        File file = new File(".\\files\\doNotTouchMe.txt");
	        FileOutputStream fos = new FileOutputStream(file);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        
	        oos.writeObject(backend.getAdmins());
	        oos.close();
	        fos.close();
	    }
	    catch (IOException ioe) {
	    	ioe.printStackTrace();
	    }
	}

	public boolean authenticateAdmin(String id, String password) {
		return backend.authenticateAdmin(id, password);
	}

	public ObservableList<Show> getShowsData() {
		return backend.getShowsData();
	}
	
	public boolean addShow(String movieName, int movieDuration, LocalDate date, int startHour, int startMinute, String preRoom) {
		return backend.addShow(movieName, movieDuration, date, startHour, startMinute, preRoom);
	}

	public void openAccomodation(Room room) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/Accomodation.fxml"));
			BorderPane root = (BorderPane) loader.load();
			AccomodationController controller = loader.getController();
			controller.setMain(this);
			controller.loadData(room);
			Scene scene = new Scene(root,1008,720);
			scene.getStylesheets().add(getClass().getResource("../ui/application.css").toExternalForm());
			currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean selectSeat(Room room, Integer columnIndex, Integer rowIndex) {
		return backend.selectSeat(room, columnIndex, rowIndex);
	}
	
	public void addSpectator(Room room, String name, String id, int column, int row){
        room.addSpectator(new Spectator(name, id), column, row);
    }
	
	 public void writeReport() {
	        try {
	            writer = new PrintWriter(".\\files\\Report" + backend.getReportCounter() + ".txt");	
	            writer.println(backend.toString());
	            writer.close();;
	            backend.addToReportCounter();
	            Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("El reporte se ha agregado en la carpeta files con el nombre Report" + (backend.getReportCounter()-1));
				alert.showAndWait();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
}
