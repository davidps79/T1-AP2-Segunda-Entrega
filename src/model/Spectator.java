package model;

import java.io.Serializable;

public class Spectator implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
    private String id;

    public Spectator(String name, String id){

        this.name=name;
        this.id=id;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "Name: "+name+"\n"+
                "Id: "+id;
    }

    
}
