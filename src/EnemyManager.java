import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyManager {
    public final List<Enemy> enemies = Collections.synchronizedList(new ArrayList<>());

    public void spawnEnemy(int pos_x, int pos_y, int HP, int speed, long cooldown, PlayerShip player) {
        Enemy enemy = new Enemy(pos_x, pos_y, HP, speed, cooldown, player);
        enemies.add(enemy);
    }

    public void spawnEnemyRandom(int HP) {
        Random random = new Random();
        int pos_x;
        int pos_y;
        if(ThreadLocalRandom.current().nextBoolean()){
            pos_x = random.nextInt(Enemy.width, 200);
            pos_y = random.nextInt(Enemy.height, GamePanel.SCREEN_HEIGHT - Enemy.height);
        }
        else{
            pos_x = random.nextInt(GamePanel.SCREEN_WIDTH-200, GamePanel.SCREEN_WIDTH - Enemy.width);
            pos_y = random.nextInt(Enemy.height, GamePanel.SCREEN_HEIGHT - Enemy.height);
        }
        Enemy enemy = new Enemy(pos_x, pos_y, HP, 2, 3000, Main.ship);
        enemies.add(enemy);
    }
}
