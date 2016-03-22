package me.jsutaria;

import java.util.Arrays;
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

		
		if(cmd.getName().equalsIgnoreCase("g")) {
    		Player p = (Player) sender;
			if (args[0].equalsIgnoreCase("new")) {
				if (isAnOwner(p.getName())) {
					p.sendMessage("Error, you already own a group");
				} else if (!newName(args[1])){
					p.sendMessage("Error, that name is already taken");
				} else if (!validName(args[1])) {
					p.sendMessage("Error, that name is invalid");
				} else {
					createNewGroup(args[1], p);
				}
			}
			if (args[0].equalsIgnoreCase("add")) {
				if (isAnOwner(p.getName())) {
					String groupName = isOwnerOf(p.getName());
					String player = args[1];
					addPlayer(p, groupName, player);
					p.sendMessage(player + " invited to group");
					for(Player pl : Bukkit.getOnlinePlayers()){
			            if (pl.getName().equalsIgnoreCase(player)) {
			            	pl.sendMessage("You have been invited to " + groupName);
			            }
			        }
				} else {
					p.sendMessage("Error, you do not own a group");
				}
			}
			if (args[0].equalsIgnoreCase("join")) {
				if (hasInvite(args[3], p.getName())) {
					addUserToGroup(args[3], p.getName());
					p.sendMessage("Successfully joined " + args[3]);
				} else if (isInGroup(args[3], p.getName())) {
					p.sendMessage("Error, you are already in this group");
				} else {
					p.sendMessage("Error, you are not invited to this group");
				}
			}
		}
		
		return true;
	}
	
	public void addUserToGroup(String groupName, String player) {
		List<String> users = this.getConfig().getStringList(groupName + ".users");
		List<String> invites = this.getConfig().getStringList(groupName + ".invites");
		invites.remove(player.toLowerCase());
		users.add(player.toLowerCase());
		
	}
	
	public boolean isInGroup(String groupName, String player) {
		boolean bool = false;
		List<String> users = this.getConfig().getStringList(groupName + ".users");
		if (users.contains(player)) {
			bool = true;
		}
		return bool;
	}
	
	public boolean hasInvite(String groupName, String player) {
		boolean bool = false;
		List<String> invites = this.getConfig().getStringList(groupName + ".invites");
		if (invites.contains(player)) {
			bool = true;
		}
		return bool;
	}
	
	public void addPlayer(Player p, String groupName, String player) {
		List<String> invites = this.getConfig().getStringList(groupName + ".invites");
		invites.add(player.toLowerCase());
		getConfig().set(groupName + ".invites", invites);
		saveConfig();
	}
	
	public void createNewGroup(String name, Player plyr) {
		getConfig().createSection(name);
		getConfig().createSection(name + ".users");
		List<String> userList = this.getConfig().getStringList(name + ".users");
		userList.add(plyr.getName().toLowerCase());
		getConfig().set(name + ".users", userList);
		getConfig().createSection(name + ".invites");
		getConfig().createSection(name + ".settings");
		getConfig().createSection(name + ".settings.tagcolor");
		getConfig().set(name + ".settings.tagcolor", "WHITE");
		getConfig().createSection(name + ".settings.chatcolor");
		getConfig().set(name + ".settings.chatcolor", "WHITE");
		ChatColor chatColour = ChatColor.valueOf(this.getConfig().getString(name + ".settings.chatcolor"));
    	ChatColor tagColour = ChatColor.valueOf(this.getConfig().getString(name + ".settings.tagcolor"));
		plyr.sendMessage("Group with name " + name + " successfully created");
		saveConfig();
	}
	
	public boolean validName(String name) {
		boolean bool = true;
		List<String> invalids = Arrays.asList("new", "set", "add", "delete", "kick", "join", "help");
		for (String key : invalids) {
			if (key.equalsIgnoreCase(name)) {
				bool = false;
			}
		}
		return bool;
	}
	
	public boolean newName(String name) {
		boolean bool = true;
		for (String key : this.getConfig().getKeys(false)) {
			if (key.equalsIgnoreCase(name)) {
				bool = false;
			}
		}
		return bool;
	}
	public String isOwnerOf(String plyr) {
		String group = "";
		for(String key : this.getConfig().getKeys(false)){
    		List<String> usrs = this.getConfig().getStringList(key + ".users");
    		String owner = usrs.get(0);
			if (plyr.equalsIgnoreCase(owner)) {
				return key;
			}
		}
		return group;
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
if(cmd.getName().equalsIgnoreCase("groupchat")) {
	getConfig().createSection(args[0]);
	saveConfig();
	for(String key : this.getConfig().getKeys(true)){
		sender.sendMessage(key);
	}
}
*/
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


