package packBase;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import com.sun.opengl.util.FPSAnimator;

public class Program {
	
	// teste à classe Point0
	@SuppressWarnings("unused")
	private static void testPoint0() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();

		final GLCanvas canvas = new GLCanvas(capabilities);
		Point0 point0 = new Point0(canvas, (double) width, (double) height);
		canvas.addGLEventListener(point0);

		final Window window = new Window("Test J1_0_Point", canvas, width, height);
		window.setVisible(true);
	}
	
	// teste à classe Line3
	@SuppressWarnings("unused")
	private static void testLine3() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(false);
		final GLCanvas canvas = new GLCanvas(capabilities);
		Line3 line3 = new Line3(canvas, (double) width, (double) height);
		canvas.addGLEventListener(line3);

		final Window window = new Window("Test J1_3_Line", canvas, width, height);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start();
	}

	// teste à classe Line4
	@SuppressWarnings("unused")
	private static void testLine4() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(false);
		final GLCanvas canvas = new GLCanvas(capabilities);
		Line4 line4 = new Line4(canvas, (double) width, (double) height);
		canvas.addGLEventListener(line4);

		final Window window = new Window("Test J1_4_Line", canvas, width, height);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 10, true);
		animator.start();
	}
	
	// teste à classe Triangle3
	@SuppressWarnings("unused")
	private static void testTriangle3() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(false);
		final GLCanvas canvas = new GLCanvas(capabilities);
		Triangle3 triangle3 = new Triangle3(canvas, (double) width, (double) height);
		canvas.addGLEventListener(triangle3);

		final Window window = new Window("Test J1_3_Triangle", canvas, width, height);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 5, true);
		animator.start();
	}
	
	// programa
	private static void executeProgram() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(false);
		final GLCanvas canvas = new GLCanvas(capabilities);
		efA efa = new efA(canvas, (double) width, (double) height);
		canvas.addGLEventListener(efa);

		final Window window = new Window("Sucessão de quadrados", canvas, width, height);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 10, true);
		animator.start();
	}

	public static void main(String[] args) {
		// testPoint0();
		// testLine3();
		// testLine4();
		// testTriangle3();
		executeProgram();
	}

}
