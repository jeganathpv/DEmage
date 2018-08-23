package jaddu;

import java.awt.Color;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/*
 * This class is used to decrypt data from the image.
 *
 * @author Jeganath PV
 *
 */
public class DecryptImage {

	/**
	 * This method decryption text message from given image.
	 * It reads level of shadow on green channel from pixel.
	 */
	public static String decryption(String fileName) throws IOException {
		  BufferedImage bufferedImage = ImageIO.read(new File(fileName));

		  int width = bufferedImage.getWidth();
		  String text = "";

		  ArrayList<Character> characters = new ArrayList<Character>();

		  //read the char from pixels and add in char
		  for (int pixel = 0; pixel < width; pixel++) {
			  if (pixel % Configuration.SPACE == 0) {
				  Color colorShade = new Color(bufferedImage.getRGB(pixel, Configuration.TOP_MARGIN), true);
				  characters.add((char) colorShade.getGreen());
			  }
		  }

		  for (char character : characters)
			  text += character;
		  
//		  Boolean bool=text.contains("?¡§¬­§¥¥¤§³µ¶³«££¢¡???? £¤¥£¡ ??  ¢©®¯­£???????~~??????|wos");
//			if(bool) {
//				text=text.replaceAll("?¡§¬­§¥¥¤§³µ¶³«££¢¡???? £¤¥£¡ ??  ¢©®¯­£???????~~??????|wos", "");
//			}
//			
		  return text;
	}
}
