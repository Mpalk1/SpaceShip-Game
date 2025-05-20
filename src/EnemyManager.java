import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyManager implements Updatable{
    public final List<Enemy> enemies = Collections.synchronizedList(new ArrayList<>());
    GamePanel gp;

    public EnemyManager(GamePanel gp){
        this.gp = gp;
    }

    public void spawnEnemy(int pos_x, int pos_y, int HP, int speed, long cooldown, PlayerShip player) {
        Enemy enemy = new Enemy(pos_x, pos_y, HP, speed, player, gp);
        enemies.add(enemy);
    }

    public void spawnEnemyRandom(int HP) {
        Random random = new Random();
        int pos_x;
        int pos_y;
        if (ThreadLocalRandom.current().nextBoolean()) {
            pos_x = random.nextInt(Enemy.width, 200);
            pos_y = random.nextInt(Enemy.height, GamePanel.SCREEN_HEIGHT - Enemy.height);
        } else {
            pos_x = random.nextInt(GamePanel.SCREEN_WIDTH - 200, GamePanel.SCREEN_WIDTH - Enemy.width);
            pos_y = random.nextInt(Enemy.height, GamePanel.SCREEN_HEIGHT - Enemy.height);
        }
        Enemy enemy = new Enemy(pos_x, pos_y, HP, 2, Main.ship, gp);
        enemies.add(enemy);
    }

    @Override
    public void setup() {

    }

    @Override
    public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
            Iterator<Enemy> it_enemy = enemies.iterator();
            while(it_enemy.hasNext()){
                Enemy enemy = it_enemy.next();
                enemy.update();
                if(enemy.shouldRemove()){
                    it_enemy.remove();
                    gp.score_cnt += 25;
                }
            }
            if (enemies.isEmpty()) {
                for (int i = 0; i < 5; i++) {
                   spawnEnemyRandom(100);
                }
            }
        gp.BulletM.spawnEnemyBullet();

    }
}
