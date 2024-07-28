package br.com.rillis.villagerglobalprice;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class VillagerClickEvent implements Listener {
    @EventHandler
    public void onRightClickVillager(PlayerInteractAtEntityEvent event) {
        if(event.getRightClicked().getType().equals(EntityType.VILLAGER)){
            GlobalPrice.editNBT(event.getRightClicked(), event.getPlayer());
        }
    }
}
