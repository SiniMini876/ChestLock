package com.sinimini876.chestlock.commands

import com.sinimini876.chestlock.ChestLock
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PingCommand(plugin: ChestLock) : CommandExecutor {
    init {
        plugin.getCommand("ping")?.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can use this command!")
            return true
        }
        val p = sender
        if (p.hasPermission("hello.use")) {
            val targetBlock = p.getTargetBlock(null, 5)
            return true
        } else {
            p.sendMessage("You do not have the permission to use this command!")
        }
        return false
    }
}