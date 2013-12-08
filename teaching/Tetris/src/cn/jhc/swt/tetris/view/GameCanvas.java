package cn.jhc.swt.tetris.view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import cn.jhc.swt.tetris.util.Config;

public class GameCanvas extends Canvas {

	public GameCanvas(Composite parent, int style) {
		super(parent, style);
		this.setSize(Config.CANVAS_WIDTH * Config.CELL_SIZE,
				Config.CANVAS_HEIGHT * Config.CELL_SIZE);
		
	}

}
