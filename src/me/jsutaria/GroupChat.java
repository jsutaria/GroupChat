package me.jsutaria;

import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class GroupChat extends JavaPlugin {
	@Override
	public void onEnable() {
        this.saveDefaultConfig();
	}
	@Override
	public void onDisable() {
		 
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*
		if(cmd.getName().equalsIgnoreCase("groupchat")) {
			getConfig().createSection(args[0]);
			saveConfig();
			for(String key : this.getConfig().getKeys(true)){
				sender.sendMessage(key);
			}
		}
		*/
		
		if(cmd.getName().equalsIgnoreCase("g")) {
    		Player p = (Player) sender;
			/*
    		List<?> group = this.getConfig().getList(args[0]);
    		List<String> users = this.getConfig().getStringList(args[0] + ".users");
    		ChatColor chatColour = ChatColor.BLACK;
    		ChatColor tagColour = ChatColor.BLACK;
    		chatColour = ChatColor.valueOf(this.getConfig().getString(args[0] + ".settings.chatcolor"));
        	tagColour = ChatColor.valueOf(this.getConfig().getString(args[0] + ".settings.tagcolor"));
    		p.sendMessage(args[0]);
			p.sendMessage(tagColour + "(TAG) " + chatColour + "Hi");
    		for (String s : users) {
    			p.sendMessage(s);
    			if(isAnOwner(s)) {
    				p.sendMessage(s + " is an owner");
    			}
    			if(isTheOwner(s, args[0])) {
    				p.sendMessage(s + " is the owner");
    			}
    		}
    		*/
			if (args[0].equalsIgnoreCase("new")) {
				if (isAnOwner(p.getName())) {
					p.sendMessage("Error, you already own a group");
				} else {
					
				}
			}
		}
		return true;
	}
	
	
	public boolean isAnOwner(String plyr) {
		boolean bool = false;
		for(String key : this.getConfig().getKeys(false)){
    		List<String> usrs = this.getConfig().getStringList(key + ".users");
    		String owner = usrs.get(0);
			if (plyr.equalsIgnoreCase(owner)) {
				bool = true;
			}
		}
		return bool;
	}
	public boolean isTheOwner(String plyr, String key) {
		boolean bool = false;
		List<String> usrs = this.getConfig().getStringList(key + ".users");
		String owner = usrs.get(0);
		if (plyr.equalsIgnoreCase(owner)) {
			bool = true;
		}
		return bool;
	}

}
/*
if (args[0].equalsIgnoreCase("add")) {
	if (args[1].isEmpty()) {
	}
	else {
		boolean tthis = group.isEmpty();
		p.sendMessage(ChatColor.GRAY + "(GroupChat) " + ChatColor.DARK_GRAY + "Error, no user given");
		return false;
	}
}
*/
	/* if(cmd.getName().equalsIgnoreCase("groupme")) {
	List<String> group = this.getConfig().getStringList("grouplist");
	String color = this.getConfig().getString("default.settings.chatcolor");
	if (args[0].equalsIgnoreCase("add")) {
		if (args[1].isEmpty()) {
			group.add(args[1].toLowerCase());
		}
		else {
			sender.sendMessage(ChatColor.GRAY + "(GroupChat) " + ChatColor.DARK_GRAY + "Error, no user given");
			return false;
		}
	} else if (args[0].equalsIgnoreCase("remove")) {
		if (args[1].isEmpty()) {
			group.remove(args[1].toLowerCase());
		}
		else {
			sender.sendMessage(ChatColor.GRAY + "(GroupChat) " + ChatColor.DARK_GRAY + "Error, no user given");
			return false;
		}
	} else if (args[0].equalsIgnoreCase("list")) {
		for(String key : this.getConfig().getKeys(false)){
			sender.sendMessage(key);
		}
		sender.sendMessage(color);
	}
	else {
		StringBuilder sb = new StringBuilder();
        for(int i = 0; i < args.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(args[i]);
        }
        
        for(Player p : Bukkit.getOnlinePlayers()){
            if(group.contains(p.getName().toLowerCase())){
            	p.sendMessage(ChatColor.GRAY + "(GroupChat) " + ChatColor.DARK_GRAY + sb.toString());
            }
        }
	}
	getConfig().set("grouplist", group);
	saveConfig();
}
return true;
*/



