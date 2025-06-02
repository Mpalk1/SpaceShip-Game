import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BulletManager implements Updatable {
    final List<Bullet> playerBullets = Collections.synchronizedList(new ArrayList<>());
    final List<Bullet> EnemyBullets = Collections.synchronizedList(new ArrayList<>());
    GamePanel gp;
    int cooldown = 200;
    int enemy_cooldown = 2;
    long startTime = System.currentTimeMillis();
    long enemy_startTime = (int) System.currentTimeMillis() / 1000;


    public BulletManager(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void setup() {

    }

    @Override
    public void update() {
        if (!gp.BulletM.playerBullets.isEmpty()) {
            Iterator<Bullet> it_bullets = gp.BulletM.playerBullets.iterator();
            while (it_bullets.hasNext()) {
                Bullet bullet = it_bullets.next();
                bullet.update();
                if (bullet.shouldRemove()) {
                    it_bullets.remove();
                    System.out.println("bullet removed");
                }
            }
        }
        if (!gp.BulletM.EnemyBullets.isEmpty()) {
            Iterator<Bullet> it_bullets = gp.BulletM.EnemyBullets.iterator();
            while (it_bullets.hasNext()) {
                Bullet bullet = it_bullets.next();
                bullet.update();
                if (bullet.shouldRemove()) {
                    it_bullets.remove();
                    System.out.println("bullet removed");
                }
            }
        }


    }

    public void spawnPlayerBullet() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        if (System.currentTimeMillis() - cooldown >= startTime) {
//            startTime = System.currentTimeMillis();
//            playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation, gp, Bullet.player_bullet));
//            gp.SoundManager.playShootingSound();
//        }
        switch (Main.ship.selectedGun){
            case "default":
                if (System.currentTimeMillis() - Main.ship.cooldowns.get("default") >= startTime) {
                    startTime = System.currentTimeMillis();
                    playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation, gp, Bullet.player_bullet, 25));
                    gp.SoundManager.playShootingSound();
                }
                break;
            case "rifle":
                if (System.currentTimeMillis() - Main.ship.cooldowns.get("rifle") >= startTime) {
                    startTime = System.currentTimeMillis();
                    playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation, gp, Bullet.player_bullet, 40));
                    gp.SoundManager.playShootingSound();
                }
                break;
            case "shotgun":
                if (System.currentTimeMillis() - Main.ship.cooldowns.get("shotgun") >= startTime) {
                    startTime = System.currentTimeMillis();
                    playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation, gp, Bullet.player_bullet, 20));
                    playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation + Math.PI/40, gp, Bullet.player_bullet, 20));
                    playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation + Math.PI/20, gp, Bullet.player_bullet, 20));
                    playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation - Math.PI/40, gp, Bullet.player_bullet,20));
                    playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation - Math.PI/20, gp, Bullet.player_bullet,20));
                    gp.SoundManager.playShootingSound();
                }
                break;
            case "sniper":
                if (System.currentTimeMillis() - Main.ship.cooldowns.get("sniper") >= startTime) {
                    startTime = System.currentTimeMillis();
                    playerBullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, Main.ship.rotation, gp, Bullet.player_bullet, 100));
                    gp.SoundManager.playShootingSound();
                }
                break;
        }
    }

    public void spawnEnemyBullet() {
        synchronized (gp.EnemyM.enemies){
            for(Enemy enemy: gp.EnemyM.enemies){
                if(System.currentTimeMillis()/1000.0 - enemy.cooldown >= enemy.start_time){
                    enemy.start_time = System.currentTimeMillis() / 1000.0;
                    EnemyBullets.add(new Bullet(enemy.center_x, enemy.center_y, enemy.rotation, gp, Bullet.enemy_bullet, 25));
                }
            }
        }
    }

}
