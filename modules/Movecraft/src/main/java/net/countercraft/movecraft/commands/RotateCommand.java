package net.countercraft.movecraft.commands;

import net.countercraft.movecraft.Rotation;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.CraftManager;
import net.countercraft.movecraft.localisation.I18nSupport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RotateCommand implements TabExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!command.getName().equalsIgnoreCase("rotate")){
            return false;
        }
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("you need to be a player to pilot a craft");
            return true;
        }
        Player player = (Player) commandSender;
        if(args.length<1){
            commandSender.sendMessage("you need to supply a direction");
            return true;
        }
        if (args[0].equalsIgnoreCase("left")) {
            if (!player.hasPermission("movecraft.commands") && !player.hasPermission("movecraft.commands.rotateleft")) {
                player.sendMessage(I18nSupport.getInternationalisedString("Insufficient Permissions"));
                return true;
            }
            final Craft craft = CraftManager.getInstance().getCraftByPlayerName(player.getName());
            if(craft==null){
                player.sendMessage(I18nSupport.getInternationalisedString("You need to be piloting a craft"));
                return true;
            }
            if (!player.hasPermission("movecraft." + craft.getType().getCraftName() + ".rotate")) {
                player.sendMessage(I18nSupport.getInternationalisedString("Insufficient Permissions"));
                return true;
            }
            CraftManager.getInstance().getCraftByPlayerName(player.getName()).rotate(Rotation.ANTICLOCKWISE, craft.getHitBox().getMidPoint());
            return true;
        }

        if (args[0].equalsIgnoreCase("right")) {
            if (!player.hasPermission("movecraft.commands") && !player.hasPermission("movecraft.commands.rotateright")) {
                player.sendMessage(I18nSupport.getInternationalisedString("Insufficient Permissions"));
                return true;
            }
            final Craft craft = CraftManager.getInstance().getCraftByPlayerName(player.getName());
            if(craft==null){
                player.sendMessage(I18nSupport.getInternationalisedString("You need to be piloting a craft"));
                return true;
            }
            if (!player.hasPermission("movecraft." + craft.getType().getCraftName() + ".rotate")) {
                player.sendMessage(I18nSupport.getInternationalisedString("Insufficient Permissions"));
                return true;
            }
            CraftManager.getInstance().getCraftByPlayerName(player.getName()).rotate(Rotation.CLOCKWISE, craft.getHitBox().getMidPoint());
            return true;
        }
        player.sendMessage("invalid direction");
        return true;
    }

    private final String[] completions = {"Right", "Left"};
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length !=1)
            return Collections.emptyList();
        List<String> returnValues = new ArrayList<>();
        for(String completion : completions)
            if(completion.toLowerCase().startsWith(strings[strings.length-1].toLowerCase()))
                returnValues.add(completion);
        return returnValues;
    }
}
