package com.hortonworks.examples

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.util._


object StreamingExample {

  def main(arg: Array[String]) {

    if (arg.length < 2) {
      System.err.println("Usage: StreamingExample WindowSize SourceFolder DestinationFolder")
      System.exit(1)
    }

    val jobName = "SparkStreamingExampleTest"
    val conf = new SparkConf().setAppName(jobName)
    val sc = new SparkContext(conf)

    val windowSize = arg(0)
    val srcDir = arg(1)
    val destDir = arg(2)
    val ssc = new StreamingContext(sc, Seconds(windowSize.toInt))

    val lines = ssc.textFileStream(srcDir)
    val massagedLinesTuples = lines.map(x => (Math.random(), x))
    massagedLinesTuples.foreachRDD(rdd => {
      if (!rdd.isEmpty) {
        val dirName = "d" + Math.random()
        println ("Saving sequence file " + dirName)
        rdd.saveAsSequenceFile(destDir + "/" + dirName)
        println("Done saving sequence file " + dirName)
      }
    }
    )


    ssc.start()
    ssc.awaitTermination()


  }
}
