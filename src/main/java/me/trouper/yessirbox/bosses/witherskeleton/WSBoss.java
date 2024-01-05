package me.trouper.yessirbox.bosses.witherskeleton;

import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import io.github.itzispyder.pdk.utils.misc.SoundPlayer;
import io.github.itzispyder.real.boss.Attack;
import io.github.itzispyder.real.boss.Boss;
import io.github.itzispyder.real.boss.Stage;
import io.github.itzispyder.real.utils.SchedulerUtils;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.bosses.attacks.FireBallAttack;
import me.trouper.yessirbox.bosses.attacks.FlameThrowerAttack;
import me.trouper.yessirbox.bosses.attacks.MinionAttack;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;

import java.util.concurrent.atomic.AtomicInteger;

import static io.github.itzispyder.pdk.utils.StringUtils.color;

public class WSBoss extends Boss {
    public WSBoss() {
        super("&4&lWither Skeleton", 100);
    }

    @Override
    public void onSpawn(Boss boss, Entity entity) {
        boss.fakeChat("You Fools! You dare summon me to this wretched realm! I will devour you, and your world with it!");
        LivingEntity living = boss.getLivingEntity();
        living.setAI(false);
        living.setSilent(true);
        Stage stageOne = boss.getCurrentStage();
        Stage stageTwo = new Stage("II/IV",100,0.3F);
        Stage stageThree = new Stage("III/IV", 100, 0.3F);
        Stage stageFour = new Stage("IV/IV", 100, 0.3F);
        // Stage 1
        stageOne.registerAttack(new FireBallAttack());
        stageOne.registerAttack(new MinionAttack<>(Skeleton.class, minion -> spawmSkeletonMinion(boss,minion)));

        // Stage 2
        stageTwo.registerAttack(new FlameThrowerAttack());

        // Stage 3
        // Stage 4

        boss.queueNextStage(stageFour);
        boss.queueNextStage(stageThree);
        boss.queueNextStage(stageTwo);

    }

    @Override
    public void onHurt(Boss boss) {
        SoundPlayer sound = new SoundPlayer(boss.getLocation(),Sound.ENTITY_IRON_GOLEM_HURT,1,0.3F);
        sound.playWithin(20);
    }

    @Override
    public void onAttack(Boss boss, Attack attack) {

    }

    @Override
    public void onDeath(Boss boss) {

    }

    @Override
    public void onElevate(Boss boss, Stage stage, Stage stage1) {
        boss.fakeChat("You Fools! You may think you defeated me, but that is only my FIRST stage! 3 more to go MUAHAHAHAHAHA!");
    }

    private void spawmSkeletonMinion(Boss boss, Skeleton minion) {
        Location OG = minion.getLocation();
        minion.teleport(OG.clone().subtract(0,2,0));
        minion.customName(Component.text(color("&0[&8Skeleton Goon&0]")));
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
        SchedulerUtils.loop(1,20,()->{
            if (time.incrementAndGet() == 20) {
                minion.setAI(true);
                minion.setInvulnerable(false);
            }
            boss.getWorld().spawnParticle(Particle.REDSTONE, OG, 50, 0.5, 0, 0.5, 0, dust);
            boss.getWorld().spawnParticle(Particle.SQUID_INK, OG, 50, 0.5, 0, 0.5, 0);
            minion.teleport(minion.getLocation().add(0,0.1,0));
        });
    }
}
