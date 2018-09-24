package jaddu;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Emage {
	
	public static final int TOP_MARGIN = 13;
	public static final int SPACE = 14;
	static int width;
	static String fileName;

	static ArrayList<Character> chars;
	static ArrayList<Integer> asciiChars;
	static BufferedImage originalImage;
	static File file = null;

	
	public static void main(String[] args) throws IOException {
		fileName=new String("output.png");
		String text=new String("Hello");
		if(text.length()<10)
			text= "0"+text.length() + text;
		else
			text= text.length() + text;
		
		chars = new ArrayList<Character>();
		asciiChars = new ArrayList<Integer>();
		originalImage = ImageIO.read(new File(fileName));

		
		width=originalImage.getWidth();
		encryptionImg(text);
	}
	
	public static void encryptionImg(String text) {
		int index = 0;

		//for each loop to add characters
		for (char c : text.toCharArray())
			chars.add(c);

		//loop to store ascii value of char
		for (char c : chars)
			asciiChars.add((int) c);

		
		for (int x = 0; x < width; x++) {
			if (x % SPACE == 0 && index < asciiChars.size()) {
				Color greenShade = new Color(1, asciiChars.get(index), 1);
				originalImage.setRGB(x, TOP_MARGIN, greenShade.getRGB());
				index++;
			}
		}

		try {

			file = new File("outputfile.png");
			ImageIO.write(originalImage, "png", file);
			System.out.println("done");
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	public int getImageWidth() {
		return this.width;
	}



}
