package com.nolly.mc.template_neoforge.database

import org.sqlite.SQLiteDataSource
import java.lang.AutoCloseable
import java.nio.file.Path
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectories

class Database(databasePath: Path) : AutoCloseable {
	private val connection: Connection

	init {
		databasePath.parent?.createDirectories()
		val dataSource = SQLiteDataSource().apply { url = "jdbc:sqlite:${databasePath.absolutePathString()}" }
		connection = dataSource.connection
		connection.autoCommit = true
		pragma("PRAGMA journal_mode=WAL")
		pragma("PRAGMA foreign_keys=ON")
	}

	private fun pragma(sql: String) {
		connection.createStatement().use { it.execute(sql) }
	}

	fun exec(sql: String, vararg parameters: Any?): Int = connection.prepareStatement(sql).use { statement ->
		bind(statement, parameters)
		statement.executeUpdate()
	}

	fun <T> query(sql: String, vararg parameters: Any?, mapper: (Row) -> T): List<T> = connection.prepareStatement(sql).use { statement ->
		bind(statement, parameters)
		statement.executeQuery().use { resultSet -> buildList { while (resultSet.next()) add(mapper(Row(resultSet))) } }
	}

	fun <T> queryFirst(sql: String, vararg parameters: Any?, mapper: (Row) -> T): T? = query(sql, *parameters, mapper = mapper).firstOrNull()

	fun <T> transaction(block: () -> T): T {
		connection.autoCommit = false
		return try {
			val result = block()
			connection.commit()
			result
		} catch (throwable: Throwable) {
			connection.rollback()
			throw throwable
		} finally {
			connection.autoCommit = true
		}
	}

	private fun bind(statement: PreparedStatement, parameters: Array<out Any?>) {
		parameters.forEachIndexed { index, parameter -> statement.setObject(index + 1, parameter) }
	}

	override fun close() = connection.close()

	class Row(private val rs: ResultSet) {
		fun int(column: String): Int = rs.getInt(column)
		fun long(column: String): Long = rs.getLong(column)
		fun string(column: String): String? = rs.getString(column)
		fun bool(column: String): Boolean = rs.getBoolean(column)
		fun double(column: String): Double = rs.getDouble(column)
	}
}
