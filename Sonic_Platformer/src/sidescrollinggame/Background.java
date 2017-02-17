
package sidescrollinggame;

import java.awt.Color;
import java.awt.Graphics;

public class Background {
    
    public int rects[][] = {{0,645,267,165},{0,250,161,205},{161,357,152,90},{273,150,314,26},{851,191,215,88},{585,420,314,28},{375,697,158,103},{533,593,255,207},{909,593,257,207}};
    
    public Background(){
        
    }
    
    public void draw(Graphics g){
        g.setColor(Color.RED);
        for (int i = 0; i < 9; i++) {
            g.drawRect(rects[i][0],rects[i][1],rects[i][2],rects[i][3]);
        }
    }
    
    public int[] getPlat(int i){
        return rects[i];
    }
    public int[][] getPlatsList(){
        return rects;
    }


    
    
}
