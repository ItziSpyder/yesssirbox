package me.trouper.yessirbox.bosses.attacks;

import io.github.itzispyder.real.boss.Attack;
import io.github.itzispyder.real.boss.Boss;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Comparator;
import java.util.List;

public class FireBallAttack implements Attack {
    public static void shootFireball(Player target, Location from) {
        Fireball fb = from.getWorld().spawn(from,Fireball.class);
        Vector vec = target.getEyeLocation().toVector().subtract(from.toVector()).normalize();
        fb.setDirection(vec);
        fb.setVelocity(vec.multiply(1.5));
    }

    @Override
    public void perform(Boss boss, World world, Location location, List<Player> list) {
        Player nearest = list.stream().sorted(Comparator.comparing(p->p.getLocation().distance(location))).toList().get(0);
        shootFireball(nearest,boss.getLivingEntity().getEyeLocation());
    }
}
