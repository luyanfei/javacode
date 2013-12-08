package cn.jhc.controller;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import cn.jhc.domain.Spittle;
import cn.jhc.service.SpitterService;


public class HomeControllerTest {

	@Test
	public void shouldDisplayRecentSpittles() {
		List<Spittle> expectedSpittles = 
				asList(new Spittle(), new Spittle(), new Spittle());
		SpitterService spitterService = mock(SpitterService.class);
		when(spitterService.getRecentSpittles(HomeController.DEFAULT_SPITTLES_PER_PAGE))
			.thenReturn(expectedSpittles);
		HomeController controller = new HomeController(spitterService);
		HashMap<String, Object> model = new HashMap<String, Object>();
		String viewName = controller.showHomePage(model);
		assertEquals("home", viewName);
		assertSame(expectedSpittles, model.get("spittles"));
		verify(spitterService).getRecentSpittles(HomeController.DEFAULT_SPITTLES_PER_PAGE);
	}
}
