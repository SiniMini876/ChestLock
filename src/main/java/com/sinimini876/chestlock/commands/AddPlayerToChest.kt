package com.sinimini876.chestlock.commands

import com.sinimini876.chestlock.ChestLock
import com.sinimini876.chestlock.listeners.utils.Utils
import org.bukkit.Material
import org.bukkit.block.data.type.Chest
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddPlayerToChest(private val plugin: ChestLock) : CommandExecutor {
    init {
        plugin.getCommand("addPlayerToChest")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can use this command!")
            return true
        }
        val p = sender
        if (p.hasPermission("addPlayerToChest.use")) {
            if (args[0] == null) return false
            val targetBlock = p.getTargetBlock(null, 5)
            val blockType = targetBlock.type
            if (blockType == Material.CHEST || blockType == Material.BARREL) {
                val config = plugin.config
                var coordination: String? = targetBlock.location.blockX.toString() + " " + targetBlock.location.blockY + " " + targetBlock.location.blockZ
                if (blockType == Material.CHEST) {
                    val chest = targetBlock.blockData as Chest
                    if (chest.type != Chest.Type.SINGLE) {
                        coordination = Utils.doubleChestCoordination(chest, targetBlock, coordination)
                    }
                }
                val owners = config[coordination!!] as ArrayList<String>?
                if (owners == null) {
                    p.sendMessage("This chest is unlocked, you can't add to it users you need to first lock it")
                    return true
                }
                if (owners[0] != p.name) {
                    sender.sendMessage("Only the owner of this chest can use this command!")
                    return true
                }
                if (owners.contains(args[0])) {
                    sender.sendMessage("The player is already on the access list!")
                    return true
                }
                owners.add(args[0])
                config[coordination] = owners
                plugin.saveConfig()
                sender.sendMessage("Added " + args[0] + " to the owners!")
                return true
            }
            sender.sendMessage("You need to look at a unlocked chest to use this command!")
            return true
        } else {
            p.sendMessage("You do not have the permission to use this command!")
        }
        return false
    }
}