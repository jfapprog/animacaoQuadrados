package packBase;

import java.util.Stack;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

import com.sun.opengl.util.GLUT;

public class efA extends Triangle3 {

	// variáveis
	private GLUT glut;
	private Square square;
	private Stack<Square> squareList;
	private int counter;
	private float r;
	private float g;
	private float b;
	private int partial;
	private int division;

	// construtor
	public efA(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
		this.glut = new GLUT();
		this.square = new Square();
		this.squareList = new Stack<Square>();
		this.counter = 0;
		this.r = 0.0f;
		this.g = 0.0f;
		this.b = 0.0f;
		this.partial = 1;
		this.division = 10;
	}

	// desenhar um quadrado
	protected void drawSquare() {
		// obter vertices do quadrado
		int[][] vertices = this.square.getVertices();
		// desenhar lados do quadrado
		for (int i = 0; i < 4; i++)
			this.antialiasedLine(vertices[i][0], vertices[i][1], vertices[(i + 1) % 4][0], vertices[(i + 1) % 4][1],
					this.r, this.g, this.b);
	}

	// preencher um quadrado
	protected void fillSquare() {
		int[][] verticesSquare = this.square.getVertices();
		int[][] verticesTriangle = new int[4][2];
		// preencher primeiro triângulo
		for (int i = 0; i < 3; i++) {
			verticesTriangle[i][0] = verticesSquare[i][0];
			verticesTriangle[i][1] = verticesSquare[i][1];
		}
		this.fillTriangle(verticesTriangle, this.r, this.g, this.b);
		// preencher segundo triângulo
		for (int i = 2; i < 5; i++) {
			verticesTriangle[i - 2][0] = verticesSquare[i % 4][0];
			verticesTriangle[i - 2][1] = verticesSquare[i % 4][1];
		}
		this.fillTriangle(verticesTriangle, this.r, this.g, this.b);
	}

	// determinar coordenadas dos vértices iniciais
	protected void startSquare() {
		int halfSide = (int) (2 * Math.min(this.width, this.height) / 5);
		// determinar os vértices iniciais
		int xLeft = (int) (this.width / 2 - halfSide);
		int xRight = (int) (this.width / 2 + halfSide);
		int yLower = (int) (this.height / 2 - halfSide);
		int yUpper = (int) (this.height / 2 + halfSide);
		int[][] vertices = new int[4][2];
		vertices[0][0] = xLeft;
		vertices[0][1] = yLower;
		vertices[1][0] = xLeft;
		vertices[1][1] = yUpper;
		vertices[2][0] = xRight;
		vertices[2][1] = yUpper;
		vertices[3][0] = xRight;
		vertices[3][1] = yLower;
		// guardar vértices no quadrado
		this.square.setVertices(vertices);
	}

