package jaddu;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Dmage {
	public static final int TOP_MARGIN = 13;
	public static final int SPACE = 14;
	public static void main(String[] args) throws IOException{
		String fileName=new String("outputpython.png");
		System.out.print("Text inside the image is ");
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

		  
		  System.out.println(text);
		  for (char character : characters)
			  text += character;
		  
		  String finalText="";
		  String textLen=text.substring(0, 2);
		  int textLength=Integer.parseInt(textLen)+2;
		  for(int i=2;i<textLength;i++)
			  finalText+=text.charAt(i);
		  
		  System.out.println(finalText);

	}

	

}
