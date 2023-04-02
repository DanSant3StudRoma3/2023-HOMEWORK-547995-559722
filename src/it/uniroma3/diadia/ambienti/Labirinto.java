package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe Labirinto, classe centrale nella inizializzazione delle stanze
 * del labirinto. 
 * 
 * Inizializza tutte le stanze in Diadia e crea effettivamente il labirinto.
 * Viene referenziato solo quando deve creare un labirinto nuovo o quando serve
 * conoscere le entrate, le uscite ed eventuali stanze del labirinto.
 * 
 * @author danie
 * @version 1.0
 *
 */
public class Labirinto {

	private Stanza[] labirinto = new Stanza[5];
	private Stanza stanzaCorrente;
	private Stanza entrata;
	private Stanza stanzaVincente;

	/**
	 * Classe costruttore che crea un labirinto hardcoded.
	 * Nel labirinto ci sono stanze già connesse, una stanza di entrata, nella quale il giocatore 
	 * si trova all'inizio di ogni nuova partita, una stanzaVincente, che permette, una volta 
	 * raggiunta di concludere la partita, e vari attrezzi in diverse stanze.
	 */
	public Labirinto() {
		/* crea stanze del labirinto */
		Stanza atrio = new Stanza("Atrio");
		Stanza aulaN11 = new Stanza("Aula N11");
		Stanza aulaN10 = new Stanza("Aula N10");
		Stanza laboratorio = new Stanza("Laboratorio Campus");
		Stanza biblioteca = new Stanza("Biblioteca");

		/* Ripone le stanze in un array per più facile consultazione */
		labirinto[0] = atrio;
		labirinto[1] = aulaN11;
		labirinto[2] = aulaN10;
		labirinto[3] = laboratorio;
		labirinto[4] = biblioteca;

		/* collega le stanze */
		atrio.impostaStanzaAdiacente("nord", biblioteca);
		atrio.impostaStanzaAdiacente("est", aulaN11);
		atrio.impostaStanzaAdiacente("sud", aulaN10);
		atrio.impostaStanzaAdiacente("ovest", laboratorio);
		aulaN11.impostaStanzaAdiacente("est", laboratorio);
		aulaN11.impostaStanzaAdiacente("ovest", atrio);
		aulaN10.impostaStanzaAdiacente("nord", atrio);
		aulaN10.impostaStanzaAdiacente("est", aulaN11);
		aulaN10.impostaStanzaAdiacente("ovest", laboratorio);
		laboratorio.impostaStanzaAdiacente("est", atrio);
		laboratorio.impostaStanzaAdiacente("ovest", aulaN11);
		biblioteca.impostaStanzaAdiacente("sud", atrio);


		/* crea gli attrezzi */
		Attrezzo lanterna = new Attrezzo("lanterna",3);
		Attrezzo osso = new Attrezzo("osso",1);

		/* pone gli attrezzi nelle stanze */
		this.getStanza(aulaN10).addAttrezzo(lanterna);
		this.getStanza(atrio).addAttrezzo(osso);

		// il gioco comincia nell'atrio
		entrata = atrio;
		stanzaCorrente = entrata;  
		stanzaVincente = biblioteca;
	}


	/**
	 * Classe costruttrice che permette di stabilire quali siano l'entrata e l'uscita del labirinto già scritto.<br>
	 * I paramtetri <b>NON POSSONO</b> essere null.
	 * <br><b>ATTENZIONE!</b> Allo stato attuale utilizza il labirinto hardcoded nel costruttore {@link Labirinto()} principale, 
	 * permettendo quindi di scegliere soltanto le stanze dei parametri e <b>non</b> di creare un altro labirinto. 
	 * O perlomeno spero faccia così...
	 *  
	 * @param entrata	Una stanza di entrata, nella quale il giocatore si trova all'inizio della partita.
	 * @param stanzaVincente	Un'uscita, che una volta raggiunta dal giocatore fa terminare la partita. 
	 */
	public Labirinto(Stanza entrata, Stanza stanzaVincente) {
		super();
		this.entrata = entrata;
		this.stanzaCorrente = this.entrata;
		this.stanzaVincente = stanzaVincente;
	}

	/**
	 * Classe costruttotrice che permette di definire una stanza del labirinto, una stanza di entrata 
	 * ed un'uscita.<br>
	 * Questo costruttore permette di creare un labirinto da zero, senza che vengano aggiunte stanze
	 * al di fuori di quelle passate per parametri.
	 * Possono essere passati parametri <b>null</b> per creare un labirinto più o meno vuoto.<br><br>
	 * <b>ATTENZIONE!</b> Le stanze devono essere collegate una per una, altrimenti rimangono isolate 
	 * ed il labirinto effettivamente non esiste
	 * @param primaStanza	La prima stanza del labirinto, da cui si possono collegare altre stanze. 
	 * @param entrata	Una stanza di entrata, nella quale il giocatore si trova all'inizio della partita.
	 * @param stanzaVincente	Un'uscita, che una volta raggiunta dal giocatore fa terminare la partita. 
	 */
	public Labirinto(Stanza primaStanza, Stanza entrata, Stanza stanzaVincente) {
		int i=0;
		if (primaStanza != null) {
			labirinto[i] = primaStanza;
			i++;
		}
		if (entrata != null) {
			this.entrata = entrata;
			stanzaCorrente = this.entrata;
			labirinto[i] = this.entrata;
			i++;
		} 
		if (stanzaVincente != null) {
			this.stanzaVincente = stanzaVincente;
			labirinto[i] = this.stanzaVincente;
		}
	}

	/**
	 * Ricerca una stanza nell'array labirinto e, se assente, il valore di ritorno è <b>null</b>.
	 * <br><b>ATTENZIONE!</b> La stanza di ritorno è sempre <b>l'ultima stanza</b> trovata, e quindi aggiunta, al labirinto.
	 * 
	 * @param nomeStanza
	 * <br>Il parametro è la stanza da ricercares.
	 * @return 
	 * <code>Stanza</code> se è stata trovata una stanza con il nome corrispondente.
	 * <br><code>null</code> se non viene trovata la stanza ricercata. 
	 */
	public Stanza getStanza(Stanza stanzaDaCercare) {
		Stanza trovato = null;
		String nomeStanza = stanzaDaCercare.getNome();

		if (this.labirinto.length > 0) {
			for (Stanza stanzaScandaglio : labirinto) {
				if (stanzaScandaglio.getNome().equals(nomeStanza) == true)
					trovato = stanzaScandaglio;
			}
		}

		return trovato;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}

	public Stanza getStanzaVincente() {
		return this.stanzaVincente;
	}

	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;

	}

}
