package cn.jhc.swt.tetris.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;

import cn.jhc.swt.tetris.model.Ground;
import cn.jhc.swt.tetris.model.Shape;
import cn.jhc.swt.tetris.util.Global;

public class CanvasKeyListener implements KeyListener {

	/**
	 * 
	 */
	private Shape shape;
	
	private final Ground ground;

	/**
	 * @param shape
	 */
	public CanvasKeyListener(Shape shape, Ground ground) {
		this.shape = shape;
		this.ground = ground;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		//如果shape对象已经不能活动，直接返回。
		if(shape == null || !shape.isAlive())
			return;
		//处理用户按键事件。
		switch(e.keyCode) {
		case SWT.ARROW_LEFT:
			shape.moveLeft();
			if(!ground.canPut(shape))
				shape.moveRight();
			break;
		case SWT.ARROW_RIGHT:
			shape.moveRight();
			if(!ground.canPut(shape))
				shape.moveLeft();
			break;
		case SWT.ARROW_UP:
			shape.rotate();
			if(!ground.canPut(shape))
				shape.rotateBack();
			break;
		case SWT.ARROW_DOWN:
			if(ground.canMoveDown(shape))
				shape.moveDown();
			break;
		}
		((Canvas)e.widget).redraw();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

}