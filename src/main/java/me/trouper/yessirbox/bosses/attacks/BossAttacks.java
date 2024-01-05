package me.trouper.yessirbox.bosses.attacks;

import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import io.github.itzispyder.pdk.utils.misc.Randomizer;
import io.github.itzispyder.pdk.utils.misc.SoundPlayer;
import io.github.itzispyder.pdk.utils.raytracers.BlockDisplayRaytracer;
import io.github.itzispyder.pdk.utils.raytracers.CustomDisplayRaytracer;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.itzispyder.pdk.utils.StringUtils.color;

public class BossAttacks {



    public static void flamethrower(LivingEntity boss) {
        Vector vec = Utils.getNearestPlayer(boss.getLocation()).getEyeLocation().toVector().subtract(boss.getEyeLocation().toVector()).normalize();
        World w = boss.getWorld();
        CustomDisplayRaytracer.trace(boss.getEyeLocation(),vec,20,0.1, point -> {
            Location loc = point.getLoc();

            List<Entity> targets = new ArrayList<>(boss.getWorld().getNearbyEntities(loc, 0.5,0.5,0.5, entity -> entity instanceof LivingEntity living && !living.isDead() && living != boss));

            SoundPlayer hissSound = new SoundPlayer(loc, Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);

            targets.forEach(target -> {
                if (target instanceof LivingEntity living) {
                    living.damage(4, boss);
                    living.setFireTicks(120);
                    hissSound.playWithin(10);
                    boss.getWorld().spawnParticle(Particle.LAVA, loc, 4, 0,0,0, 0.1);
                    living.getWorld().createExplosion(living.getLocation(),5F,true,false,boss);
                }
            });
            //Bukkit.broadcast(Component.text("Spawning particle at location: " + loc));
            w.spawnParticle(Particle.FLAME, loc, 1, 0,0,0, 0);
            w.spawnParticle(Particle.SMOKE_NORMAL, loc, 1, 0,0,0, 0.1);
            return !targets.isEmpty() || !loc.getBlock().isPassable();
        });
        BlockDisplayRaytracer.trace(Material.ORANGE_STAINED_GLASS,boss.getEyeLocation(),Utils.getNearestPlayer(boss.getLocation()).getEyeLocation(),0.5,1);
    }
}
