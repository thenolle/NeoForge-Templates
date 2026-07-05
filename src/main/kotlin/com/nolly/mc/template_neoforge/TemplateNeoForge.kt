package com.nolly.mc.template_neoforge

import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(TemplateNeoForge.ID)
object TemplateNeoForge {
	const val ID = "template_neoforge"
	val LOGGER: Logger = LogManager.getLogger(ID)

	init {
		LOGGER.info("Initializing $ID")
		MOD_BUS.addListener(::onCommonSetup)
		MOD_BUS.addListener(::onServerSetup)
	}

	private fun onCommonSetup(event: FMLCommonSetupEvent) {
		LOGGER.info("Common setup complete")
	}

	private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
		LOGGER.info("Dedicated server setup complete")
	}
}
