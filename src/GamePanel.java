import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;


public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNIT = SCREEN_WIDTH * SCREEN_HEIGHT / (UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 75;//for timer
    final int[] x = new int[GAME_UNIT];
    final int[] y= new int[GAME_UNIT];
    int bodyParts = 6;
    int appleX;
    int appleY;
    int applesEaten;
    char direction = 'R';//'R'ight,'L'eft,'U'p,'D'own
    boolean running = false;
    Timer timer;
    Random random;



    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));//SPECIAL
        startGame();

    }
    public void startGame(){
        newApple();
        running = true; //by default, it was false
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){

            for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i ++) {
                //HORIZONAL LINES
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                //VERTICAL LINES
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        for(int i=0;i < bodyParts;i++){
            if (i==0){ //head of snake
                g.setColor(Color.green);
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);//head's location
                //x[i],y[i] are x[0], y[0] here
            }else {
                g.setColor(new Color(45, 180,0));
//                System.out.println("X:" + i + x[i]);
//                System.out.println("Y:" + i +y[i]);
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);//bodies' location
            }
        }
    }
    public void newApple(){ //create a new apple
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
        for(int i= bodyParts; i>0; i--){
            x[i] = x[i-1];//so each unit of the snake's body will concatenate and follow each other
            y[i] = y[i-1];
        }



        switch (direction){
            case 'U':
                y[0] = y[0]-UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0]+UNIT_SIZE;
                break;

            case 'R':
                x[0] = x[0]+UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0]-UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if(x[0]==appleX&&y[0]==appleY){
            bodyParts++;
            newApple();
        }

    }
    public void checkCollisions(){
        //if the body has 0 units
        for(int i=bodyParts; i>0; i--){
            if(x[0]==x[i]&&y[0]==y[i]){ //if head's location == bodies' location, both x axis and y axis
                running = false;
            }
        }
        //check if head touches the left side
        if(x[0]<UNIT_SIZE){
           running = false;
        }
        //check if head touches the right side
        if(x[0]>SCREEN_WIDTH-2*UNIT_SIZE){
            running = false;
        }
        //check if head touches the top
        if(y[0]<0){
           running = false;
        }
        //check if head touches the bottom
        if(y[0]>SCREEN_HEIGHT-2*UNIT_SIZE){
            running = false;
        }

        //timer stop
        if(!running){
            timer.stop();
        }

    }
    public void gameOver(Graphics g){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            //direction go to right
            if(e.getKeyCode()== KeyEvent.VK_RIGHT){
                direction = 'R';
            }
            //direction go to left
            if(e.getKeyCode()==KeyEvent.VK_LEFT){
                direction = 'L';
            }
            //direction go to down
            if(e.getKeyCode()==KeyEvent.VK_DOWN){
                direction = 'D';
            }
            //direction go to down
            if(e.getKeyCode()==KeyEvent.VK_UP){
                direction = 'U';
            }

        }
    }
}
