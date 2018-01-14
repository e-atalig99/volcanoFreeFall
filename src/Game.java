//Elizaveta Atalig

import java.applet.Applet;
import java.applet.AudioClip;



public class Game
{
  private Grid grid;
  private int userCol;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private int type;
  private int score;
  private int extraScore;
  private double speed;
  private String userImage;
  private int currBack;
  private boolean protect;
  private int currMil;
  int avoidLevel;
  private static final AudioClip music=Applet.newAudioClip(Game.class.getResource("drum.wav"));
  
  
  
  public Game()
  {
    grid = new Grid(7,5);
    userCol = 3;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    type=0;
    score=0;
    extraScore=0;
    speed=300;
    updateTitle();
    userImage= "user.gif";
    grid.setBackground("intro.gif");
    currBack=1;
    protect=false;
    currMil=0;
    avoidLevel=7;
  }
  
  public void play()
  {
    grid.showMessageDialog("PLAY: CLICK OK");
    grid.setBackground("backk1.gif");
    grid.setImage(new Location(0, userCol), "user.gif");
  
    
        
    while (!isGameOver())
    { 
     if(msElapsed%8000==0){
         music.play();
     }
        
      grid.pause(100);
      handleKeyPress();
      if(msElapsed%100==0&&!protect)
          changeUser();
      if(msElapsed%5000==0&&avoidLevel>4){
          avoidLevel--;
      }
      if (msElapsed % speed == 0)
      {
        populateRightEdge();
        scrollLeft();
        changeBackground();
        
        
      }
      updateTitle();
      msElapsed += 100;
      if(protect&&msElapsed-currMil>10000){
          protect=false;
      }
    }
    gameOver();
  }
  
  public void changeUser(){
      if(userImage.equals("user.gif"))
          userImage="user2.gif";
      else
          userImage="user.gif";
      grid.setImage(new Location(0, userCol), userImage);
  }
  
  public void handleKeyPress()
  {
      int key= grid.checkLastKeyPressed();
      if(key==37&&userCol!=1){
          handleCollision(new Location(0,userCol-1));
          grid.setImage(new Location(0, userCol-1), userImage);
          grid.setImage(new Location(0,userCol), null);
          userCol-=1;
          
      }
      else if(key==39&&userCol!=grid.getNumCols()-2){
          handleCollision(new Location(0, userCol+1));
          grid.setImage(new Location( 0,userCol+1), userImage);
          grid.setImage(new Location(0,userCol), null);
          userCol+=1;
      }
          
  }
  