	// desenhar tabela
	protected void drawTable(int h) {
		int xLeft, xRight, yLower, yUpper;
		int[][] vertices = new int[4][2];
		xLeft = 0;
		xRight = (int) (this.width / 20);
		for (int linha = 0; linha < 11; linha++) {
			yLower = (int) ((h - linha) * this.height / 20);
			yUpper = (int) ((h - 1 - linha) * this.height / 20);
			vertices[0][0] = xLeft;
			vertices[0][1] = yLower;
			vertices[1][0] = xLeft;
			vertices[1][1] = yUpper;
			vertices[2][0] = 2 * xRight;
			vertices[2][1] = yUpper;
			vertices[3][0] = 2 * xRight;
			vertices[3][1] = yLower;
			// desenhar lados da tabela
			for (int i = 0; i < 4; i++)
				this.antialiasedLine(vertices[i][0], vertices[i][1], vertices[(i + 1) % 4][0], vertices[(i + 1) % 4][1],
						1.0f, 1.0f, 1.0f);
			// desenhar linha central
			this.antialiasedLine(xRight, yLower, xRight, yUpper, 1.0f, 1.0f, 1.0f);
		}
		// escrever título das colunas
		this.gl.glColor3f(1.0f, 1.0f, 1.0f);
		this.gl.glRasterPos3f((float) xLeft, (float) ((h - 1) * this.height / 20), 0.0f);
		this.glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Pn");
		this.gl.glRasterPos3f((float) xRight, (float) ((h - 1) * this.height / 20), 0.0f);
		this.glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "An");
	}

	// escrever perímetro na tabela
	protected void writePerimeter() {
		this.gl.glColor3f(1.0f, 1.0f, 1.0f);
		this.gl.glRasterPos3f(0.0f, (float) ((16 - this.counter) * this.height / 20), 0.0f);
		double perimeter = Math.round(this.square.perimeter());
		String s = String.format("%.0f", perimeter);
		this.glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, s);
	}

	// escrever área na tabela
	protected void writeArea() {
		this.gl.glColor3f(1.0f, 1.0f, 1.0f);
		this.gl.glRasterPos3f((float) (this.width / 20), (float) ((16 - this.counter) * this.height / 20), 0.0f);
		double area = Math.round(this.square.area());
		String s = String.format("%.0f", area);
		this.glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, s);
	}

	// escrever título da tabela
	protected void writeInfo() {
		this.gl.glColor3f(0.0f, 0.7f, 0.0f);
		this.gl.glRasterPos3f(0.0f, (float) (19 * this.height / 20), 0.0f);
		this.glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Sucessões:");
		this.drawTable(18);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// se o contador é zero reinicializar desenho
		if (this.counter == 0) {
			// gerar cores aleatórias
			this.r = (float) Math.random();
			this.g = (float) Math.random();
			this.b = (float) Math.random();
			// gerar número de divisões do quadrado
			this.division = (int) (8 * Math.random() + 2);
			this.partial = (int) ((division - 2) * Math.random() + 1);
			// limpar lista de quadrados e buffer
			this.squareList.clear();
			this.gl.glClear(GL.GL_COLOR_BUFFER_BIT);
			// reinicializar quadrado
			this.startSquare();
			// adicionar quadrado à lista
			this.squareList.add(this.square.clone());
			// desenhar quadrado
			this.drawSquare();
			// preencher quadrado
			this.fillSquare();
			// iniciar inforamções e escrever informações na tabela
			this.writeInfo();
			this.writePerimeter();
			this.writeArea();
		} else {
			// se 0 < contador < 20 realizar sucessão decrescente
			if (this.counter > 0 && this.counter < 20) {
				// obter as coordenadas do quadrado atual
				int[][] temp = this.square.getVertices();
				// determinar coordenadas dos novos vértices
				int[][] vertices = new int[4][2];
				for (int i = 0; i < 4; i++) {
					vertices[i][0] = this.partial * temp[i][0] / this.division
							+ (this.division - this.partial) * temp[(i + 1) % 4][0] / this.division;
					vertices[i][1] = partial * temp[i][1] / this.division
							+ (this.division - this.partial) * temp[(i + 1) % 4][1] / this.division;
				}
				// alterar coordenadas do quadrado
				this.square.setVertices(vertices);
				// adicionar quadrado à lista
				this.squareList.push(this.square.clone());
				// preencher quadrado
				this.fillSquare();
				// escrever informações na tabela
				if (this.counter < 10) {
					this.writePerimeter();
					this.writeArea();
				}
			} else if (this.counter >= 20 && this.counter < 40) {
				// retirar quadrado da pilha
				this.square = this.squareList.pop();
				// desenhar quadrado
				this.drawSquare();
			}
		}
		counter++;
		if (counter == 40)
			counter = 0;
		// diminuir tonalidade
		this.r -= 0.03f;
		this.g -= 0.03f;
		this.b -= 0.03f;
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// alterar variáveis
		this.width = (double) width;
		this.height = (double) height;
		// especificar a área de desenho
		this.gl.glMatrixMode(GL.GL_PROJECTION);
		this.gl.glLoadIdentity();
		this.gl.glOrtho(0.0, this.width, 0.0, this.height, -1.0, 1.0);
		// mudar para a matriz do modelo
		this.gl.glMatrixMode(GL.GL_MODELVIEW);
		this.gl.glLoadIdentity();
		// reinicializar contador, para reinicializar construção
		counter = 0;
	}

}
