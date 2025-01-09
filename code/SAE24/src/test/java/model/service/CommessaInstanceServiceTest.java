package model.service;

import static org.junit.Assert.*;

import org.junit.Test;

import model.entity.Commessa;
import model.entity.CommessaInstance;

public class CommessaInstanceServiceTest {
	
	CommessaInstanceService cm= new CommessaInstanceService("test");
	
	
	@Test
	public void testCreaNewCommessaInstance() {
	CommessaService service = new CommessaService("test");
	service.addCommessa("PRova", null, null, null, null);
	Commessa c = new Commessa("PRova",null,null,null);
	CommessaInstance cminstance = cm.creaNewCommessaInstance(c);	
	assertNotNull(cminstance);
		
	}

}
