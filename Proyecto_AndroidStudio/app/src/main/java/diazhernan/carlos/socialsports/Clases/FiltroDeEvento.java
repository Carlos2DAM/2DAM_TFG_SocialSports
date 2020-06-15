package diazhernan.carlos.socialsports.Clases;

import java.util.Date;

public class FiltroDeEvento {

    private String sport;
    private String location;
    private Date fechaDelEvento;
    private String horaDelEvento;
    private boolean reserved;
    private float reputation;

    public FiltroDeEvento() {
        sport = "";
        location = "";
        fechaDelEvento = null;
        horaDelEvento = "";
        reserved = false;
        reputation = -1;
    }

    public FiltroDeEvento(String sport, String location, Date fechaDelEvento, String horaDelEvento, boolean reserved, float reputation) {
        this.sport = sport;
        this.location = location;
        this.fechaDelEvento = fechaDelEvento;
        this.horaDelEvento = horaDelEvento;
        this.reserved = reserved;
        this.reputation = reputation;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getFechaDelEvento() {
        return fechaDelEvento;
    }

    public void setFechaDelEvento(Date fechaDelEvento) {
        this.fechaDelEvento = fechaDelEvento;
    }

    public String getHoraDelEvento() {
        return horaDelEvento;
    }

    public void setHoraDelEvento(String horaDelEvento) {
        this.horaDelEvento = horaDelEvento;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public float getReputation() {
        return reputation;
    }

    public void setReputation(float reputation) {
        this.reputation = reputation;
    }
}
