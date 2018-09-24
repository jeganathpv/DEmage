package jaddu
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.ArrayList
import javax.imageio.ImageIO
object Dmage {
  val TOP_MARGIN = 13
  val SPACE = 14
  @Throws(IOException::class)
  @JvmStatic fun main(args:Array<String>) {
    val fileName = String("outputpython.png")
    print("Text inside the image is ")
    val bufferedImage = ImageIO.read(File(fileName))
    val width = bufferedImage.getWidth()
    val text = ""
    val characters = ArrayList<Char>()
    //read the char from pixels and add in char
    for (pixel in 0 until width)
    {
      if (pixel % SPACE == 0)
      {
        val colorShade = Color(bufferedImage.getRGB(pixel, Configuration.TOP_MARGIN), true)
        characters.add(colorShade.getGreen().toChar())
      }
    }
    println(text)
    for (character in characters)
    text += character
    val finalText = ""
    val textLen = text.substring(0, 2)
    val textLength = Integer.parseInt(textLen) + 2
    for (i in 2 until textLength)
    finalText += text.get(i)
    println(finalText)
  }
}