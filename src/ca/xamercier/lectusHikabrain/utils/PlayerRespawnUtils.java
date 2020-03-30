package ca.xamercier.lectusHikabrain.utils;

import org.bukkit.entity.Player;

public class PlayerRespawnUtils extends Thread {

	@SuppressWarnings("deprecation")
	public void run(Player p) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p.spigot().respawn();
		this.destroy();
	}
}