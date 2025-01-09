package model;

import static org.junit.Assert.*;

import org.junit.Test;

import model.entity.Commessa;
import model.entity.CommessaInstance;
import model.entity.Dipendente;
import model.entity.Reparto;
import model.service.CommessaService;

public class CommessaServiceTest2 {

	@Test
	public void testAssegnaTasksSistema() {
		Dipendente d = new Dipendente("Luca", Reparto.CABLAGGIO);
		Commessa padre = new Commessa("Monta testa", Reparto.CABLAGGIO, null, null);
		Commessa figlio1 = new Commessa("cabla testa", Reparto.CABLAGGIO, null, null);
		Commessa figlio2 = new Commessa("produci testa", Reparto.CABLAGGIO, null, null);
		CommessaService service = new CommessaService("test");
		figlio1.setCommessaPadre(padre); // Associa figlio1 al padre
		figlio2.setCommessaPadre(padre);
		padre.addCommessaFiglia(figlio2);
		padre.addCommessaFiglia(figlio1);
		CommessaInstance cm = new CommessaInstance(padre,1);
		int numTask = service.assegnaTasksSistema(padre, cm);
		assertEquals(2, numTask);
		
	}

}
