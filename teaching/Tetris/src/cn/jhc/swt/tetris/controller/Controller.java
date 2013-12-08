package cn.jhc.swt.tetris.controller;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import cn.jhc.swt.tetris.listener.CanvasKeyListener;
import cn.jhc.swt.tetris.model.Ground;
import cn.jhc.swt.tetris.model.Shape;
import cn.jhc.swt.tetris.model.ShapeFactory;
import cn.jhc.swt.tetris.util.Config;
import cn.jhc.swt.tetris.view.GameCanvas;

/**
 * 控制器。
 * 判断能否下落的功能应该在Controller中实现，汤阳光的版本却使用了监听器isShapeMoveDownable，与常理不合。
 * @author luyanfei
 * 
 */
public class Controller implements PaintListener {

	private GameCanvas canvas = null;
	private Shape shape = null;
	private Ground ground = null;
	private CanvasKeyListener listener = null;
	/**
	 * 双缓存绘图用Image对象。
	 */
	private Image image = null;
	private GC imageGC = null;

	public Controller(GameCanvas canvas, Ground ground) {
		super();
		this.canvas = canvas;
		this.ground = ground;
		this.image = new Image(Display.getDefault(),
				Config.CANVAS_WIDTH*Config.CELL_SIZE,
				Config.CANVAS_HEIGHT*Config.CELL_SIZE);
		this.imageGC = new GC(this.image);
		this.canvas.addPaintListener(this);
		this.shape = ShapeFactory.getShape();
		this.listener = new CanvasKeyListener(shape, ground);
		this.canvas.addKeyListener(listener);
	}
	/**
	 * 创建并启动令Shape自动下落的线程。
	 */
	public void start() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!canvas.isDisposed()) {
					if (!shape.isAlive()) {  			//如果当前shape对象已经不能活动，则生成新的Shape对象。
						shape = null;
						listener.setShape(null);
						generateNewShape();
					}
					else{
						if(ground.canMoveDown(shape))
							shape.moveDown();
						else {
							shape.setAlive(false);
							ground.accept(shape);
							redraw();
							//消行时需要让玩家看到方块先变成障碍物，然后再消行。
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							ground.removeAllFullLine();
						}
						redraw();
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		t.start();
	}

	@Override
	public void paintControl(PaintEvent e) {
		imageGC.fillRectangle(0, 0, 
				Config.CANVAS_WIDTH*Config.CELL_SIZE, Config.CANVAS_HEIGHT*Config.CELL_SIZE);
		shape.draw(imageGC);
		ground.draw(imageGC);
		e.gc.drawImage(image, 0, 0);
	}

	public void dispose() {
		imageGC.dispose();
		image.dispose();
	}
	/**
	 * 重绘GameCanvas，需要在GUI线程中执行。
	 */
	private void redraw() {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				if(!canvas.isDisposed())
					canvas.redraw();
			}
		});
	}
	/**
	 * 生成新的Shape对象。由于需要获取Color对象，需要在GUI线程中执行。
	 */
	private void generateNewShape() {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				shape = ShapeFactory.getShape();	
				listener.setShape(shape);
			}
		});
	}
}
