package sidescrollinggame;

public class Character {
    int x, y;
    int velX=0, velY = 0;
    //constructor for character
    public Character() {
        //initialize x,y coords
        x = 0;
        y = 595;
    }

    //moves the character right by increasing their x position by 3
    public void moveRight() {
        x += velX;
    }
    
    //moves the character left by decreasing their x position by 3
    public void moveLeft() {
        x -= velY;
    }
    
    
    //return xpos of character
    public int getX(){
        return x;
    }
    //return ypos of character
    public int getY(){
        return y;
    }
    


}


