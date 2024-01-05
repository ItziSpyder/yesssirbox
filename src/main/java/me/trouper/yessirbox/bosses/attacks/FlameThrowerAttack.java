package me.trouper.yessirbox.bosses.attacks;

import io.github.itzispyder.pdk.utils.misc.SoundPlayer;
import io.github.itzispyder.pdk.utils.raytracers.BlockDisplayRaytracer;
import io.github.itzispyder.pdk.utils.raytracers.CustomDisplayRaytracer;
import io.github.itzispyder.real.boss.Attack;
import io.github.itzispyder.real.boss.Boss;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.utils.DisplayUtils;
import me.trouper.yessirbox.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class FlameThrowerAttack implements Attack {

    @Override
    public void perform(Boss boss, World world, Location location, List<Player> list) {
        Vector vec = Utils.getNearestPlayer(boss.getLocation()).getEyeLocation().toVector().subtract(boss.getLivingEntity().getEyeLocation().toVector()).normalize();
        World w = boss.getWorld();
        CustomDisplayRaytracer.trace(boss.getLivingEntity().getEyeLocation(), vec,20,0.1, point -> {
            Location loc = point.getLoc();

            List<Entity> targets = new ArrayList<>(boss.getWorld().getNearbyEntities(loc, 0.5,0.5,0.5, entity -> entity instanceof LivingEntity living && !living.isDead() && living != boss.getLivingEntity()));

            targets.forEach(target -> {
                if (target instanceof LivingEntity living) {
                    spawnTempFireRing(living,boss,loc);
                }
            });

            w.spawnParticle(Particle.FLAME, loc, 1, 0,0,0, 0);
            w.spawnParticle(Particle.SMOKE_NORMAL, loc, 1, 0,0,0, 0.1);

            return !targets.isEmpty() || !loc.getBlock().isPassable();
        });
    }

    private void spawnTempFireRing(LivingEntity living, Boss boss, Location loc) {
        SoundPlayer hissSound = new SoundPlayer(loc, Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
        living.damage(4, boss.getLivingEntity());
        living.setFireTicks(120);
        hissSound.playWithin(10);
        boss.getWorld().spawnParticle(Particle.LAVA, loc, 4, 0,0,0, 0.1);
        living.getWorld().createExplosion(living.getLocation(),5F,false,false,boss.getLivingEntity());
        for (int θ = 0; θ < 360; θ++) {
            double x = (5 * Math.cos(Math.toRadians(θ)));
            double z = (5 * Math.sin(Math.toRadians(θ)));
            Location test = living.getLocation().clone().add(x,0,z);
            spawnTallFire(test);
        }
    }
        private void spawnTallFire(Location loc) {
            for (int i = 0; i < 4; i++) {
            Location mod = loc.clone().add(0,i,0);
            if (!loc.getBlock().getType().isAir()) return;
            loc.getBlock().setType(Material.SOUL_FIRE,false);
            Bukkit.getScheduler().runTaskLater(YessirBox.getInstance(), ()-> {
                if (!mod.getBlock().getType().equals(Material.SOUL_FIRE)) return;
                mod.getBlock().setType(Material.AIR);
            },60);
        }
    }
}
