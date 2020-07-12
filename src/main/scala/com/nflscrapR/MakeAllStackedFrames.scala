package com.nflscrapR

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object MakeAllStackedFrames {

  Logger.getLogger("org").setLevel(Level.OFF)

  val spark: SparkSession = SparkSession
    .builder
    .master("local")
    .appName("jupyter-scala")
    .getOrCreate

  import spark.implicits._

  def stackGames(dataType:String, seasonType: String): DataFrame = {
    val regGames = spark.read
      .option("inferSchema", "true")
      .option("header", "true")
      .csv(s"./$dataType/$seasonType/*.csv")

    val teamGameScore = if (dataType == "games_data") {
      val keepCols = regGames.columns.filterNot(x => x.startsWith("away") || x.startsWith("home")).map(col)
      val structCol =
        explode(array(
          struct(
            $"home_team" as "team",
            $"home_score" as "score",
            lit(1) as "home"
          ),
          struct(
            $"away_team" as "team",
            $"away_score" as "score",
            lit(0) as "home"
          )
        )) as "team_score"
      regGames.select(keepCols :+ structCol: _*)
        .select(keepCols :+ $"team_score.*": _*)
    } else {
      regGames
    }

    teamGameScore.write.mode("overwrite").parquet(s"./$dataType/$seasonType/stacked")
    teamGameScore
  }

  val seasonTypes = Seq("post_season", "pre_season", "regular_season")
  val dataTypes = Seq("games_data", "play_by_play_data", "roster_data")

  val allFrames: Map[String, Map[String, DataFrame]] = dataTypes.map {
    dt => dt -> seasonTypes.map {
      st => st -> stackGames(dt, st)
    }
  }
    .toMap
    .mapValues(_.toMap)

  val allColumns: Map[String, Array[String]] = allFrames.mapValues(_.values.map(_.columns).reduce(_ intersect _))

  allFrames.map {
    case (str, stringToFrame) =>
      val unioned = stringToFrame.map {
        case (_, frame) =>
          val cols = allColumns(str)
          frame.select(cols.head, cols.tail: _*)
      }
        .reduce(_ union _)
      unioned.write.mode("overwrite").parquet(s"./$str/stacked")
  }

}
