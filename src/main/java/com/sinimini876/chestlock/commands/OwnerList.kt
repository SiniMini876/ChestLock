package com.sinimini876.chestlock.commands

import com.sinimini876.chestlock.ChestLock
import com.sinimini876.chestlock.listeners.utils.Utils
import org.bukkit.Material
import org.bukkit.block.data.type.Chest
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class OwnerList(private val plugin: ChestLock) : CommandExecutor {
    init {
        plugin.getCommand("ownerList")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can use this command!")
            return true
        }
        val p = sender
        if (p.hasPermission("ownerList.use")) {
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
                val Owners = config[coordination!!] as ArrayList<String>?
                var finalString: String? = ""
                if (Owners == null) {
                    sender.sendMessage("This chest is unlocked")
                    return true
                }
                for (owner in Owners) {
                    finalString += Utils.chat("&5$owner ")
                }
                sender.sendMessage("This chest is locked by $finalString")
                return true
            }
            sender.sendMessage("You need to look at a chest to use this command!")
            return true
        } else {
            p.sendMessage("You do not have the permission to use this command!")
        }
        return false
    }
}