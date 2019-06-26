package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.noleggio.model.Evento.TipoEvento;

public class Simulatore {
	private PriorityQueue<Evento> queue = new PriorityQueue<>();
	
	//dovrà mantenere lo stato del mondo 
	//è lo stato del sistema mentre non accade nulla
	//nel mio caso quante auto ho e quante sono disponibili
	private int autoTotali;
	//va da 0 a auto totali e si evolve in funzione del tempo
	private int autoDisponibili;
	
	
	//Parametri di simulazione
	//sono dati sulla base dei quali il simulatore decide cosa fare nel caso ci sia un evento
	//sono una serie di costanti, gestiscono anche generazioni nuovi eventi
	//per questi metterò degli appositi getter e setter
	private LocalTime oraInizio = LocalTime.of(8, 0);
	private LocalTime oraFine= LocalTime.of(20, 0);
	private Duration intervalloArrivoCliente= Duration.ofMinutes(10);
	private List<Duration> durateNoleggio;
	
	
	//Statistiche raccolte
	private int numeroClientiTotali;
	private int numeroClientiInsoddisfatti;
	
	
	//variabili interne
	private Random rand= new Random();
	
	public Simulatore() {
		durateNoleggio= new ArrayList<Duration>();
		durateNoleggio.add(Duration.ofHours(1));
		durateNoleggio.add(Duration.ofHours(2));
		durateNoleggio.add(Duration.ofHours(3));
	}
	//Ho sempre 2 metodi principali
	//inizializzare la simulazione
	public void init(int autoTotali) {
		//preparo le strutture dati indispensabili 
		this.autoTotali= autoTotali;
		this.autoDisponibili= autoTotali;
		this.numeroClientiTotali=0;
		this.numeroClientiInsoddisfatti=0;
		this.queue.clear();
		
		//carico gli eventi iniziali posso farlo anche in un loadevents
		//arriva un cliente ogni intervalloArrivoCliente a partire dalla oraInizio
		for(LocalTime ora= oraInizio; ora.isBefore(oraFine); ora=ora.plus(intervalloArrivoCliente)) {
			//la variabile ora rappresenta gli istanti di tempo in cui arrivano i clienti
			//in ciascuno di questi intervalli carico un evento
			queue.add(new Evento(ora, TipoEvento.CLIENTE_ARRIVA));
		}
		
	} 
	//eseguire la simulazione
	public void run() {
		//cicli finchè la coda è piena
		//oppure finchè il tempo non è finito
		//oppure finché non raggiungo certe statistiche
		while(!queue.isEmpty()) {
			//poll lo toglie dalla lista
			Evento ev= queue.poll();
			switch(ev.getTipo()) {
			case CLIENTE_ARRIVA:
				//devo aggiornare lo stato del mondo 
				//aggiornare le statistiche
				//eventualmente aggiungere eventi
				this.numeroClientiTotali++;
				if(this.autoDisponibili==0) {
					this.numeroClientiInsoddisfatti++;
					
				}else {
					//noleggio un'auto
					this.autoDisponibili--;
					//quando verrà restituita l'auto
					int i= rand.nextInt(durateNoleggio.size());
				 	Duration noleggio= durateNoleggio.get(i);
				 	LocalTime rientro= ev.getTempo().plus(noleggio);
				 	
				 	queue.add(new Evento(rientro, TipoEvento.AUTO_RESTITUITA));
					
				}
				break;
				
			case AUTO_RESTITUITA:
				this.autoDisponibili++;
				break;
			}
		}
		
	}
	public LocalTime getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}
	public LocalTime getOraFine() {
		return oraFine;
	}
	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}
	public Duration getIntervalloArrivoCliente() {
		return intervalloArrivoCliente;
	}
	public void setIntervalloArrivoCliente(Duration intervalloArrivoCliente) {
		this.intervalloArrivoCliente = intervalloArrivoCliente;
	}
	public List<Duration> getDurateNoleggio() {
		return durateNoleggio;
	}
	public void setDurateNoleggio(List<Duration> durateNoleggio) {
		this.durateNoleggio = durateNoleggio;
	}
	public int getAutoTotali() {
		return autoTotali;
	}
	public int getAutoDisponibili() {
		return autoDisponibili;
	}
	public int getNumeroClientiTotali() {
		return numeroClientiTotali;
	}
	public int getNumeroClientiInsoddisfatti() {
		return numeroClientiInsoddisfatti;
	}
	//i valori statistici sono disponibili solo in lettura
	//i parametri sono settabili 
	
}
