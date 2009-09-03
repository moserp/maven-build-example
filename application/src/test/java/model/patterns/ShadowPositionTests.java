package model.patterns;

import junit.framework.TestCase;

public class ShadowPositionTests extends TestCase {

	public void testToString() throws Exception {
		ShadowPosition shadow = new ShadowPosition(45);
		assertEquals("45", shadow.toString());
	}
}
