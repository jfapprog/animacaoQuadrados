package packBase;

public class Square {

	// vari�veis
	private int[][] vertices;

	// construtor default
	public Square() {
		this.vertices = new int[4][2];
	}

	// construtor
	public Square(int[][] vertices) {
		this.vertices = new int[4][2];
		// deep copy
		for (int i = 0; i < 4; i++) {
			this.vertices[i][0] = vertices[i][0];
			this.vertices[i][1] = vertices[i][1];
		}
	}

	// construtor c�pia
	public Square(Square square) {
		this.vertices = new int[4][2];
		// deep copy
		for (int i = 0; i < 4; i++) {
			this.vertices[i][0] = square.getVertices()[i][0];
			this.vertices[i][1] = square.getVertices()[i][1];
		}
	}

	// retorna uma c�pia dos v�rtices do quadrado
	public int[][] getVertices() {
		int[][] res = new int[4][2];
		// deep copy
		for (int i = 0; i < 4; i++) {
			res[i][0] = this.vertices[i][0];
			res[i][1] = this.vertices[i][1];
		}
		return res;
	}

	// altera os v�rtices do quadrado
	public void setVertices(int[][] vertices) {
		// deep copy
		for (int i = 0; i < 4; i++) {
			this.vertices[i][0] = vertices[i][0];
			this.vertices[i][1] = vertices[i][1];
		}
	}

	// retorna uma c�pia do quadrado
	public Square clone() {
		return new Square(this);
	}
	
	// retorna o lado do quadrado
	public double side() {
		double dx = this.vertices[0][0] - this.vertices[1][0];
		double dy = this.vertices[0][1] - this.vertices[1][1];
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	// retorna o per�metro do quadrado
	public double perimeter() {
		return 4 * this.side();
	}
	
	// retorna a �rea
	public double area() {
		return this.side() * this.side();
	}
	
}
