package by.epam.maksim.movietheater.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AuditoriumTest {
	
	@Test
	public void testCountVips() {
		Auditorium a = new Auditorium("", 5, "1,2,3");
		assertEquals(0, a.countVipSeats(Arrays.asList(10L, 20L, 30L)));
		assertEquals(1, a.countVipSeats(Arrays.asList(10L, 2L, 30L)));
		assertEquals(2, a.countVipSeats(Arrays.asList(10L, 2L, 3L, 4L, 5L, 6L)));
	}

	@Test
	public void testGetAllSeats() {
	    Auditorium a = new Auditorium("", 10, "1");
	    assertEquals(10, a.getAllSeats().size());
	}

}
