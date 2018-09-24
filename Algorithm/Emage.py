from PIL import Image
MARGIN=13
SPACE=14
##pass image using Image.open
##input
im=Image.open("D:\workspace\Image-Encrypt-Decrypt\output.png")
pix=im.load()
##get width of the image
width=im.size[0]
text="helloworldimjaddu"
lenText=len(text)
if(lenText<10):
    text="0"+str(lenText)+text
else:
    text=str(lenText)+text
asciiChars=[ord(c) for c in text]
index=0
##encrytion process
x=0
while x<width:
    if(x%SPACE==0 and index<len(asciiChars)):
        pix[x,MARGIN]=(1,asciiChars[index],1)
        index+=1
    x+=1

##save image
##output
im.save("D:\workspace\Image-Encrypt-Decrypt\outputpython.png")


##finish
