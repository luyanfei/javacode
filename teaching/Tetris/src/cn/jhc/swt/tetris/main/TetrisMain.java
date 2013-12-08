package cn.jhc.swt.tetris.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cn.jhc.swt.tetris.controller.Controller;
import cn.jhc.swt.tetris.model.Ground;
import cn.jhc.swt.tetris.view.GameCanvas;

/**
 * 游戏主函数所在类。
 * @author 吕焱飞
 *
 */
public class TetrisMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell();
//		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		GameCanvas canvas = new GameCanvas(shell, SWT.NONE);
		Controller controller = new Controller(canvas, new Ground());
		controller.start();
		shell.setText("俄罗斯方块");
		shell.pack();
		shell.open();
		while(!shell.isDisposed())
			if(!display.readAndDispatch())
				display.sleep();
		controller.dispose();
		display.dispose();
	}

}
