package packBase;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

public class Line3 extends Point0 {

	// construtor
	public Line3(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
	}

	// ativar pixel
	protected void writePixel(int x, int y, int flag) {
		this.gl.glBegin(GL.GL_POINTS);
		if (flag == 0)
			this.gl.glVertex2i(x, y);
		else if (flag == 1) // simetria em relação à bissetriz y = x
			this.gl.glVertex2i(y, x);
		else if (flag == 10) // simetria em relação ao eixo 0x
			this.gl.glVertex2i(x, -y);
		else if (flag == 11) // simetria em relação à bissetriz y = -x
			this.gl.glVertex2i(y, -x);
		this.gl.glEnd();
	}

	protected void bresenhamLine(int x0, int y0, int xn, int yn) {
		int flag = 0;
		if (xn < x0) {
			// trocar P0 com Pn
			int temp = x0;
			x0 = xn;
			xn = temp;

			temp = y0;
			y0 = yn;
			yn = temp;
		}

		if (yn < y0) {
			// simetria em relação ao eixo 0x
			y0 = -y0;
			yn = -yn;

			flag = 10;
		}

		int dx = xn - x0;
		int dy = yn - y0;

		if (dx < dy) {
			int temp = x0;
			x0 = y0;
			y0 = temp;

			temp = xn;
			xn = yn;
			yn = temp;

			temp = dx;
			dx = dy;
			dy = temp;

			flag++;
		}

		int d = 2 * dy - dx;
		int incrE = 2 * dy;
		int incrNE = 2 * (dy - dx);

		for (int x = x0, y = y0; x < xn + 1; x++) {
			this.writePixel(x, y, flag);
			if (d <= 0)
				d += incrE;
			else {
				y++;
				d += incrNE;
			}
		}
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// gerar um segmento de reta aleatório
		int x0 = (int) (Math.random() * this.width);
		int y0 = (int) (Math.random() * this.height);
		int xn = (int) (Math.random() * this.width);
		int yn = (int) (Math.random() * this.height);
		// gerar cores aleatórias
		float r = (float) Math.random();
		float g = (float) Math.random();
		float b = (float) Math.random();
		// desenhar o segmento de reta
		this.gl.glColor3f(r, g, b);
		this.bresenhamLine(x0, y0, xn, yn);
	}

}