  public void generateBack()
  {
      int ranGet=(int)(Math.random()*(grid.getNumCols()*15));
      int ranAvo= (int)(Math.random()*(grid.getNumCols()*15));
      int ranGet2= (int)(Math.random()*(grid.getNumCols()*15));
      int ranGet3= (int)(Math.random()*(grid.getNumCols()*10));

      if(ranGet==ranAvo||ranAvo==ranGet2)
          ranAvo= (int)(Math.random()*(grid.getNumCols()*15));
      if(ranGet==ranGet2)
          ranGet2= (int)(Math.random()*(grid.getNumCols()*15));
      if(ranGet<grid.getNumCols()-1&&ranGet>0&&ranGet!=userCol)
          grid.setImage(new Location(0, ranGet), "get.gif");
      if(ranAvo<grid.getNumCols()-1&&ranAvo>0&&ranAvo!=userCol){
          if(300-score<500)
            grid.setImage(new Location(0, ranAvo ), "avoid.gif");
          else{
              if(ranAvo==2){
                 grid.setImage(new Location(0, ranAvo-1 ), "spike2.gif");
                 grid.setImage(new Location(0, ranAvo-2 ), "spike1.gif");
              }
              else if(ranAvo==grid.getNumCols()-2){
                  grid.setImage(new Location(0, ranAvo ), "spike3.gif");
                  grid.setImage(new Location(0, ranAvo+1), "spike4.gif");
              }
              else{
                  grid.setImage(new Location(0, ranAvo ), "tiki.gif");
              }
          }
      }
      if(ranGet2<grid.getNumCols()-1&&ranGet2>0&&ranGet2!=userCol)
          grid.setImage(new Location(0, ranGet2 ), "get1.gif");
      if(ranGet3<grid.getNumCols()-1&&ranGet3>0&&ranGet3!=userCol)
          grid.setImage(new Location(0, ranGet3 ), "get2.gif");
  }
   public void populateRightEdge()
  {
      int ranGet=(int)(Math.random()*(grid.getNumCols()*10));
      int ranAvo= (int)(Math.random()*(grid.getNumCols()*avoidLevel));
      int ranGet2= (int)(Math.random()*(grid.getNumCols()*10));
      int ranGet3= (int)(Math.random()*(grid.getNumCols()*10));
      
      if(ranGet==ranAvo||ranAvo==ranGet2)
          ranAvo= (int)(Math.random()*(grid.getNumCols()*15));
      if(ranGet==ranGet2)
          ranGet2= (int)(Math.random()*(grid.getNumCols()*15));
      if(ranGet<grid.getNumCols()-1&&ranGet>0)
          grid.setImage(new Location(grid.getNumRows()-1, ranGet), "get.gif");
      
      if(msElapsed%10000==0){
        int ranShield= (int)(Math.random()*(grid.getNumCols()*2)+1);
        if(ranShield<grid.getNumCols()-1&&ranShield>0)
            grid.setImage(new Location(grid.getNumRows()-1, ranShield), "shield.gif");
      }
      if(ranAvo<grid.getNumCols()-1&&ranAvo>0){
          if(300-score<500)
            grid.setImage(new Location(grid.getNumRows()-1, ranAvo ), "avoid.gif");
          else{
              if(ranAvo==2){
                 grid.setImage(new Location(grid.getNumRows()-1, ranAvo-1 ), "spike2.gif");
                 grid.setImage(new Location(grid.getNumRows()-1, ranAvo-2 ), "spike1.gif");

              }
              else if(ranAvo==grid.getNumCols()-2){
                  grid.setImage(new Location(grid.getNumRows()-1, ranAvo ), "spike3.gif");
                  grid.setImage(new Location(grid.getNumRows()-1, ranAvo+1 ), "spike4.gif");
              }
              else{
                  grid.setImage(new Location(grid.getNumRows()-1, ranAvo ), "tiki.gif");
              }
          }
             
      }
      if(ranGet2<grid.getNumCols()-1&&ranGet2>0)
          grid.setImage(new Location(grid.getNumRows()-1, ranGet2 ), "get1.gif");
      if(ranGet3<grid.getNumCols()-1&&ranGet3>0)
          grid.setImage(new Location(grid.getNumRows()-1, ranGet3 ), "get2.gif");
      
  }
 
          
                  
  
  public void scrollLeft()
  {
      handleCollision(new Location(1,userCol));
      for(int r=0;r<grid.getNumRows();r++){
          for(int c=0;c<grid.getNumCols();c++){
              if(grid.getImage(new Location(r,c))!=null){
                  if(r!=0){
                      grid.setImage(new Location(r-1, c),grid.getImage(new Location(r,c)));
                      grid.setImage(new Location(r,c),null);
                  }
                  else{
                      
                      grid.setImage(new Location(r,c), null);
                      
                  }
              }
              grid.setImage(new Location(0,userCol), userImage);
          }
      }
              
  }
  
  
  public void handleCollision(Location loc)
  {
      //System.out.println(loc);
      if(grid.getImage(loc)==null||grid.getImage(loc)==null)
          return;
      if(grid.getImage(loc)=="get.gif"){
          handleGet(0);
          //System.out.println(timesGet);
      }
      else if(!protect&&(grid.getImage(loc)=="avoid.gif"||grid.getImage(loc)=="spike2.gif"||grid.getImage(loc)=="spike3.gif"||grid.getImage(loc)=="tiki.gif")){
          timesAvoid++;
          //System.out.println(timesAvoid);
      }
      else if(grid.getImage(loc)=="get1.gif"){
          handleGet(1);
      }
      else if(grid.getImage(loc)=="get2.gif"){
          handleGet(2);
      }
      else if(grid.getImage(loc)=="shield.gif"){
          protect=true;
          userImage="userShield.gif";
          currMil=msElapsed;
      }
      grid.setImage(loc,null);
  }
  public void changeBackground(){
      if(currBack==7)
          currBack=1;
      else
          currBack++;
      grid.setBackground("backk"+currBack+".gif");
  }
  public void fly(){
      
      if(currBack==1)
          currBack=7;
      else
          currBack--;
      
      grid.setBackground("backk"+currBack+".gif");
  }
  public int getScore()
  {
    score= (int)(msElapsed*.01)-extraScore;
    return score;
  }
  
