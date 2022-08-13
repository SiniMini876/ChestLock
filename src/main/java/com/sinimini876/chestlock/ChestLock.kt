package com.sinimini876.chestlock

import com.sinimini876.chestlock.commands.*
import com.sinimini876.chestlock.items.ItemManager
import com.sinimini876.chestlock.listeners.chestlock.LockListener
import org.bukkit.plugin.java.JavaPlugin

class ChestLock : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()
        LockListener(this)
        AddPlayerToChest(this)
        RemovePlayerFromChest(this)
        OwnerList(this)
        LockChest(this)
        UnlockChest(this)
        GetLocker(this)
        ItemManager(this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}