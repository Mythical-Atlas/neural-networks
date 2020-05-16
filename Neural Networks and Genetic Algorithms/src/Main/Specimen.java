package Main;

import java.awt.Color;

public class Specimen {
	public int generation;
	public int specimen;
	public int fitness;
	public Color node1;
	public Color node2;
	public Color node3;
	public Color node4;
	public Color out1;
	public Color out2;
	public double[] weights;
	
	public Specimen(int generation, int specimen, double[] weights) {
		this.generation = generation;
		this.specimen = specimen;
		this.weights = weights;
		
		node1 = new Color((int)(255 * weights[0]),
						  (int)(255 * weights[1]),
						  (int)(255 * weights[2]));
		node2 = new Color((int)(255 * weights[3]),
						  (int)(255 * weights[4]),
						  (int)(255 * weights[5]));
		node3 = new Color((int)(255 * weights[6]),
						  (int)(255 * weights[7]),
						  (int)(255 * weights[8]));
		node4 = new Color((int)(255 * weights[9]),
						  (int)(255 * weights[10]),
						  (int)(255 * weights[11]));
		
		out1 = new Color((int)(Math.min(node1.getRed() * weights[12] + node2.getRed() * weights[13] + node3.getRed() * weights[14] + node4.getRed() * weights[15], 255)),
						 (int)(Math.min(node1.getGreen() * weights[12] + node2.getGreen() * weights[13] + node3.getGreen() * weights[14] + node4.getGreen() * weights[15], 255)),
						 (int)(Math.min(node1.getBlue() * weights[12] + node2.getBlue() * weights[13] + node3.getBlue() * weights[14] + node4.getBlue() * weights[15], 255)));
		out2 = new Color((int)(Math.min(node1.getRed() * weights[16] + node2.getRed() * weights[17] + node3.getRed() * weights[18] + node4.getRed() * weights[19], 255)),
						 (int)(Math.min(node1.getGreen() * weights[16] + node2.getGreen() * weights[17] + node3.getGreen() * weights[18] + node4.getGreen() * weights[19], 255)),
						 (int)(Math.min(node1.getBlue() * weights[16] + node2.getBlue() * weights[17] + node3.getBlue() * weights[18] + node4.getBlue() * weights[19], 255)));
	}
}
