package me.trouper.yessirbox.events;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.events.CustomListener;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.commands.BountyCommand;
import me.trouper.yessirbox.data.Bounty;
import me.trouper.yessirbox.utils.TimeStamp;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BountyEvent implements CustomListener, Global {
    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player v = e.getPlayer();
        Player k = v.getKiller();
        YessirBox.log.info("Processing Death Event, Victim: " + v + " Killer: " + k);
        Bounty b = new Bounty(v.getUniqueId(),v.getUniqueId(),0, TimeStamp.now(),0);
        for (Bounty bounty : YessirBox.bounties.storage) {
            if (!(bounty.target() == v.getUniqueId())) continue;
            b = bounty;
            YessirBox.log.info("Found a valid bounty for the victim \n" + b.getInfo());
            break;
        }
        if (b.amount() == 0) {
            YessirBox.log.info("No valid bounties found for victim");
            return;
        }
        if (k == null) return;
        YessirBox.bounties.storage.remove(b);
        EconomyResponse res = YessirBox.econ.depositPlayer(k, b.amount());
        if (res.transactionSuccess()) {
            YessirBox.getInstance().getServer().broadcast(Component.text(color(
                    YessirBox.config.prefix + "&3" + Bukkit.getOfflinePlayer(b.setter()).getName() +
                            "&7's bounty on &e" + v.getName() +
                            "&7 has been claimed by &a" + k.getName() + ".")));
            info(k,YessirBox.config.prefix + "&7You have claimed " + Bukkit.getOfflinePlayer(b.setter()).getName() +
                    "&7's bounty on &e" + v.getName() +
                    "&7 and received &2$" + b.amount() + "&7.");
        } else {
            YessirBox.log.warning("Error! Could not deposit money for bounty!" + res.errorMessage);
        }
    }
}
