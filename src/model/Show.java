package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Show implements Serializable {
	private static final long serialVersionUID = 1L;
	private String movieName;
	private int movieDuration;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Room room;

    public Show(String movieName, int movieDuration, LocalDate date, LocalTime start, LocalTime end, Room room) {
        this.movieName = movieName;
        this.movieDuration = movieDuration;
        this.date = date;
        this.start = start;
        this.end = end;
        this.room = room;
    }
    
    public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(int movieDuration) {
		this.movieDuration = movieDuration;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	@Override	
	public String toString(){
        return " " + movieName + " - " + date + " - "+ room.toString().toUpperCase() + room.toString2();        
	}
}