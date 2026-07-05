package com.nolly.mc.template_neoforge

import com.nolly.mc.template_neoforge.database.Database
import com.nolly.mc.template_neoforge.database.PlayerStatsRepository
import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.neoforged.fml.loading.FMLPaths
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.runWhenOn

@Mod(TemplateNeoForge.ID)
object TemplateNeoForge {
	const val ID = "template_neoforge"
	val LOGGER: Logger = LogManager.getLogger(ID)

	lateinit var database: Database private set
	lateinit var playerStats: PlayerStatsRepository private set

	init {
		LOGGER.info("Initializing $ID")
		MOD_BUS.addListener(::onCommonSetup)
		runWhenOn(Dist.CLIENT) {
			MOD_BUS.addListener(::onClientSetup)
		}
		runWhenOn(Dist.DEDICATED_SERVER) {
			MOD_BUS.addListener(::onServerSetup)
		}
	}

	private fun onCommonSetup(event: FMLCommonSetupEvent) {
		val databasePath = FMLPaths.CONFIGDIR.get().resolve("$ID/data.db")
		database = Database(databasePath)
		playerStats = PlayerStatsRepository(database).also { it.createTable() }
		LOGGER.info("SQLite database ready at $databasePath")
	}

	private fun onClientSetup(event: FMLClientSetupEvent) {
		LOGGER.info("Client setup complete")
	}

	private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
		LOGGER.info("Dedicated server setup complete")
	}
}
