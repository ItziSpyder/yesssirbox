package me.trouper.yessirbox.events;

import io.github.itzispyder.pdk.events.CustomListener;
import me.trouper.yessirbox.system.RecipientList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDamageListener implements CustomListener {

    public static final RecipientList attackCooldownBypassers = new RecipientList();
    public static final int DEFAULT_NO_DAMAGE_TICKS = 9;
    public static final int DEFAULT_MAX_NO_DAMAGE_TICKS = 10;
    public static final int NO_DAMAGE_TICKS = 1;
    public static final int MAX_NO_DAMAGE_TICKS = 2;

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        try {
            this.handleBurstMelee(e);
        }
        catch (Exception ignore) {}
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        try {
            this.handleBurstMelee(e);
            this.handleThornsBypass(e);
        }
        catch (Exception ignore) {}
    }

    public void handleBurstMelee(EntityDamageEvent e) {
        final Entity ent = e.getEntity();
        final EntityDamageEvent.DamageCause cause = e.getCause();

        if (cause.name().contains("ENTITY")) return;
        if (ent instanceof LivingEntity eLiving) {
            eLiving.setNoDamageTicks(DEFAULT_NO_DAMAGE_TICKS);
            eLiving.setMaximumNoDamageTicks(DEFAULT_MAX_NO_DAMAGE_TICKS);
        }
    }

    public void handleThornsBypass(EntityDamageEvent e) {
        final Entity ent = e.getEntity();
        final EntityDamageEvent.DamageCause cause = e.getCause();

        if (cause != EntityDamageEvent.DamageCause.THORNS) return;
        if (ent instanceof Player p ) {
            final ItemStack item = p.getInventory().getItemInMainHand();

            if (attackCooldownBypassers.isRecipient(p)) {
                e.setCancelled(true);
            }
        }
    }

    public void handleBurstMelee(EntityDamageByEntityEvent e) {
        final Entity victim = e.getEntity();
        final Entity damager = e.getDamager();
        final double dist = victim.getLocation().distance(damager.getLocation());

        if (damager instanceof Player pDamager && victim instanceof LivingEntity vLiving) {
            if (dist > 3.5) return;
            if (pDamager.isDead() || victim.isDead()) return;

            final ItemStack item = pDamager.getInventory().getItemInMainHand();

            if (attackCooldownBypassers.isRecipient(pDamager)) {
                vLiving.setNoDamageTicks(NO_DAMAGE_TICKS);
                vLiving.setMaximumNoDamageTicks(MAX_NO_DAMAGE_TICKS);
            }
            else {
                vLiving.setNoDamageTicks(DEFAULT_NO_DAMAGE_TICKS);
                vLiving.setMaximumNoDamageTicks(DEFAULT_MAX_NO_DAMAGE_TICKS);
            }
        }
    }
}
