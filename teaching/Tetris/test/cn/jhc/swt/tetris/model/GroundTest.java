/**
 * 
 */
package cn.jhc.swt.tetris.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cn.jhc.swt.tetris.model.Ground;
import cn.jhc.swt.tetris.model.Shape;
import cn.jhc.swt.tetris.model.ShapeFactory;
import cn.jhc.swt.tetris.util.Config;

/**
 * @author luyanfei
 * 
 */
public class GroundTest {
	Ground ground = null;
	Shape shape = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ground = new Ground();
		shape = ShapeFactory.getShape();
	}

	@Test
	public void test() {
		int[][] a = ground.getBody();
		for (int i = 0; i < Config.CANVAS_HEIGHT; i++) {
			for (int j = 0; j < Config.CANVAS_WIDTH; j++) {
				assertEquals(0, a[i][j]);
			}
		}
	}

	@Test
	public void testCanPut() {
		assertTrue("初始Shape对象不在画布之内。", ground.canPut(shape));
	}

	/**
	 * 测试Ground的accept方法。
	 */
	@Test
	public void testAccept() {
		ground.accept(shape);
		for(int i=0;i<Config.SHAPE_SIZE;i++) {
			for(int j=0;j<Config.SHAPE_SIZE;j++) {
				if(shape.isBlock(j, i)) {
					assertEquals(1, ground.getBody()[shape.getY()+i][shape.getX()+j]);
				}
			}
		}
	}
}
