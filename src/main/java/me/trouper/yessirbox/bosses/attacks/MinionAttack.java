package me.trouper.yessirbox.bosses.attacks;

import io.github.itzispyder.pdk.utils.misc.Randomizer;
import io.github.itzispyder.real.boss.Attack;
import io.github.itzispyder.real.boss.Boss;
import io.github.itzispyder.real.boss.Stage;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class MinionAttack<T extends LivingEntity> implements Attack {
    private final Class<T> entityType;
    private final Consumer<T> action;
    public MinionAttack (Class<T> entityType, Consumer<T> action) {
        this.entityType = entityType;
        this.action = action;
    }
    @Override
    public void perform(Boss boss, World world, Location location, List<Player> list) {
        Stage stage = boss.getCurrentStage();
        Randomizer rand = stage.getRandom();
        double x = rand.getRandomDouble(-5,5);
        double z = rand.getRandomDouble(-5,5);
        T entity = boss.spawnMinion(location.clone().add(x,0,z), entityType);
        action.accept(entity);
    }
}
