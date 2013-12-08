package cn.jhc.swt.tetris.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.jhc.swt.tetris.model.Shape;
import cn.jhc.swt.tetris.model.ShapeFactory;

public class TestShapeFactory {

	@Test
	public void testGetShape() {
		assertTrue(ShapeFactory.getShape() instanceof Shape);
	}

}
