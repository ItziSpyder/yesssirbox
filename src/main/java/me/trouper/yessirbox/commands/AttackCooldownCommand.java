package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.events.EntityDamageListener;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value="attackcooldown", permission=@Permission(value="ysb.attackcooldown", message="No"),printStackTrace = true)
public class AttackCooldownCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;
        boolean isRecipient = EntityDamageListener.attackCooldownBypassers.isRecipient(p);
        if (isRecipient) EntityDamageListener.attackCooldownBypassers.removeRecipient(p);
        else EntityDamageListener.attackCooldownBypassers.addRecipient(p);
        isRecipient = EntityDamageListener.attackCooldownBypassers.isRecipient(p);
        p.sendMessage(Component.text(color("&7[&bAttackCooldown&7] &8>> &3You are " + (isRecipient ? "&anow" : "&cno longer") + " &3a an attack cooldown bypasser!")));
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
    }
}
