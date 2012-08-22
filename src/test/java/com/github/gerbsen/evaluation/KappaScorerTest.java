package de.danielgerber.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class KappaScorerTest {

	public static junit.framework.Test suite() {

		return new JUnit4TestAdapter(KappaScorerTest.class);
	}

	@Test
	public void testCohensKappaScore() {

		assertEquals(0.4, KappaScorer.getCohenKappaScore(20, 5, 10, 15, 50), 0); 
	}
}
