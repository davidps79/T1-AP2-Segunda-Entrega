package model;

import java.io.Serializable;
import exception.OccupiedSeatException;

public class Room implements Serializable {
	private static final long serialVersionUID = 1L;
	private Seat[][] seats;
    private String id;

    public Room(RoomType type){
        int rows = 0;

        if (type == RoomType.miniRoom) {
        	this.id = "Sala Mini";
        	rows = 4;
        	seats = new Seat[7][rows];
        } else {
        	this.id = "Sala Mediana";
        	rows = 6;
        	seats = new Seat[7][rows];
        }
        
        for (int i=0; i<7; i++){
            for (int j=0; j<rows; j++){
                seats[i][j] = new Seat();
            }
        }
        
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    public Seat[][] getSeats() {
        return this.seats;
    }
    
    public void addSpectator(Spectator spectator, int column, int row) {
        try {
			seats[column][row].setSpectator(spectator);
		} catch (OccupiedSeatException e) {
			// No se hace nada...
		}
    }

    @Override
    public String toString() {
    	return id;
    }
    
    public String toString2(){
        String report="";
        for (int i=0; i<seats.length; i++){
            for (int j=0; j<seats[0].length; j++){
                if (seats[i][j].getSpectator()!=null){
                    report+= "\n" + seats[i][j].getSpectator().getName();
                }
            }
        }
        return report;
    }
}
