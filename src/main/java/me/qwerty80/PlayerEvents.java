package me.qwerty80;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PlayerEvents implements Listener {

    Qwerty80 main;

    public PlayerEvents(Qwerty80 _main) {
        main = _main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            // https://wiki.vg/Protocol_version_numbers
            int version = Utils.viaApi.getPlayerVersion(event.getPlayer());
            
            if (Utils.range(version, 735, 755)) {
                event.getPlayer().sendMessage(Component.text("Hey there! Thank you for joining our server. Even though we allow older clients to join, please don't forget that our server was designed with 1.17.1 in mind. If you come across any glitches or bugs, please test them on 1.17.1 before reporting to us. Thank you and we hope you have a great time!").color(NamedTextColor.RED));
                return;
            }

            if (version < 735) {
                event.getPlayer().sendMessage(Component.text("Hey there! Thank you for joining our server. Even though we allow legacy clients to join, please don't forget that since you're on a version below 1.16 some items or blocks might look different than expected. We do not support versions below 1.16, expect a lot of bugs.").color(NamedTextColor.RED));
                return;
            }
        }, 5);
    }
}
