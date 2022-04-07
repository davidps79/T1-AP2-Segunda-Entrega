package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CinemaSystem implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Admin> admins;
	private ArrayList<Show> arrayShowsData;
	private transient ObservableList<Show> showsData;
	private int reportCounter;
	
	public int getReportCounter() {
		return reportCounter;
	}
	
	public CinemaSystem() {
		try {
			loadShows();
		} catch (ClassNotFoundException | IOException e) {
			arrayShowsData = new ArrayList<Show>();
			showsData = FXCollections.observableArrayList(arrayShowsData);	
		}
		admins = new ArrayList<>();
	}
	
	public void loadShows() throws IOException, ClassNotFoundException {
		File file = new File(".\\files\\data.txt");
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		@SuppressWarnings("unchecked")
		ArrayList<Show> obj = (ArrayList<Show>)ois.readObject();
		arrayShowsData = obj;
		showsData = FXCollections.observableArrayList(arrayShowsData);		
		ois.close();
		fis.close();
	}
	
	public List<Admin> getAdmins() {
		return admins;
	}

	public void addAdmin(String id, String password) {
		admins.add(new Admin(id, password));
	}
	
	public boolean theSpaceIsAvailable(LocalDate date, LocalTime start, LocalTime end, String preRoom) {
		boolean isAvailable = true;
		
		for (Show show : showsData) {
			if (preRoom.equals(show.getRoom().getId()) && date.equals(show.getDate())) {
				if (start.isBefore(show.getEnd()) && show.getStart().isBefore(end)){
					isAvailable = false;
					break;
				}
			}
		}
		
		return isAvailable;
	}
	
	public boolean addShow(String movieName, int movieDuration, LocalDate showDate, int startHour, int startMinute, String preRoom) {
		LocalTime startTime = LocalTime.of(startHour, startMinute);
		LocalTime endTime = startTime.plusMinutes(movieDuration + 10);
		if (theSpaceIsAvailable(showDate, startTime, endTime, preRoom)) {
			Room room = new Room(preRoom.equals("Sala Mini")? RoomType.miniRoom: RoomType.mediumRoom);
			showsData.add(new Show(movieName, movieDuration, showDate, startTime, endTime, room));
			arrayShowsData.add(new Show(movieName, movieDuration, showDate, startTime, endTime, room));
			return true;
		} else {
			return false;
		}		
	}
	
	public void loadAdmins() throws IOException, ClassNotFoundException {
		File file = new File(".\\files\\doNotTouchMe.txt");
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		@SuppressWarnings("unchecked")
		ArrayList<Admin> obj = (ArrayList<Admin>)ois.readObject();
		admins = obj;		
		ois.close();
		fis.close();
	}
	
	public boolean authenticateAdmin(String id, String password) {
		boolean validation = false;
		for (Admin admin: admins) {
			if (admin.getId().equals(id) && admin.getPassword().equals(password)){
				validation = true;
				break;
			}
		}
		return validation;
	}

	public ObservableList<Show> getShowsData() {
		return showsData;
	}
	
	public ArrayList<Show> getShowsData2() {
		return arrayShowsData;
	}

	public boolean selectSeat(Room room, Integer columnIndex, Integer rowIndex) {
		if (room.getSeats()[columnIndex][rowIndex].getSpectator()==null) {
			return true;
		} else {
			return false;
		}
	}
	
    public void addSpectator(Room room, String name, String id, int column, int row){
        room.addSpectator(new Spectator(name, id), column, row);
    }

	public void addToReportCounter() {
		reportCounter++;
	}
	
	@Override
	public String toString() {
		String s = "";
		int i=1;
		for (Show show:showsData) {
			if (i>1) {
				s+="\n\n";
			}
			
			s+= i + "." + show.toString();
			i++;
		}
		return s;
	}
}