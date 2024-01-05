package me.trouper.yessirbox.events;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.events.CustomListener;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.bosses.attacks.BossAttacks;
import me.trouper.yessirbox.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.concurrent.atomic.AtomicInteger;

public class BossStagesEvent implements CustomListener, Global {
    public static Boolean isRunning = false;

    public void startBoss(WitherSkeleton boss) {
        isRunning = true;
        AtomicInteger scheduler = new AtomicInteger();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(YessirBox.getInstance(),()->{
            if (!isRunning || boss.isDead()) return;
            double health = Utils.getHealth(boss);
            if (health >= 0.9 && health <= 1) {
                showStage("1/10");
                if (!(scheduler.incrementAndGet() % 20 == 0)) return;

                //BossAttacks.fireBallNearest(boss);
            }
            if (health >= 0.8 && health <= 0.9) {
                showStage("2/10");
                if (!(scheduler.incrementAndGet() % 80 == 0)) return;

                //BossAttacks.spawnMinions(boss);
            }
            if (health >= 0.7 && health <= 0.8) {
                showStage("3/10");
                if (!(scheduler.incrementAndGet() % 30 == 0)) return;

                BossAttacks.flamethrower(boss);
            }
            if (health >= 0.6 && health <= 0.7) {
                showStage("4/10");


            }
            if (health < 0.1) isRunning = false;
        },0,1);
    }

    public void showStage(String stage) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendActionBar(Component.text(color("&4&lBoss Stage: &f&n" + stage)));
        });
    }
}
