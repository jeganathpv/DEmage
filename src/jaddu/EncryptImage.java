package jaddu;

import java.awt.Color;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/*
 * This class is used to encrypt text message into image.
 *
 * @author Jeganath PV
 *
 */
public class EncryptImage {
	static int width;
	String fileName;

	ArrayList<Character> chars;
	ArrayList<Integer> asciiChars;
	BufferedImage originalImage;

	File file;

	/**
	 * Here all common variables for this class are initialized.
	 *
	 * @param fileName Name of file which will be destination of encrypted message
	 * @throws IOException
	 */
	EncryptImage(String fileName) throws IOException {
		chars = new ArrayList<Character>();
		asciiChars = new ArrayList<Integer>();
		originalImage = ImageIO.read(new File(fileName));

		file = null;
		this.width = originalImage.getWidth();
	}

	/**
	 * It encrypts data into green channel of pixel using configuration from Config class.
	 *
	 * @param text Text message to encrypt
	 * @see Config
	 */
	public void encryption(String text) {
		int index = 0;

		//for each loop to add characters
		for (char c : text.toCharArray())
			chars.add(c);

		//loop to store ascii value of char
		for (char c : chars)
			asciiChars.add((int) c);

		
		for (int x = 0; x < width; x++) {
			if (x % Configuration.SPACE == 0 && index < asciiChars.size()) {
				Color greenShade = new Color(1, asciiChars.get(index), 1);
				originalImage.setRGB(x, Configuration.TOP_MARGIN, greenShade.getRGB());
				index++;
			}
		}

		try {
			//output file of image after encryption
//			file = new File("D:\\workspace\\Image-Cipher-master\\images\\output1.png");
			file = new File("output1.png");
			ImageIO.write(originalImage, "png", file);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	public int getImageWidth() {
		return this.width;
	}
}
