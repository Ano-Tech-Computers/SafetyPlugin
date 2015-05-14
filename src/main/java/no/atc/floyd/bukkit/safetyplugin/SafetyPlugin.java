package no.atc.floyd.bukkit.safetyplugin;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;


/**
* Indispensable plugin for Bukkit
*
* @author FloydATC
*/
public class SafetyPlugin extends JavaPlugin implements Listener {
    //public static Permissions Permissions = null;
    
    

    public void onDisable() {
    	
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
    	PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );
    }

    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerBucketEmptyEvent( PlayerBucketEmptyEvent event ) {
    	Player p = event.getPlayer();
    	if (p.getLocation().getBlockY() < 16) {
        	getLogger().fine("Player "+p.getName()+" is at y<16, lava/water is permitted");
	    	Material m = event.getBucket();
	    	if (m == Material.WATER_BUCKET) {
	        	getLogger().info("Allowing water bucket for "+event.getPlayer().getName());
	    		event.setCancelled(false);
	    	}
	    	if (m == Material.LAVA_BUCKET) {
	        	getLogger().info("Allowing lava bucket for "+event.getPlayer().getName());
	    		event.setCancelled(false);
	    	}
    	} else {
        	getLogger().fine("Player "+p.getName()+" is at y>=16, lava/water is forbidden");
	    	Material m = event.getBucket();
	    	if (m == Material.WATER_BUCKET && p.hasPermission("safety.allow.waterbucket") == false) {
	        	getLogger().info("Blocking water bucket for "+event.getPlayer().getName());
	    		event.setCancelled(true);
	    	}
	    	if (m == Material.LAVA_BUCKET && p.hasPermission("safety.allow.lavabucket") == false) {
	        	getLogger().info("Blocking lava bucket for "+event.getPlayer().getName());
	    		event.setCancelled(true);
	    	}
    	}
    }    
   
    
}

