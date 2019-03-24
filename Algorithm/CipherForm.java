package placement;

public class CipherForm {
	
	public static String encryptText(String text) {
		//Adding # to the string
		if(text.length()%2!=0) {
			text+="#";
		}
		//Reversing the string
		String reverse = "";
        for(int i = text.length() - 1; i >= 0; i--)
        {
            reverse = reverse + text.charAt(i);
        }
        text=reverse;
        //Split string into two substring
        int textLen=text.length();
        String textA="",textB="";
        for(int i=0;i<(textLen/2);i++) {
        	textA+=text.charAt(i);
        }
        for(int i=(textLen/2);i<text.length();i++) {
        	textB+=text.charAt(i);
        }
        //Merge two substrings
        text=textB+textA;
        
        //Change Characters in even to position 
        char[] textChar=text.toCharArray();
        for(int i=0;i<text.length();i++) {
        	if(i%2==0) {
        		textChar[i]++;
        	}
        }
        //Change First and Last Char
        char temp=textChar[0];
        textChar[0]=textChar[textLen-1];
        textChar[textLen-1]=temp;
        
        //Make Char array to string
        text=new String(textChar);
        
        System.out.println(text);
        return text;
	}
	public static void decryptText(String text) {
		
        int textLen=text.length();
		//Change Characters in even to position 
        char[] textChar=text.toCharArray();
        
        char temp=textChar[0];
        textChar[0]=textChar[textLen-1];
        textChar[textLen-1]=temp;
        
        for(int i=0;i<text.length();i++) {
        	if(i%2==0) {
        		textChar[i]--;
        	}
        }
        text=new String(textChar);
        
        
		//Split string into two substring
        String textA="",textB="";
        for(int i=0;i<(textLen/2);i++) {
        	textA+=text.charAt(i);
        }
        for(int i=(textLen/2);i<text.length();i++) {
        	textB+=text.charAt(i);
        }
        //Merge two substrings
        text=textB+textA;
		//Reversing the string
		String reverse = "";
        for(int i = text.length() - 1; i >= 0; i--)
        {
            reverse = reverse + text.charAt(i);
        }
        text=reverse;
        //Removing # from the string
		if(text.length()%2==0) {
			if(text.contains("#")) {
				text=text.replace("#","");
			}
		}
		System.out.println(text);
	}
	
	public static void main(String[] args) {
		String text="Hello";
		System.out.println("Orginal Text is "+text);
		System.out.println("Encrypt Text");
		String cipher=encryptText(text);
		System.out.println("Decrypt Text");
		decryptText(cipher);
		
//		char c='a';
//		c++;
//		System.out.println(c+"");
	}
	
}
