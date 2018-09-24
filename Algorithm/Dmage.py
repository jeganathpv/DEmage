from PIL import Image
MARGIN=13
SPACE=14


##input
##pass the encrypted text
im=Image.open("D:\workspace\Image-Encrypt-Decrypt\outputpython.png")
pix=im.load()

##get width of the image
width=im.size[0]

text=""

##temp list to store all the char
textList=[]

##decryption process
pixel=0
while (pixel<width):
    if(pixel%SPACE==0):
        textList.append(chr(pix[pixel,MARGIN][1]))
    pixel+=1

##to find the length of encrypted text
textLen= textList[0]+textList[1]
textLen=int(textLen)+2

##to get only encrypted text
i=2
while(i<textLen):
    text=text+textList[i]
    i+=1

print("Encrypted Text is",text)
