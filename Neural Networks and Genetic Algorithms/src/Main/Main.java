package Main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import States.State;

@SuppressWarnings("serial")
public class Main extends JPanel implements KeyListener, MouseListener, Runnable {
	int currentState;
	Thread thread;
	State[] states;
	State[] defaultStates;
	static int scale;
	static int width;
	static int height;
	
	public static void main(String[] args) {
		java.awt.Point center = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		JFrame frame = new JFrame("Neural Networks and Genetic Algorithms");
		
		scale = 2;
		width = 320;
		height = 240;
		
		frame.setBounds(center.x - width * scale / 2, center.y - height * scale / 2, width * scale, height * scale);
		frame.setContentPane(new Main());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	
	public Main() {
		setPreferredSize(new java.awt.Dimension(width * scale, height * scale));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		
		addKeyListener(this);
		addMouseListener(this);
		
		thread = new Thread(this);
		thread.start();
	}
	
	@SuppressWarnings("static-access")
	public void run() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D)image.getGraphics();
		defaultStates = new State[]{new States.Test()};
		states = defaultStates;
		
		while(true) {
			long start = System.nanoTime();
			
			states[currentState].update(graphics);
			if(states[currentState].switchStates) {setState(states[currentState].nextState);}
			
			java.awt.Graphics graphicsTemp = getGraphics();
			graphicsTemp.drawImage(image, 0, 0, width * scale, height * scale, null);
			graphicsTemp.dispose();
			
			long wait = 1000 / 600 - (System.nanoTime() - start) / 1000000;
			if(wait <= 0) {wait = 0;}
			try {thread.sleep(wait);}
			catch(Exception e) {e.printStackTrace();}
		}
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {states[currentState].keyPressed(key.getKeyCode());}
	public void keyReleased(KeyEvent key) {states[currentState].keyReleased(key.getKeyCode());}
	
	public void mouseClicked(MouseEvent e) {states[currentState].mouseClicked(e);}
	public void mouseEntered(MouseEvent e) {states[currentState].mouseEntered(e);}
	public void mouseExited(MouseEvent e) {states[currentState].mouseExited(e);}
	public void mousePressed(MouseEvent e) {states[currentState].mousePressed(e);}
	public void mouseReleased(MouseEvent e) {states[currentState].mouseReleased(e);}
	
	void setState(int state) {
		states = defaultStates;
		currentState = state;
	}
}
