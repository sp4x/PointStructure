package points;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegionTest {

	@Test
	public void new_region_has_no_bounds() {
		Region r = new Region();
		assertEquals(Integer.MIN_VALUE, r.minX, 0);
		assertEquals(Integer.MIN_VALUE, r.minY, 0);
		assertEquals(Integer.MAX_VALUE, r.maxX, 0);
		assertEquals(Integer.MAX_VALUE, r.maxY, 0);
	}
	
	@Test
	public void should_split_the_region_in_tho_halves() {
		Region r = new Region();
		Region[] couple = r.splitOnXAxis(10);
		assertEquals((int) couple[0].maxX, 10);
		assertEquals((int) couple[1].minX, 10);
	}
	

	

}
