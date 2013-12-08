package cn.jhc.swt.tetris.util;

public class Config {
	/**
	 * 单元格的长度，单位是像素，由于是正方形，不必区分长和宽。
	 */
	public static final int CELL_SIZE = 20;
	/**
	 * 游戏画布的宽度，单位是{@link CELL_SIZE}。
	 */
	public static final int CANVAS_WIDTH = 15;
	/**
	 * 游戏画布的高度，单位是{@link CELL_SIZE}。
	 */
	public static final int CANVAS_HEIGHT = 20;
	/**
	 * 表示Shape所用的数组的大小，一般是4X4，但为演示一些额外的有趣情形，也在这里修改。
	 */
	public static final int SHAPE_SIZE = 4;
}
