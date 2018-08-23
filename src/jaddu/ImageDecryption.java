package jaddu;


import java.io.IOException;

public class ImageDecryption {
	public static void main(String[] args) throws IOException {
		//here you need to give the file name
		String fileName=new String("output1.png");
		DecryptImage decryptImage=new DecryptImage();
		System.out.println("Text inside the image is "+DecryptImage.decryption(fileName));
//		String hiddenData=DecryptImage.decryption(fileName);
//		Boolean bool=hiddenData.contains("?¡");
//		if(bool) {
//			hiddenData=hiddenData.replaceAll("?¡§¬­§¥¥¤§³µ¶³«££¢¡???? £¤¥£¡ ??  ¢©®¯­£???????~~??????|wos", "");
//		}
//		System.out.println(hiddenData);

	}

}
