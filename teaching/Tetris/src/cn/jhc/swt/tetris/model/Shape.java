package cn.jhc.swt.tetris.model;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import cn.jhc.swt.tetris.util.Config;

/**
 * 代表俄罗斯方块，文档中以方块称呼，方块的格子称单元格。 需要注意的是，一个Shape对象并非是玩家看到的一个形状，而是一种形状，可能会包含多种变形。
 * 玩家使用向上键即可在多种变形之间切换。
 * 
 * @author luyanfei
 * 
 */
public class Shape {
	/**
	 * 描述方块的多种变形的二维数组，每种变形用一维数据来描述，1表示对应单元格要填充，0表示不必填充。
	 */
	private int[][] body;
	/**
	 * 描述该形状牌第几种变形，status=0表示第0种变形。
	 */
	private int status;
	/**
	 * 方块左上角单元格在画布中的x坐标，以单元格为单位。
	 */
	protected int x = 10;
	/**
	 * 方块左上角单元格在画布中的y坐标，以单元格为单位。
	 */
	protected int y = 0;
	/**
	 * 标识Shape对象是否活动，不活动的对象被ground接受，不能再移动。
	 */
	private boolean alive = true;

	/**
	 * 方块的颜色。
	 */
	private Color color = null;

	public Shape(int[][] body, int status) {
		this.body = body;
		this.status = status;
	}

	/**
	 * 向下移。
	 */
	public void moveDown() {
		y++;
	}
	/**
	 * 向上移。
	 */
	public void moveUp() {
		y--;
	}

	/**
	 * 向左移。
	 */
	public void moveLeft() {
			x--;
	}

	/**
	 * 向右移。
	 */
	public void moveRight() {
			x++;
	}

	/**
	 * 顺时针旋转。
	 */
	public void rotate() {
		status = (status + 1) % body.length;
	}

	/**
	 * 逆时针旋转。
	 */
	public void rotateBack() {
		status = status == 0 ? body.length-1 : (status - 1) % body.length;
	}

	/**
	 * 通过传入的GC做图，画出方块。
	 * @param gc 画图用的GC。
	 */
	public void draw(GC gc) {
		Color oldColor = gc.getBackground();
		gc.setBackground(color);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (isBlock(j, i))
					gc.fillRectangle((x + j) * Config.CELL_SIZE, (y + i)
							* Config.CELL_SIZE, Config.CELL_SIZE,
							Config.CELL_SIZE);
			}
		}
		gc.setBackground(oldColor);
	}

	/**
	 * 该变形的（x，y）位置是否为1。
	 * 
	 * @param x 相对坐标x
	 * @param y 相对坐标y
	 * @return 对应位置为1，返回true，否则返回false。
	 */
	protected boolean isBlock(int x, int y) {
		return body[status][y * 4 + x] == 1;
	}

	public int[][] getBody() {
		return body;
	}

	public void setBody(int[][] body) {
		this.body = body;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
