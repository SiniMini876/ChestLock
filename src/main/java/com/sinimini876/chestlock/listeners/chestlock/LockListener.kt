package com.sinimini876.chestlock.listeners.chestlock

import com.sinimini876.chestlock.ChestLock
import com.sinimini876.chestlock.items.ItemManager
import com.sinimini876.chestlock.listeners.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.data.type.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerToggleSneakEvent

class LockListener(private val plugin: ChestLock) : Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @EventHandler
    fun onSneak(e: PlayerToggleSneakEvent) {
        val p = e.player
        val targetBlock = p.getTargetBlock(null, 5)
        val playerInventory = p.inventory
        val currentItem = playerInventory.itemInMainHand
        val blockType = targetBlock.type
        if (p.isSneaking && ((blockType == Material.CHEST) || (blockType == Material.BARREL)) && (ItemManager.locker.itemMeta == currentItem.itemMeta)) {
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
                val newOwners = ArrayList<String>()
                newOwners.add(p.name)
                config[coordination] = newOwners
                plugin.saveConfig()
                p.sendMessage("Your chest is now locked!")
                return
            }
            if (!owners.contains(p.name)) {
                p.sendMessage("This chest is locked by $owners")
                return
            }
            if (owners.size == 1) {
                p.sendMessage("Your chest is now unlocked!")
                config[coordination] = null
                plugin.saveConfig()
                return
            }
            owners.remove(p.name)
            config[coordination] = owners
            plugin.saveConfig()
        }
    }

    @EventHandler
    fun onChestOpen(e: PlayerInteractEvent) {
        val targetBlock = e.clickedBlock
        val player = e.player
        assert(targetBlock != null)
        val blockType = targetBlock!!.type
        if (blockType == Material.CHEST || blockType == Material.BARREL) {
            val config = plugin.config
            var coordination: String? = targetBlock.location.blockX.toString() + " " + targetBlock.location.blockY + " " + targetBlock.location.blockZ
            if (blockType == Material.CHEST) {
                val chest = targetBlock.blockData as Chest
                if (chest.type != Chest.Type.SINGLE) {
                    coordination = Utils.doubleChestCoordination(chest, targetBlock, coordination)
                }
            }
            val owners = config[coordination!!] as ArrayList<String>? ?: return
            if (!owners.contains(player.name)) {
                e.isCancelled = true
                val finalString = StringBuilder()
                for (owner in owners) {
                    finalString.append(Utils.chat("&5$owner "))
                }
                player.sendMessage("This chest is locked by " + finalString.toString().trim { it <= ' ' })
            }
        }
    }
}