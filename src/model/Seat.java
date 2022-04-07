package model;

import java.io.Serializable;
import exception.OccupiedSeatException;

public class Seat implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
    private Spectator spectator;

    public Seat(){
    }
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }    
    
    public boolean isAvailable() {
    	return (spectator==null);
    }
    
    public Spectator getSpectator() {
		return spectator;
	}

	public void setSpectator(Spectator spectator) throws OccupiedSeatException {
		if (this.spectator == null) this.spectator = spectator;
		else throw new OccupiedSeatException("La silla esta ocupada");
	}

}
