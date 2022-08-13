package com.sinimini876.chestlock.commands

import com.sinimini876.chestlock.ChestLock
import com.sinimini876.chestlock.items.ItemManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetLocker(plugin: ChestLock) : CommandExecutor {
    init {
        plugin.getCommand("getlocker")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can use this command!")
            return true
        }
        val p = sender
        if (p.isOp) {
            p.inventory.addItem(ItemManager.Companion.locker)
            return true
        } else {
            p.sendMessage("You do not have the permission to use this command!")
        }
        return false
    }
}