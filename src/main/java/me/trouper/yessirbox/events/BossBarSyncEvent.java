package me.trouper.yessirbox.events;

import io.github.itzispyder.pdk.events.CustomListener;
import io.github.itzispyder.pdk.utils.raytracers.CustomDisplayRaytracer;
import me.trouper.yessirbox.commands.BossCommand;
import me.trouper.yessirbox.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class BossBarSyncEvent implements CustomListener {
    @EventHandler
    public void onBossDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof WitherSkeleton ent)) return;
        Component customName = ent.customName();
        if (customName.equals(BossCommand.bossName)){
            var point = CustomDisplayRaytracer.blocksInFrontOf(ent.getLocation(),ent.getLocation().getDirection(),0,true);
            point.getNearbyEntities(null,100,entity -> {
                if (entity instanceof Player player) {
                    player.showBossBar(BossCommand.b);
                    return true;
                }
                return false;
            });
            float ratio = Utils.getHealth(ent);
            Bukkit.broadcast(Component.text(ratio));
            BossCommand.b.progress(ratio);
        }
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof WitherSkeleton ent)) return;
        String customName = ent.getCustomName();
        if (customName.equals(color("&4&lWither Skeleton"))) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.hideBossBar(BossCommand.b);
            });
        }
    }
}
