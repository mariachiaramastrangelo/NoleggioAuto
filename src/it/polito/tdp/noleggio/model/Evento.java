package it.polito.tdp.noleggio.model;

import java.time.LocalTime;
//necessario per una coda prioritaria
public class Evento implements Comparable<Evento>{
	
	public enum TipoEvento{
		CLIENTE_ARRIVA,
		AUTO_RESTITUITA
	}
	//il tempo qui è continuo approssimato al minuto
	private LocalTime tempo;
	private TipoEvento tipo;
	
	
	public Evento(LocalTime tempo, TipoEvento tipo) {
		super();
		this.tempo = tempo;
		this.tipo = tipo;
	}

	
	public LocalTime getTempo() {
		return tempo;
	}


	public void setTempo(LocalTime tempo) {
		this.tempo = tempo;
	}


	public TipoEvento getTipo() {
		return tipo;
	}


	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}


	@Override
	//priorità tempo 
	public int compareTo(Evento e) {
		// TODO Auto-generated method stub
		return this.tempo.compareTo(e.tempo);
	}

}
