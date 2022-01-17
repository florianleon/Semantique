package semantic;


import org.junit.Assert;
import org.junit.Test;

import semantic.model.SemanticModel;

public class TestOntologyInteraction {
	
	@Test
	public void createSemanticModel()
	{
		SemanticModel sm = new SemanticModel("./tp-iss.ttl");
		Assert.assertNotNull(sm);
	}

}
