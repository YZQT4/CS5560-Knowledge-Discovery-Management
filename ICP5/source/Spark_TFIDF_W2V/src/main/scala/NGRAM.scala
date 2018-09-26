/**
  * Created by Mayanka on 19-06-2017.
  */
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF}

import scala.collection.immutable.HashMap
object NGRAM {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "D:\\winutils")
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val input_folder = sc.wholeTextFiles("abstracts")
    val documents = input_folder.map(abs=>{
      abs._2
    })
    documents.foreach(f=>{
      val a = getNGrams(f,2)
      a.foreach(f=>println(f.mkString(" ")))
    })

  }

  def getNGrams(sentence: String, n:Int): Array[Array[String]] = {
    val words = sentence
    val ngrams = words.split(' ').sliding(n)
    ngrams.toArray
  }

}