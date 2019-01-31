package packBase;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

public class Line4 extends Line3 {

	// construtor
	public Line4(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
	}

	// ativa pixel de acordo com a sua distância à reta
	protected void intensifyPixel(int x, int y, float r, float g, float b, float distance, int flag) {
		float dist = Math.abs(distance);
		// determinar a intensidade
		float ri = (float) (r * (1 - dist / 1.5));
		float gi = (float) (g * (1 - dist / 1.5));
		float bi = (float) (b * (1 - dist / 1.5));
		this.gl.glColor3f(ri, gi, bi);
		// ativar pixel
		this.writePixel(x, y, flag);
	}

	// desenha uma reta pelo algoritmo do ponto médio utilizando anti-aliasing
	protected void antialiasedLine(int x0, int y0, int xn, int yn, float r, float g, float b) {
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

		float hip = (float) Math.sqrt(dx * dx + dy * dy);
		float sinA = dy / hip;
		float cosA = dx / hip;
		
		float distance = 0.0f;
		
		for (int x = x0, y = y0; x < xn + 1; x++) {
			// ativar pixeis
			this.intensifyPixel(x, y, r, g, b, distance, flag);
			this.intensifyPixel(x, y + 1, r, g, b, distance - cosA, flag);
			this.intensifyPixel(x, y - 1, r, g, b, distance + cosA, flag);
			// atualizar parâmetros
			if (d <= 0) {
				distance += sinA;
				d += incrE;
			} else {
				distance += (sinA - cosA);
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
		this.antialiasedLine(x0, y0, xn, yn, r, g, b);
	}

}
