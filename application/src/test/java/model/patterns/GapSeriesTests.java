package model.patterns;

import junit.framework.TestCase;

public class GapSeriesTests extends TestCase {

	public void testSetGapsForEmptySeries() throws Exception {
		GapSeries series = new GapSeries();
		series.setBlockingPositionsDependingOnNumberOfEmptySpaces();

		try {
			series.getGap(0);
		} catch (Exception e) {
			assertEquals("Index: 0, Size: 0", e.getMessage());
		}
	}

	public void testSetGapsForNonEmptySeries() throws Exception {
		GapSeries series = new GapSeries();
		series.add(44);
		series.addEmptyPosition(45);
		series.addEmptyPosition(46);
		series.add(47);

		series.setBlockingPositionsDependingOnNumberOfEmptySpaces();
		assertEquals(45, series.getGap(0));
		assertEquals(46, series.getGap(1));
	}
}
