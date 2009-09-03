package model.patterns;

import junit.framework.TestCase;

public class IndexListTests extends TestCase {

	public void testToString() throws Exception {
		DirectionalBoardPositionsList list = new DirectionalBoardPositionsList();
		list.add(45);
		assertEquals("45 ", list.toString());
	}
}
