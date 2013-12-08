package cn.jhc.swt.tetris.model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import cn.jhc.swt.tetris.util.Config;
import cn.jhc.swt.tetris.util.Global;

/**
 * Ground本义指地面，泛指周围的环境。因为判断左右是否越界也放在Ground中。
 * 
 * @author luyanfei
 * 
 */
public class Ground {
	/**
	 * 代表整个画布的二维数组，body的每个元素代表画布上对应的单元格的状态，0表示没有，1表示有障碍物。
	 */
	private int[][] body = new int[Config.CANVAS_HEIGHT][Config.CANVAS_WIDTH];


	/**
	 * 测试该Shape能否在当前Ground范围之内放下。
	 */
	public boolean canPut(Shape shape) {
		for (int i = 0; i < Config.SHAPE_SIZE; i++) {
			for (int j = 0; j < Config.SHAPE_SIZE; j++) {
				if(shape.isBlock(j, i)) {
					if(shape.x+j>=Config.CANVAS_WIDTH					//向右是否出界 
							|| shape.x+j<0								//是否向左出界
							|| shape.y+i>=Config.CANVAS_HEIGHT 			//是否向下出界
							|| body[shape.y+i][shape.x+j] == 1) 		//是否与Ground原有的块冲突
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * 能否往下移比较特殊，专门写出函数以方便使用。
	 * @param shape
	 * @return
	 */
	public boolean canMoveDown(Shape shape) {
		boolean flag = false;
		shape.moveDown();
		if(canPut(shape))
			flag = true;
		shape.moveUp();
		return flag;
	}
	/**
	 * 接受方块，将方块转换成障碍物。
	 * @param shape 需要转换成障碍物的对象。
	 */
	public void accept(Shape shape) {
		for(int i=0;i<Config.SHAPE_SIZE;i++)
			for(int j=0;j<Config.SHAPE_SIZE;j++)
				if(shape.isBlock(j, i))
					body[shape.getY()+i][shape.getX()+j]=1;
	}
	/**
	 * 移除所有布满了方块的行。
	 */
	public void removeAllFullLine() {
		int height = body.length;
		for(int i=height-1; i>=0; i--) {
			while(isFull(i))
				removeOneLine(i);
		}
	}
	/**
	 * 判断一行是否为布满方块的行。
	 * @param lineNum 行号。
	 * @return
	 */
	protected boolean isFull(int lineNum) {
		boolean flag = true;
		int len = body[0].length;
		for(int i=0;i<len;i++) {
			if(body[lineNum][i]==0) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	/**
	 * 删除一行，该行上方的行要往下移。
	 * @param lineNum 要删除的行号。
	 */
	public void removeOneLine(int lineNum) {
		int width = body[0].length; 	//为测试方便，此处取第一行的长度为后面拷贝数组时的长度。
		for(int i=lineNum-1;i>=0;i--) {
			//把第i行拷贝到第i+1行
			System.arraycopy(body[i], 0, body[i+1], 0, width);
		}
		//第0行全部设为0
		int[] t = new int[width];
		System.arraycopy(t, 0, body[0], 0, width);
	}
	/**
	 * 画出Ground的方法。
	 * @param gc
	 */
	public void draw(GC gc) {
		Color oldColor = gc.getBackground();
		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		for(int i=0;i<Config.CANVAS_HEIGHT;i++)
			for(int j=0;j<Config.CANVAS_WIDTH;j++)
				if(body[i][j]==1)
					gc.fillRectangle(j*Config.CELL_SIZE, i*Config.CELL_SIZE, Config.CELL_SIZE, Config.CELL_SIZE);
		gc.setBackground(oldColor);
	}

	public int[][] getBody() {
		return body;
	}

	public void setBody(int[][] body) {
		this.body = body;
	}

}
