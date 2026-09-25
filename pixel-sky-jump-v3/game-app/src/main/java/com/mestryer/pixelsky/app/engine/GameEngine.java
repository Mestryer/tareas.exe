package com.mestryer.pixelsky.app.engine;
import com.mestryer.pixelsky.core.model.*;
import lombok.Getter;
import java.util.*;

@Getter

public class GameEngine {
    private final Player player=Player.builder().id("player").name("Mestryer").x(100).y(300).width(42).height(60).lives(3).score(1996).build();
    private Level level;
    private final List<FloatingIsland> islands=new ArrayList<>();
    private final List<Coin> coins=new ArrayList<>();
    private final List<Enemy> enemies=new ArrayList<>();
    private int levelNumber=1;
    private boolean started,gameOver,complete;
    private double cameraX,invuln;

    public void load(Level l,int n){level=l;levelNumber=n;islands.clear();coins.clear();enemies.clear();
        islands.addAll(l.getIslands().stream().map(i->FloatingIsland.builder().id(i.getId()).x(i.getX()).y(i.getY()).width(i.getWidth()).height(i.getHeight()).build()).toList());
        coins.addAll(l.getCoins().stream().map(c->Coin.builder().id(c.getId()).x(c.getX()).y(c.getY()).value(c.getValue()).build()).toList());
        enemies.addAll(l.getEnemies().stream().map(e->Enemy.builder().id(e.getId()).name(e.getName()).x(e.getX()).y(e.getY()).width(e.getWidth()).height(e.getHeight()).leftLimit(e.getLeftLimit()).rightLimit(e.getRightLimit()).speed(e.getSpeed()).alive(true).build()).toList());
        player.setX(100);player.setY(300);player.setVelocityX(0);player.setVelocityY(0);player.setLives(3);
        started=false;
        gameOver=false;
        complete=false;
        cameraX=0;
        invuln=0;
        player.setInvulnerable(false);
    }

    public void start(){
        started=true;
    }

    public void update(double dt,boolean left,boolean right,boolean jump) {
        if(!started||gameOver||complete)
            return;
        if(left)player.setVelocityX(player.getVelocityX()-900*dt);
        if(right)player.setVelocityX(player.getVelocityX()+900*dt);
        if(!left&&!right)player.setVelocityX(player.getVelocityX()*Math.pow(.001,dt));player.setVelocityX(clamp(player.getVelocityX(),-260,260));
        if(jump&&player.isGrounded()){player.setVelocityY(-590);player.setGrounded(false);
        }

        player.setVelocityY(player.getVelocityY()+1350*dt);
        player.setX(player.getX()+player.getVelocityX()*dt);player.setY(player.getY()+player.getVelocityY()*dt);player.setGrounded(false);
        for(FloatingIsland i:islands)
            if(player.getVelocityY()>=0&&player.getX()+player.getWidth()>i.getX()&&player.getX()<i.getX()+i.getWidth()&&player.getY()+player.getHeight()<=i.getY()+18&&player.getY()+player.getHeight()+player.getVelocityY()*dt>=i.getY()){player.setY(i.getY()-player.getHeight());player.setVelocityY(0);player.setGrounded(true);
        }

        for(Coin c:coins)
            if(!c.isCollected()&&hit(player.getX(),player.getY(),player.getWidth(),player.getHeight(),c.getX()-10,c.getY()-10,20,20)){c.setCollected(true);
            player.setScore(player.getScore()+c.getValue());
        }

        for(Enemy e:enemies)
            if(e.isAlive()){e.setX(e.getX()+e.getSpeed()*dt);
            if(e.getX()<e.getLeftLimit()||e.getX()+e.getWidth()>e.getRightLimit()){e.setSpeed(-e.getSpeed());e.setX(clamp(e.getX(),e.getLeftLimit(),e.getRightLimit()-e.getWidth()));
        }

        if(hit(player.getX()+5,player.getY()+5,player.getWidth()-10,player.getHeight()-10,e.getX(),e.getY(),e.getWidth(),e.getHeight())){
            if(player.getVelocityY()>0&&player.getY()+player.getHeight()-e.getY()<20){e.setAlive(false);player.setVelocityY(-430);player.setScore(player.getScore()+250);
        }

    else if(invuln<=0)loseLife();
        }
        }

        if(invuln>0)invuln-=dt; player.setInvulnerable(invuln > 0);
        if(player.getY()>950)loseLife();player.setX(clamp(player.getX(),0,level.getWidth()-player.getWidth()));
        if(coins.stream().allMatch(Coin::isCollected)){complete=true;
            player.setScore(player.getScore()+500);}cameraX=clamp(player.getX()-350,0,Math.max(0,level.getWidth()-1280));
    }
    private void loseLife(){player.setLives(player.getLives()-1);
        if(player.getLives()<=0){gameOver=true;
            return;
        }
        player.setX(100);player.setY(250);player.setVelocityX(0);player.setVelocityY(0);invuln=2; player.setInvulnerable(true);
    }
    private boolean hit(double ax,double ay,double aw,double ah,double bx,double by,double bw,double bh){
        return ax<bx+bw&&ax+aw>bx&&ay<by+bh&&ay+ah>by;
    }
    private double clamp(double v,double a,double b){return Math.max(a,Math.min(b,v));
    }
}