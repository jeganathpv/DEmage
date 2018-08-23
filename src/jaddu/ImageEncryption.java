package jaddu;

import java.io.IOException;

public class ImageEncryption {
	public static void main(String[] args) {
		//here you need to give the file name which file yu need to encrypt
		String file=new String("D:\\workspace\\Image-Cipher-master\\images\\github_logo.jpg");
		String text=new String("Hello I'm Jaddu:)");
		try {
			EncryptImage encryptImage= new EncryptImage(file);
			encryptImage.encryption(text);
			System.out.println("Text Successfully Encrypted...:)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

}