  public void updateTitle()
  {
    grid.setTitle("Height:  " + (300-getScore())+" Powerups: "+timesGet);
  }
  
  public boolean isGameOver()
  {
    if(timesAvoid==1||score>=300)
        return true;
    return false;
  }
  public void handleGet(int b){
      if(timesGet==0){
          type=b;
      }
      if(b==type)
          timesGet++;
      else{
         type=b;
         timesGet=1;
      }
      if(timesGet==3){
          powerUp(type);
          timesGet=0;
      }
  }
  public void powerUp(int a){
      userImage="userfly.gif";
      for(int i=0;i<grid.getNumRows();i++){
        for(int r=0;r<grid.getNumRows();r++){
            for(int c=0;c<grid.getNumCols();c++){
                if(grid.getImage(new Location(r,c))!=null){
                    if(r==0&&c==userCol){
                        
                    }    
                    else if(r!=grid.getNumRows()-1){
                        grid.setImage(new Location(r+1, c),grid.getImage(new Location(r,c)));
                        grid.setImage(new Location(r,c), null);
                    }
                    else{

                        grid.setImage(new Location(r,c), null);

                    }
                }
                grid.setImage(new Location(0,userCol), userImage);
             
            }
            generateBack();
            fly();
            grid.pause(50);
        }
        handleKeyPress();
        extraScore+=50;
      }  
      userImage="user.gif";
      grid.pause(100);
  }
  public void gameOver(){
      userImage="user.gif";
      grid.setBackground("bakneg.gif");
      grid.pause(500);
      grid.setBackground("back0.gif");
      grid.pause(500);
      grid.setBackground("backlava.gif");
      for(int r=0;r<grid.getNumRows()-2;r++){
          grid.setImage(new Location(r+1, userCol),userImage);
          grid.setImage(new Location(r,userCol), null);
          grid.pause(175);
              grid.setBackground("backlava3.gif");
          grid.pause(175);
              grid.setBackground("backlava2.gif");
          grid.pause(175);
              grid.setBackground("backlava.gif");
      }
      grid.setImage(new Location(grid.getNumRows()-2,userCol), "burn.gif");
      grid.pause(175);
      grid.setBackground("backlava3.gif");
      grid.pause(175);
      grid.setBackground("backlava2.gif");
      grid.pause(175);
      grid.setBackground("backlava.gif");
      grid.pause(175);
      grid.setBackground("backlava2.gif");
      grid.setImage(new Location(grid.getNumRows()-2, userCol),"skel.gif");
      grid.pause(175);
      grid.setBackground("backlava.gif");
      grid.setImage(new Location(grid.getNumRows()-3, userCol),"ghost.gif");
      grid.pause(500);
      for(int r=grid.getNumRows()-3;r>0;r--){
          grid.setImage(new Location(r-1, userCol),"ghost.gif");
          grid.setImage(new Location(r,userCol), null);
          grid.pause(175);
              grid.setBackground("backlava3.gif");
          grid.pause(175);
              grid.setBackground("backlava2.gif");
          grid.pause(175);
              grid.setBackground("backlava.gif");
      }
      grid.showMessageDialog("GAME OVER: SCORE= "+(300-getScore()));
  }
  
  public static void test()
  {
    Game game = new Game();
    game.play();
  }
  
  public static void main(String[] args)
  {
    test();
  }
}