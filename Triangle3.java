package packBase;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

public class Triangle3 extends Line4 {

	// construtor
	public Triangle3(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
	}

	// preenche uma linha horizontal entre os valores x1 e x2
	protected void span(int x2, int x1, int y, float r, float g, float b) {
		// caso x1 > x2 trocar
		if (x1 > x2) {
			int temp = x2;
			x2 = x1;
			x1 = temp;
		}
		// ativar pixeis no segmento de reta [(x1,y);(x2,y)]
		for (int x = x1; x < x2; x++) {
			this.gl.glColor3f(r, g, b);
			this.gl.glBegin(GL.GL_POINTS);
			this.gl.glVertex2i(x, y);
			this.gl.glEnd();
		}
	}

	protected void fillTriangle(int[][] vertices, float r, float g, float b) {
		// número dos vértices com y mínimo, médio e máximo
		int yMin, yMed, yMax;
		// ordenar os vértices em y
		if (vertices[0][1] < vertices[1][1]) {
			if (vertices[0][1] < vertices[2][1]) {
				yMin = 0;
				if (vertices[1][1] < vertices[2][1]) { // 0 < 1 < 2
					yMed = 1;
					yMax = 2;
				} else { // 0 < 2 < 1
					yMed = 2;
					yMax = 1;
				}
			} else { // 2 < 0 < 1
				yMin = 2;
				yMed = 0;
				yMax = 1;
			}
		} else {
			if (vertices[1][1] < vertices[2][1]) {
				yMin = 1;
				if (vertices[0][1] < vertices[2][1]) { // 1 < 0 < 2
					yMed = 0;
					yMax = 2;
				} else { // 1 < 2 < 0
					yMed = 2;
					yMax = 0;
				}
			} else { // 2 < 1 < 0
				yMin = 2;
				yMed = 1;
				yMax = 0;
			}
		}

		// calcular o inverso do declive para os três lados
		float mMinMax = 0, mMinMed = 0, mMedMax = 0;
		float xMin = vertices[yMin][0], xMax = vertices[yMin][0];
		float dy;

		// lado que une vértice de yMin com vértice de yMax
		dy = vertices[yMax][1] - vertices[yMin][1];
		if (dy == 0)
			return; // triângulo sem área
		mMinMax = (float) (vertices[yMax][0] - vertices[yMin][0]) / dy;

		// lado que une vértice de yMin com vértice de yMed
		dy = vertices[yMed][1] - vertices[yMin][1];
		if (dy == 0)
			xMin = vertices[yMed][0];
		else
			mMinMed = (float) (vertices[yMed][0] - vertices[yMin][0]) / dy;

		// lado que une vértice de yMed com vértice de yMax
		dy = vertices[yMax][1] - vertices[yMed][1];
		if (dy == 0)
			;
		else
			mMedMax = (float) (vertices[yMax][0] - vertices[yMed][0]) / dy;

		// ativar retas horizontais entre os lados
		for (int y = vertices[yMin][1]; y < vertices[yMed][1]; y++) {
			this.span((int) xMax, (int) xMin, y, r, g, b);
			xMin += mMinMed;
			xMax += mMinMax;
		}

		for (int y = vertices[yMed][1]; y < vertices[yMax][1]; y++) {
			this.span((int) xMax, (int) xMin, y, r, g, b);
			xMin += mMedMax;
			xMax += mMinMax;
		}

	}

	@Override
	public void display(GLAutoDrawable drawable) {

		// limpar o triângulo anterior
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// gerar aleatoriamente 3 vértices
		int xA = (int) (Math.random() * this.width);
		int yA = (int) (Math.random() * this.height);
		int xB = (int) (Math.random() * this.width);
		int yB = (int) (Math.random() * this.height);
		int xC = (int) (Math.random() * this.width);
		int yC = (int) (Math.random() * this.height);

		// gerar cores aleatórias
		float r = (float) Math.random();
		float g = (float) Math.random();
		float b = (float) Math.random();
		
		// desenhar os lados do triângulo
		this.antialiasedLine(xA, yA, xB, yB, r, g, b);
		this.antialiasedLine(xB, yB, xC, yC, r, g, b);
		this.antialiasedLine(xC, yC, xA, yA, r, g, b);
		
		// preencher triângulo
		int[][] vertices = new int[3][2];
		vertices[0][0] = xA;
		vertices[0][1] = yA;
		vertices[1][0] = xB;
		vertices[1][1] = yB;
		vertices[2][0] = xC;
		vertices[2][1] = yC;
		
		this.fillTriangle(vertices, r, g, b);
	}

}
