package ca.xamercier.lectusHikabrain.games;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import ca.xamercier.lectusHikabrain.HikaBrain;
import ca.xamercier.lectusHikabrain.utils.ShutDownUtils;
import net.lectusAPI.utils.GameUtils;
import net.lectusAPI.utils.TeamUtils;
import net.md_5.bungee.api.ChatColor;

public class WinDetector {

	private static WinDetector instance = null;

	public static WinDetector getInstance() {
		if (instance == null) {
			instance = new WinDetector();
		}
		return instance;
	}

	public void detectWin() {
		if (HikaBrain.getInstance().BLUESCORE == 5) {
			GameState.setState(GameState.FINISH);
			Bukkit.broadcastMessage(HikaBrain.getInstance().getPrefix() + ChatColor.RESET + "La team " + ChatColor.BLUE
					+ "bleu" + ChatColor.RESET + " a gagner la partie !");
			HashMap<Player, String> map = new HashMap<Player, String>();
			for (Player pl : HikaBrain.getInstance().players) {
				pl.setGameMode(GameMode.SPECTATOR);
				clearInventory(pl);
				map.put(pl, "0");
			}
			GameUtils.sendCoins(HikaBrain.getInstance().getPrefix(), true, map);
			Thread shutdown = new ShutDownUtils();
			shutdown.start();
		} else if (HikaBrain.getInstance().REDSCORE == 5) {
			GameState.setState(GameState.FINISH);
			Bukkit.broadcastMessage(HikaBrain.getInstance().getPrefix() + ChatColor.RESET + "La team " + ChatColor.RED
					+ "rouge" + ChatColor.RESET + " a gagner la partie !");
			HashMap<Player, String> map = new HashMap<Player, String>();
			for (Player pl : HikaBrain.getInstance().players) {
				pl.setGameMode(GameMode.SPECTATOR);
				clearInventory(pl);
				map.put(pl, "0");
			}
			GameUtils.sendCoins(HikaBrain.getInstance().getPrefix(), true, map);
			Thread shutdown = new ShutDownUtils();
			shutdown.start();
		}
	}

	public void detectWinOnDeconnection() {
		int players = (HikaBrain.getInstance().players.size());
		if (players == 1) {
			for (Player p : HikaBrain.getInstance().players) {
				if (TeamUtils.getInstance().containsPlayerInTeam(p, "ROUGE")) {
					GameState.setState(GameState.FINISH);
					Bukkit.broadcastMessage(HikaBrain.getInstance().getPrefix() + ChatColor.RESET + "La team "
							+ ChatColor.RED + "rouge" + ChatColor.RESET + " a gagner la partie !");
					HashMap<Player, String> map = new HashMap<Player, String>();
					for (Player pl : HikaBrain.getInstance().players) {
						pl.setGameMode(GameMode.SPECTATOR);
						clearInventory(pl);
						map.put(pl, "0");
					}
					GameUtils.sendCoins(HikaBrain.getInstance().getPrefix(), false, map);
					Thread shutdown = new ShutDownUtils();
					shutdown.start();
				} else if (TeamUtils.getInstance().containsPlayerInTeam(p, "BLEU")) {
					GameState.setState(GameState.FINISH);
					Bukkit.broadcastMessage(HikaBrain.getInstance().getPrefix() + ChatColor.RESET + "La team "
							+ ChatColor.BLUE + "bleu" + ChatColor.RESET + " a gagner la partie !");
					HashMap<Player, String> map = new HashMap<Player, String>();
					for (Player pl : HikaBrain.getInstance().players) {
						pl.setGameMode(GameMode.SPECTATOR);
						clearInventory(pl);
						map.put(pl, "0");
					}
					GameUtils.sendCoins(HikaBrain.getInstance().getPrefix(), false, map);
					Thread shutdown = new ShutDownUtils();
					shutdown.start();
				}
			}
		} else if (players == 0 || players == -1) {
			Thread shutdown = new ShutDownUtils();
			shutdown.start();
		}
	}

	private static void clearInventory(Player pl) {
		pl.getInventory().clear();
		pl.getInventory().setHelmet(null);
		pl.getInventory().setChestplate(null);
		pl.getInventory().setLeggings(null);
		pl.getInventory().setBoots(null);
	}

}
