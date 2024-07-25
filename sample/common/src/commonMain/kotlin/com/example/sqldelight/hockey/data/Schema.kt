package com.example.sqldelight.hockey.data

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.example.sqldelight.hockey.HockeyDb
import com.example.sqldelight.hockey.data.PlayerVals.Position
import com.example.sqldelight.hockey.data.PlayerVals.Shoots
import kotlinx.datetime.LocalDate

fun createQueryWrapper(driver: SqlDriver): HockeyDb {
  return HockeyDb(
    driver = driver,
    teamAdapter = Team.Adapter(
      foundedAdapter = DateAdapter(),
    ),
    playerAdapter = Player.Adapter(
      shootsAdapter = EnumColumnAdapter(),
      positionAdapter = EnumColumnAdapter(),
      birth_dateAdapter = DateAdapter(),
      numberAdapter = IntColumnAdapter,
      ageAdapter = IntColumnAdapter,
      weightAdapter = FloatColumnAdapter,
    ),
  )
}

object Schema : SqlSchema<QueryResult.Value<Unit>> by HockeyDb.Schema {
  override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
    HockeyDb.Schema.create(driver)

    // Seed data time!
    createQueryWrapper(driver).apply {
      val ducks = "Anaheim Ducks"
      val pens = "Pittsburgh Penguins"
      val sharks = "San Jose Sharks"

      // Populate teams.
      teamQueries.insertTeam(ducks, LocalDate(1993, 3, 1), "Randy Carlyle", true)
      teamQueries.insertTeam(pens, LocalDate(1966, 2, 8), "Mike Sullivan", true)
      teamQueries.insertTeam(sharks, LocalDate(1990, 5, 5), "Peter DeBoer", false)

      playerQueries.insertPlayer(
        "Corey", "Perry", 10, ducks, 30, 210F, LocalDate(1985, 5, 16),
        Shoots.RIGHT, Position.RIGHT_WING,
      )
      playerQueries.insertPlayer(
        "Ryan", "Getzlaf", 15, ducks, 30, 221F, LocalDate(1985, 5, 10),
        Shoots.RIGHT, Position.CENTER,
      )
      teamQueries.setCaptain(15, ducks)

      playerQueries.insertPlayer(
        "Sidney", "Crosby", 87, pens, 28, 200F, LocalDate(1987, 8, 7),
        Shoots.LEFT, Position.CENTER,
      )
      teamQueries.setCaptain(87, pens)

      playerQueries.insertPlayer(
        "Erik", "Karlsson", 65, sharks, 28, 190F, LocalDate(1990, 5, 31),
        Shoots.RIGHT, Position.DEFENSE,
      )

      playerQueries.insertPlayer(
        "Joe", "Pavelski", 8, sharks, 31, 194F, LocalDate(1984, 7, 18),
        Shoots.RIGHT, Position.CENTER,
      )
      teamQueries.setCaptain(8, sharks)
    }
    return QueryResult.Unit
  }
}
