package me.trouper.yessirbox.events;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.events.CustomListener;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import io.github.itzispyder.pdk.utils.misc.Randomizer;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.commands.BossCommand;
import me.trouper.yessirbox.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BossStagesEvent implements CustomListener, Global {
    public static Boolean isRunning = false;
    @EventHandler
    public void stageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof WitherSkeleton ent)) return;

        if (ent.customName().equals(BossCommand.bossName) && !isRunning){
            Bukkit.broadcast(Component.text("Attempting to start boss..."));
            startBoss(ent);
        }
    }
    @EventHandler
    public void deathEvent(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof WitherSkeleton ent)) return;

        if (ent.customName().equals(BossCommand.bossName)){
            isRunning = false;
            Bukkit.getOnlinePlayers().forEach(p -> {
                var bars = p.activeBossBars();
                bars.forEach(p::hideBossBar);
                p.sendActionBar(Component.text(color("&4&lBoss Stage: &f&nDefeated")));
            });

        }
    }

    public void startBoss(WitherSkeleton ent) {
        isRunning = true;
        AtomicInteger scheduler = new AtomicInteger();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(YessirBox.getInstance(),()->{
            if (!isRunning || ent.isDead()) return;
            double health = Utils.getHealth(ent);
            if (health >= 0.9 && health <= 1) {
                if (!(scheduler.incrementAndGet() % 20 == 0)) return;
                showStage("1");
                List<Entity> near = ent.getNearbyEntities(30,30,30).stream().filter(entity -> entity instanceof Player).toList();
                Randomizer rand = new Randomizer();
                Player player = (Player) rand.getRandomElement(near);
                AtomicInteger time = new AtomicInteger();
                Bukkit.getScheduler().scheduleSyncRepeatingTask(YessirBox.getInstance(),()->{
                    if (time.getAndIncrement() > 5) return;
                    shootFireball(player,ent.getEyeLocation());
                },0,1);
            }
            if (health <= 0.8) {
                if (!(scheduler.incrementAndGet() % 80 == 0)) return;
                showStage("2");
                Randomizer rand = new Randomizer();
                double xOff = ent.getX() + rand.getRandomDouble(-10,10);
                double zOff = ent.getZ() + rand.getRandomDouble(-10,10);
                Location minionLoc = new Location(ent.getWorld(),xOff,ent.getLocation().getY(),zOff);

                spawnBossMinion(minionLoc);
            }
            if (health < 0.1) isRunning = false;
        },0,1);
    }

    public void showStage(String stage) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendActionBar(Component.text(color("&4&lBoss Stage: &f&n" + stage)));
        });
    }

    public void shootFireball(Player target, Location from) {
        Fireball fb = from.getWorld().spawn(from,Fireball.class);
        Vector vec = target.getEyeLocation().toVector().subtract(from.toVector()).normalize();
        fb.setDirection(vec);
        fb.setVelocity(vec.multiply(1.5));
    }

    public void spawnBossMinion(Location loc) {
        World w = loc.getWorld();

        Skeleton minion = w.spawn(loc.clone().add(0,-2,0),Skeleton.class);
        minion.customName(Component.text(color("&0[&8Skeleton Minion&0]")));
        minion.getEquipment().setHelmet(new ItemBuilder().material(Material.NETHERITE_HELMET).enchant(Enchantment.VANISHING_CURSE,1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
        minion.getEquipment().setChestplate(new ItemBuilder().material(Material.NETHERITE_CHESTPLATE).enchant(Enchantment.VANISHING_CURSE,1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
        minion.getEquipment().setLeggings(new ItemBuilder().material(Material.NETHERITE_LEGGINGS).enchant(Enchantment.VANISHING_CURSE,1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
        minion.getEquipment().setBoots(new ItemBuilder().material(Material.NETHERITE_BOOTS).enchant(Enchantment.VANISHING_CURSE,1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
        minion.getEquipment().setItemInMainHand(new ItemBuilder().material(Material.NETHERITE_SWORD).enchant(Enchantment.DAMAGE_ALL,5).build());
        minion.setHealth(1);
        minion.setAI(false);
        minion.setInvulnerable(true);

        Particle.DustOptions dust = new Particle.DustOptions(Color.BLACK, 1);
        AtomicInteger time = new AtomicInteger();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(YessirBox.getInstance(),()->{
            if (time.getAndIncrement() > 20) return;
            if (time.get() > 19) {
                minion.setAI(true);
                minion.setInvulnerable(false);
            }
            w.spawnParticle(Particle.REDSTONE, loc, 50, 0.5, 0, 0.5, 0, dust);
            w.spawnParticle(Particle.SQUID_INK, loc, 50, 0.5, 0, 0.5, 0);
            minion.teleport(minion.getLocation().add(0,0.1,0));
        },0,1);
    }

}
