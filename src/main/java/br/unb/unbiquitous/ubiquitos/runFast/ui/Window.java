package br.unb.unbiquitous.ubiquitos.runFast.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{

	/**
	 * serialVersionUID = 96314687473652840L;
	 */
	private static final long serialVersionUID = 96314687473652840L;
	
	public static final int WINDOW_WIDTH = 1280;//1152;//1024;
	public static final int WINDOW_HEIGHT = 768;
	
	private static Window window = null;
	
	public static Window GetInstance() {
		if (window == null) {
			window = new Window();
			window.setTitle("Run Fast!");
		}
		return window;
	}
	
	public Window() {
		//super.setIconImage(new ImageIcon(getClass().getResource("boneco.png")).getImage());
		
		//add(new Board());
        setTitle("Run Fast!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
		//this.setExtendedState(MAXIMIZED_BOTH);
	}
	
	public void troca (JPanel painel) {
		window.remove(window.getContentPane());
		window.setContentPane(painel);
		window.setVisible(true);
	}
	
}
