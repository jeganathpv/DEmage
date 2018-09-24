package jaddu
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.ArrayList
import javax.imageio.ImageIO
class Emage {
  val imageWidth:Int
  get() {
    return this.width
  }
  companion object {
    val TOP_MARGIN = 13
    val SPACE = 14
    internal var width:Int = 0
    internal var fileName:String
    internal var chars:ArrayList<Char>
    internal var asciiChars:ArrayList<Int>
    internal var originalImage:BufferedImage
    internal var file:File = null
    @Throws(IOException::class)
    @JvmStatic fun main(args:Array<String>) {
      fileName = String("output.png")
      val text = String("Hello")
      if (text.length < 10)
      text = "0" + text.length + text
      else
      text = text.length + text
      chars = ArrayList<Char>()
      asciiChars = ArrayList<Int>()
      originalImage = ImageIO.read(File(fileName))
      width = originalImage.getWidth()
      encryptionImg(text)
    }
    fun encryptionImg(text:String) {
      val index = 0
      //for each loop to add characters
      for (c in text.toCharArray())
      chars.add(c)
      //loop to store ascii value of char
      for (c in chars)
      asciiChars.add(c.toInt())
      for (x in 0 until width)
      {
        if (x % SPACE == 0 && index < asciiChars.size())
        {
          val greenShade = Color(1, asciiChars.get(index), 1)
          originalImage.setRGB(x, TOP_MARGIN, greenShade.getRGB())
          index++
        }
      }
      try
      {
        file = File("outputfile.png")
        ImageIO.write(originalImage, "png", file)
        println("done")
      }
      catch (e:IOException) {
        println("Error: " + e)
      }
    }
  }
}