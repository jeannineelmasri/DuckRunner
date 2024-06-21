package duckrunner;
import processing.core.PApplet;
import processing.core.PImage;
public class DuckRunner extends PApplet{
    //to load and display images called duck, obstacles, and background
    PImage duck;
    PImage duckFlying;
    PImage background;
    PImage obstacles;

    //all of these variables define the positioning of each image
    //sets duck positioning
    int duckX = 70;
    int duckY = 300;
    //sets background
    int bgX = 0;
    int bgY = 0;
    //obstacles variables
    int obstaclesX;
    int obstaclesY = 330;
    //controls the speed of the duck
    float speed = 8;
    //controls the jump height of the duck
    int jumpHeight;

    //controls timer needed to keep track of start and end time
    int startTime;
    int timer;

    //increases speed and obstacles gradually
    float speedIncrease = 0.8f;
    float rateOfDifficulty = 5;
    int difficultyTime = 0;


    public static void main(String[] args){
        //PApplet class which creates a screen window
        PApplet.main("duckrunner.DuckRunner");

    }
    //sets the size of the window that we created
    public void settings(){
        size(700,400);
    }
    //loadImage method to add a path to your duck, obstacle, and background image
    //loads all the images we need and sets them equal to corresponding variables
    public void setup(){
        duck = loadImage("Images/Duck.png");
        background = loadImage("Images/Sky.png");
        obstacles = loadImage("Images/Hydrant.png");
        duckFlying = loadImage("Images/DuckFlying.png");
        //prevents the fire hydrant from looking too big next to the duck
        //putting a 0 means the image will resize proportionally to the other value
        //width set to 50, and height set to 0
        obstacles.resize(50,0);
        //millis() is the number of milliseconds that have passed since your program started running
        //
        startTime = millis();
    }

    public void draw(){
        //make sure to run drawBackground() before drawDuck() or the background will draw on top of the duck
        //remember to put these in order
        drawBackground();
        drawDuck();
        createObstacles();
        //number of millis is continually increasing
        //to get elapsed time subtract initial time from millis()
        timer = (millis() - startTime)/1000; //dividing by 1000 converts from millisecons to seconds
        drawScore();
        increaseDifficulty();
    }
    //draws the images on our screen
    public void drawDuck(){
        imageMode(CENTER);
        image(duck, duckX, duckY);
        //checks if duckY is <= 300
        if (duckY <=300){
            image(duckFlying, duckX, duckY);
            jumpHeight += 1;
            duckY += jumpHeight;
        }
    }
    //displays game background
    public void drawBackground(){
        imageMode(CORNER);
        image(background, bgX, bgY); //draws the background at specific x and y positions
        image(background, bgX + background.width, 0); //makes it so the image keeps on going and going
        bgX -=speed; //moves the background
        //loops the image back to the beginning as it moves to give the illusion it goes on forever
        if(bgX <=(background.width *- 1)){
            bgX = 0;
        }
    }
    //detects when the mouse is clicked
    //when mouse is pressed, the duck jumps
    public void mousePressed(){
        if (duckY >= 300){

            jumpHeight = -15;
            duckY += jumpHeight; //duck will jump up (y coordinate)
        }
    }

    //method that controls where the hydrants are marked and how to move them
    public void createObstacles(){
        imageMode(CENTER);
        image(obstacles, obstaclesX, obstaclesY);
        obstaclesX -= speed; //scrolls the obstacle across the screen the same way we did the background
        //to have obstacle wrap around to the start, create an if statement to check if the hydrant reaches 0.
        //if obstacle reaches 0, reset it to the beginning.
        if (obstaclesX < 0 ){
            obstaclesX = width;
        }
        if((abs(duckX-obstaclesX) < 40) && abs(duckY-obstaclesY)< 80 ) {
            //prints game over in the console
            System.out.println("Game over");
        }
    }

    public void drawScore(){
        //fill method sets the color of the text to white
        fill(255, 255, 255);
        //aligns the text in the middle of the screen
        textAlign(CENTER);
        //writes the text on the screen
        text("Score: " + timer, width-70, 30);
    }

    public void increaseDifficulty(){
        //makes sure speed increases every five seconds and checks how much time has passed
        //The timer % 5 will equal 0 if 5 divides equally into the timer.
        // So, for example, if the timer is 5, 10, 20, etc., timer % 5 will equal 0
        // indicating five seconds have passed.
        if (timer % rateOfDifficulty == 0 && difficultyTime != timer){
            //increase speed if rateOfDifficulty has passed
            speed += speedIncrease;
            difficultyTime = timer;
        }
    }
}
