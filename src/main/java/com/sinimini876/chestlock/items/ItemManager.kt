package com.sinimini876.chestlock.items

import com.sinimini876.chestlock.ChestLock
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

class ItemManager(plugin: ChestLock) {
    init {
        val item = ItemStack(Material.TRIPWIRE_HOOK, 1)
        val meta = item.itemMeta
        meta.displayName(Component.text("Key Locker"))
        val lore: MutableList<String> = ArrayList()
        lore.add("this locker can lock chests!")
        meta.lore = lore
        meta.addEnchant(Enchantment.LUCK, 1, false)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        item.itemMeta = meta
        locker = item
        val recipe = ShapedRecipe(NamespacedKey(plugin, "tripwirehook"), locker)
        recipe.shape(" X ", " XX", " XX")
        recipe.setIngredient('X', Material.IRON_INGOT)
        Bukkit.addRecipe(recipe)
    }

    companion object {
        lateinit var locker: ItemStack
    }
}