package modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Requisitos {
	private int edadMinima, edadMaxima;
	private String requisitoDeGenero;
	private float reputacionNecesaria;
	
	public int getEdadMinima() {
		return edadMinima;
	}
	public void setEdadMinima(int edadMinima) {
		this.edadMinima = edadMinima;
	}
	public int getEdadMaxima() {
		return edadMaxima;
	}
	public void setEdadMaxima(int edadMaxima) {
		this.edadMaxima = edadMaxima;
	}
	public String getRequisitoDeGenero() {
		return requisitoDeGenero;
	}
	public void setRequisitoDeGenero(String requisitoDeGenero) {
		if(requisitoDeGenero == null) this.requisitoDeGenero = "";
		else this.requisitoDeGenero = requisitoDeGenero;
	}
	public float getReputacionNecesaria() {
		return reputacionNecesaria;
	}
	public void setReputacionNecesaria(float reputacionNecesaria) {
		this.reputacionNecesaria = reputacionNecesaria;
	}
	
}
