import org.apache.spark.storage.StorageLevel

object TestAppMain {

  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println("Need input path and output path")
      System.exit(1)
    }

    val spark = SparkSession
      .builder()
      .appName("Vopolski Application")
      .getOrCreate()

    val moviesDF = spark.read
      .option("inferSchema", "true")
      .json(args(0))

    moviesDF.persist(StorageLevel.MEMORY_AND_DISK_2).count()



    val goodComediesDF = moviesDF
      .repartition(4)
      .select(
      col("Title"),
      col("IMDB_Rating").as("Rating"),
      col("Release_Date").as("Release")
    )
      .where(col("Major_Genre") === "Comedy" and col("IMDB_Rating") > 6.5)

      // shuffle 200

      .orderBy(col("Rating").desc_nulls_last)

    goodComediesDF.show

    goodComediesDF
      .coalesce(2)
      .write
      .mode(SaveMode.Overwrite)
      .format("json")
      .save(args(1))
  }

}