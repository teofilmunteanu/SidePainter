import java.awt.*;

public class Bilet41 extends Frame{
    Toolkit tool;
    Image im;
    int ww,hh;
    Graphics graphics;

    Button btn;

    int circleCenterX, circleCenterY;

    boolean leftColored = false, rightColored = false;

    public static void main(String[] args){
        new Bilet41();
    }

    Bilet41(){
        //Initializare
        tool = getToolkit();
        Dimension res = tool.getScreenSize();
        ww = res.width;
        hh = res.height;
        setResizable(false);
        setTitle("Colorare puncte din panou");
        setLayout(null);
        setBackground(Color.lightGray);

        resize(ww, hh);
        move(0, 0);
        setVisible(true);

        im = createImage(ww, hh);
        graphics = im.getGraphics();


        btn = new Button("Draw");
        add(btn);
        btn.setBounds(ww/2-40, hh/2-200, 80, 30);
    }

    void DesenarePanou(){
        graphics.clearRect(0, 0, ww, hh);
        graphics.drawRect(ww/2-200, hh/2-150, 400, 300);
        graphics.setColor(Color.white);
        graphics.fillRect(ww/2-200, hh/2-150, 400, 300);


        int leftBorder = ww/2-200, rightBorder = ww/2+200;
        int topBorder = hh/2-150, bottomBorder = hh/2+150;
        graphics.setColor(Color.black);
        int diameter = 200;
        int posX = (int)(leftBorder + Math.random()*(rightBorder - leftBorder - diameter));
        int posY = (int)(topBorder + Math.random()*(bottomBorder - topBorder - diameter));
        
        circleCenterX = posX + diameter/2;
        circleCenterY = posY + diameter/2;

        graphics.drawOval(posX, posY, diameter, diameter);
        
        repaint();
    }

    void DesenareJumatate(int x){
        //graphics.fillOval(circleCenterX-30, circleCenterY-30, 60, 60);
        
        if(x < circleCenterX){
            //daca sunt pe stanga, dar dreapta e colorata, sterge dreapta
            if(rightColored){
                rightColored = false;

                graphics.setColor(Color.white);

                for(int i = circleCenterX; i<ww/2+200;i++){
                    for(int j = hh/2-150; j<=hh/2+150;j++){
                        if((i-circleCenterX)*(i-circleCenterX) + (j-circleCenterY)*(j-circleCenterY) > (100*100 + 1000)) //offset de 1000 pt a nu colora peste cerc
                        graphics.drawRect(i, j, 1, 1);
                    }
                }  
            }
            else{
                //daca stanga e deja colorata, se sterge
                if(leftColored){
                    graphics.setColor(Color.white);
                    leftColored = false;
                }
                else{
                    graphics.setColor(Color.green);
                    leftColored = true;
                }
    
                for(int i = ww/2-200; i<circleCenterX;i++){
                    for(int j = hh/2-150; j<=hh/2+150;j++){
                        if((i-circleCenterX)*(i-circleCenterX) + (j-circleCenterY)*(j-circleCenterY) > (100*100 + 1000)) //offset de 1000 pt a nu colora peste cerc
                        graphics.drawRect(i, j, 1, 1);
                    }
                }
            }  
        }


        if(x > circleCenterX){
            //daca sunt pe dreapta, dar stanga e colorata, sterge stanga
            if(leftColored){
                leftColored = false;

                graphics.setColor(Color.white);

                for(int i = ww/2-200; i<circleCenterX;i++){
                    for(int j = hh/2-150; j<=hh/2+150;j++){
                        if((i-circleCenterX)*(i-circleCenterX) + (j-circleCenterY)*(j-circleCenterY) > (100*100 + 1000)) //offset de 1000 pt a nu colora peste cerc
                        graphics.drawRect(i, j, 1, 1);
                    }
                }
                
            }
            else{
                //daca dreapta e deja colorata, sterge dreapta
                if(rightColored){
                    graphics.setColor(Color.white);
                    rightColored = false;
                }
                else{
                    graphics.setColor(Color.green);
                    rightColored = true;
                }
    
                for(int i = circleCenterX; i<ww/2+200;i++){
                    for(int j = hh/2-150; j<=hh/2+150;j++){
                        if((i-circleCenterX)*(i-circleCenterX) + (j-circleCenterY)*(j-circleCenterY) > (100*100 + 1000)) //offset de 1000 pt a nu colora peste cerc
                        graphics.drawRect(i, j, 1, 1);
                    }
                }
            } 
        }

        repaint();
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void update(Graphics g) {
        g.drawImage(im, 0, 0, this);
    }

    public boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_DESTROY) {
            System.exit(0);
        }
        else if(e.id == Event.ACTION_EVENT){
            if (e.target == btn) {
                DesenarePanou();
            }
        }

        return super.handleEvent(e);
    }

    public boolean mouseDown(Event e, int x, int y) {
        //daca e in dreptunghi
        if(x>ww/2-200 && x<ww/2+200 && y>= hh/2-150 && y<= hh/2+150){
            DesenareJumatate(x);
        }
        

        return true;
    }
}
