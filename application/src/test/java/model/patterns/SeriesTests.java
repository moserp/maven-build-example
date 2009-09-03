package model.patterns;

import junit.framework.TestCase;

public class SeriesTests extends TestCase {

	public void testToString() throws Exception {
		Series series = new Series();
		series.add(5);

		assertEquals("5 ", series.toString());
	}

	public void testCannotCallGetGap() throws Exception {
		Series series = new Series();
		try {
			series.getGap(0);
		} catch (Exception e) {
			assertEquals("Should only be invoked on GapSeries.", e.getMessage());
		}
	}

	public void testCannotCallSetGaps() throws Exception {
		Series series = new Series();
		try {
			series.setBlockingPositionsDependingOnNumberOfEmptySpaces();
		} catch (Exception e) {
			assertEquals("Should only be invoked on GapSeries.", e.getMessage());
		}
	}

}
