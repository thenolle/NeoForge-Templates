package com.nolly.mc.template_neoforge.database

import java.util.UUID

data class PlayerStats(
	val uuid: UUID,
	val name: String,
	val kills: Int,
	val deaths: Int,
)

class PlayerStatsRepository(private val database: Database) {
	fun createTable() = database.exec(
		"""
        CREATE TABLE IF NOT EXISTS player_stats (
            uuid   TEXT PRIMARY KEY NOT NULL,
            name   TEXT NOT NULL,
            kills  INTEGER NOT NULL DEFAULT 0,
            deaths INTEGER NOT NULL DEFAULT 0
        )
        """.trimIndent()
	)

	fun save(stats: PlayerStats) = database.exec(
		"""
	    INSERT INTO player_stats(uuid, name, kills, deaths)
	    VALUES (?, ?, ?, ?)
	    ON CONFLICT(uuid) DO UPDATE SET
	        name   = excluded.name,
	        kills  = excluded.kills,
	        deaths = excluded.deaths
	    """.trimIndent(), stats.uuid.toString(), stats.name, stats.kills, stats.deaths
	)

	fun find(uuid: UUID): PlayerStats? = database.queryFirst("SELECT * FROM player_stats WHERE uuid = ?", uuid.toString(), mapper = ::mapRow)

	fun topKillers(limit: Int = 10): List<PlayerStats> = database.query("SELECT * FROM player_stats ORDER BY kills DESC LIMIT ?", limit, mapper = ::mapRow)

	fun incrementKill(uuid: UUID) = database.exec("UPDATE player_stats SET kills = kills + 1 WHERE uuid = ?", uuid.toString())

	private fun mapRow(row: Database.Row) = PlayerStats(
		uuid = UUID.fromString(row.string("uuid")!!),
		name = row.string("name")!!,
		kills = row.int("kills"),
		deaths = row.int("deaths"),
	)
}
