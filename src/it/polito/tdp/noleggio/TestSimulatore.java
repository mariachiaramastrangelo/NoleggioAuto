package it.polito.tdp.noleggio;

import java.time.Duration;

import it.polito.tdp.noleggio.model.Simulatore;

public class TestSimulatore {
	public static void main(String[] args) {
		Simulatore sim= new Simulatore();
		sim.setIntervalloArrivoCliente(Duration.ofMinutes(5));
		sim.init(15);
		sim.run();
		System.out.println(sim.getNumeroClientiTotali()+ " clienti totali, di cui "+sim.getNumeroClientiInsoddisfatti()+" insoddisfatti");
	}

}
