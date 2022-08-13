package com.sinimini876.chestlock.listeners.utils

import org.bukkit.ChatColor
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.Chest

object Utils {
    fun chat(s: String?): String {
        return ChatColor.translateAlternateColorCodes('&', s!!)
    }

    fun doubleChestCoordination(chest: Chest, targetBlock: Block?, coordination: String?): String? {
        var newCoordination = coordination
        val type = chest.type
        var otherSideCoordinations = ""
        when (chest.facing) {
            BlockFace.NORTH -> otherSideCoordinations = if (type == Chest.Type.LEFT) {
                (targetBlock!!.location.blockX + 1).toString() + " " + targetBlock.location.blockY + " " + targetBlock.location.blockZ
            } else {
                (targetBlock!!.location.blockX - 1).toString() + " " + targetBlock.location.blockY + " " + targetBlock.location.blockZ
            }

            BlockFace.SOUTH -> otherSideCoordinations = if (type == Chest.Type.LEFT) {
                (targetBlock!!.location.blockX - 1).toString() + " " + targetBlock.location.blockY + " " + targetBlock.location.blockZ
            } else {
                (targetBlock!!.location.blockX + 1).toString() + " " + targetBlock.location.blockY + " " + targetBlock.location.blockZ
            }

            BlockFace.EAST -> otherSideCoordinations = if (type == Chest.Type.LEFT) {
                targetBlock!!.location.blockX.toString() + " " + targetBlock.location.blockY + " " + (targetBlock.location.blockZ + 1)
            } else {
                targetBlock!!.location.blockX.toString() + " " + targetBlock.location.blockY + " " + (targetBlock.location.blockZ - 1)
            }

            BlockFace.WEST -> otherSideCoordinations = if (type == Chest.Type.LEFT) {
                targetBlock!!.location.blockX.toString() + " " + targetBlock.location.blockY + " " + (targetBlock.location.blockZ - 1)
            } else {
                targetBlock!!.location.blockX.toString() + " " + targetBlock.location.blockY + " " + (targetBlock.location.blockZ + 1)
            }

            else -> {}
        }
        if (type == Chest.Type.LEFT) {
            newCoordination += "|$otherSideCoordinations"
        } else {
            newCoordination = "$otherSideCoordinations|$newCoordination"
        }
        return newCoordination
    }
}