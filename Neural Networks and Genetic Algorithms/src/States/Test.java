package States;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import Main.Specimen;

public class Test extends State {
	int generation;
	int specimen;
	Color hope1;
	Color hope2;
	boolean learning;
	BufferedImage background;
	Specimen[][] species;
	BufferedImage[] font;
	
	public Test() {
		learning = true;
		
		hope1 = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
		hope2 = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
		
		species = new Specimen[1000][100];
		for(int xpos = 0; xpos < species[0].length; xpos++) {
			double[] weights = new double[20];
			
			for(int ypos = 0; ypos < weights.length; ypos++) {weights[ypos] = Math.random();}
			species[0][xpos] = new Specimen(1, xpos + 1, weights);
			
			species[0][xpos].fitness = Math.max(Math.abs(species[0][xpos].out1.getRed() - hope1.getRed()), 0) + Math.max(Math.abs(species[0][xpos].out1.getGreen() - hope1.getGreen()), 0) + Math.max(Math.abs(species[0][xpos].out1.getBlue() - hope1.getBlue()), 0) +
									   Math.max(Math.abs(species[0][xpos].out2.getRed() - hope2.getRed()), 0) + Math.max(Math.abs(species[0][xpos].out2.getGreen() - hope2.getGreen()), 0) + Math.max(Math.abs(species[0][xpos].out2.getBlue() - hope2.getBlue()), 0);
		}
		
		font = new BufferedImage[10];
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/Background.png"));
			for(int pos = 0; pos < 10; pos++) {font[pos] = ImageIO.read(getClass().getResourceAsStream("/Font.png")).getSubimage(3 * pos, 0, 3, 5);}
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public void update(Graphics2D graphics) {
		if(learning) {
			graphics.setColor(species[generation][specimen].node1);
			graphics.fillRect(145, 33, 30, 30);
			graphics.setColor(species[generation][specimen].node2);
			graphics.fillRect(145, 88, 30, 30);
			graphics.setColor(species[generation][specimen].node3);
			graphics.fillRect(145, 143, 30, 30);
			graphics.setColor(species[generation][specimen].node4);
			graphics.fillRect(145, 198, 30, 30);
			graphics.setColor(species[generation][specimen].out1);
			graphics.fillRect(252, 61, 30, 30);
			graphics.setColor(species[generation][specimen].out2);
			graphics.fillRect(252, 170, 30, 30);
				
			graphics.setColor(hope1);
			graphics.fillRect(286, 61, 30, 30);
			graphics.setColor(hope2);
			graphics.fillRect(286, 170, 30, 30);
			graphics.drawImage(background, 0, 0, null);
			
			drawNumber(species[generation][specimen].specimen, 43, 5, graphics);
			drawNumber(species[generation][specimen].generation, 49, 11, graphics);
			drawNumber(species[generation][specimen].fitness, 95, 5, graphics);
			
			specimen++;
			if(specimen == species[generation].length & generation == species.length) {learning = false;}
			else if(specimen == species[generation].length & generation < species.length) {
				Specimen[] sortedSpecies = new Specimen[species[generation].length];
				
				int whilePos = 0;
				while(true) {
					Specimen top = species[generation][0];
					boolean pass = false;
					
					for(int xpos = 0; xpos < species[generation].length - whilePos; xpos++) {
						for(int ypos = 0; ypos < whilePos; ypos++) {
							if(sortedSpecies[ypos].specimen == xpos) {
								pass = true;
								break;
							}
						}
						if(pass) {break;}
						if(species[generation][xpos].fitness < top.fitness) {top = species[generation][xpos];}
					}
					sortedSpecies[whilePos] = top;
					
					whilePos++;
					if(whilePos == species[generation].length) {break;}
				}
				for(int pos = 0; pos < 50; pos++) {
					Specimen[] offspring = new Specimen[2];
					
					offspring = breed(sortedSpecies[(int)(Math.random() * 50)], sortedSpecies[(int)(Math.random() * 50)], generation + 1, new int[]{pos, pos + 50});
					species[generation + 1][pos] = offspring[0];
					species[generation + 1][pos + 50] = offspring[1];
				}
				
				generation++;
				specimen = 0;
			}
		}
		else {
			Specimen top = species[0][0];
			
			for(int xpos = 0; xpos < species.length; xpos++) {for(int ypos = 0; ypos < species[xpos].length; ypos++) {if(species[generation][xpos].fitness < top.fitness) {top = species[generation][xpos];}}}
		
			graphics.setColor(top.node1);
			graphics.fillRect(145, 33, 30, 30);
			graphics.setColor(top.node2);
			graphics.fillRect(145, 88, 30, 30);
			graphics.setColor(top.node3);
			graphics.fillRect(145, 143, 30, 30);
			graphics.setColor(top.node4);
			graphics.fillRect(145, 198, 30, 30);
			graphics.setColor(top.out1);
			graphics.fillRect(252, 61, 30, 30);
			graphics.setColor(top.out2);
			graphics.fillRect(252, 170, 30, 30);
				
			graphics.setColor(hope1);
			graphics.fillRect(286, 61, 30, 30);
			graphics.setColor(hope2);
			graphics.fillRect(286, 170, 30, 30);
			graphics.drawImage(background, 0, 0, null);
			
			drawNumber(top.specimen, 43, 5, graphics);
			drawNumber(top.generation, 49, 11, graphics);
			drawNumber(top.fitness, 95, 5, graphics);
		}
	}
	
	public void keyPressed(int key) {}
	public void keyReleased(int key) {}

	public void mouseClicked(MouseEvent mouse) {}
	public void mouseEntered(MouseEvent mouse) {}
	public void mouseExited(MouseEvent mouse) {}
	public void mousePressed(MouseEvent mouse) {}
	public void mouseReleased(MouseEvent mouse) {}
	
	void drawNumber(int number, int x, int y, Graphics2D graphics) {
		int[] newNumber;
		if(number >= 10000) {newNumber = new int[5];}
		else if(number >= 1000) {newNumber = new int[4];}
		else if(number >= 100) {newNumber = new int[3];}
		else if(number >= 10) {newNumber = new int[2];}
		else {newNumber = new int[1];}
		LinkedList<Integer> stack = new LinkedList<Integer>();
		
		while(number > 0) {
		    stack.push(number % 10);
		    number = number / 10;
		}
		int pos = 0;
		while(!stack.isEmpty()) {
			newNumber[pos] = stack.pop();
		    pos++;
		}
		
		for(int xpos = 0; xpos < newNumber.length; xpos++) {graphics.drawImage(font[newNumber[xpos]], x + xpos * 4, y, null);}
	}
	
	Specimen[] breed(Specimen parent1, Specimen parent2, int generation, int specimen[]) {
		Specimen[] offspring = new Specimen[specimen.length];
		
		for(int xpos = 0; xpos < specimen.length; xpos++) {
			double[] weights = new double[20];
			
			for(int ypos = 0; ypos < 20; ypos++) {
				if(Math.random() <= 0.5) {weights[ypos] = parent1.weights[ypos];}
				else {weights[ypos] = parent2.weights[ypos];}
				
				if(Math.random() <= 0.005) {
					if(Math.random() <= 0.5) {weights[ypos] += Math.random();}
					else {weights[ypos] -= Math.random();}
					if(weights[ypos] < 0) {weights[ypos] = 0;}
					if(weights[ypos] > 1) {weights[ypos] = 1;}
				}
			}
			
			offspring[xpos] = new Specimen(generation + 1, specimen[xpos], weights);
			offspring[xpos].fitness = Math.max(Math.abs(offspring[xpos].out1.getRed() - hope1.getRed()), 0) +  Math.max(Math.abs(offspring[xpos].out1.getGreen() - hope1.getGreen()), 0) +  Math.max(Math.abs(offspring[xpos].out1.getBlue() - hope1.getBlue()), 0) +
					 				  Math.max(Math.abs(offspring[xpos].out2.getRed() - hope2.getRed()), 0) +  Math.max(Math.abs(offspring[xpos].out2.getGreen() - hope2.getGreen()), 0) +  Math.max(Math.abs(offspring[xpos].out2.getBlue() - hope2.getBlue()), 0);
		}
		
		return offspring;
	}
}
