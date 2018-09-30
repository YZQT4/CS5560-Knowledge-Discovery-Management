import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

/**
  * Created by Mayanka on 19-06-2017.
  */
object W2V {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "D:\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
      .set("spark.driver.memory", "6g").set("spark.executor.memory", "6g")

    val sc = new SparkContext(sparkConf)

    val input = sc.textFile("data/abstracts").map(line => {
      //line.split(" ").toSeq
      val lemmatised = CoreNLP.returnLemma(line)
      val splitString = lemmatised.split(" ")
      splitString.toSeq
      //val ngram = NGRAM.getNGrams(line,2).map(f=>f.mkString(" "))
      //ngram.toSeq
    })

    val modelFolder = new File("myModelPath")

    if (modelFolder.exists()) {
      val sameModel = Word2VecModel.load(sc, "myModelPath")

      val top_words = sc.textFile("data/TFwords")
      val w2v = top_words.map(w=>{
        val word = w.toString
        val synonyms = sameModel.findSynonyms(word,40)
        (w,synonyms)
      })
      w2v.collect().foreach(result=>{
        println("\nsynonyms of "+result._1+":")
        for ((synonym, cosineSimilarity) <- result._2) {
          println(s"$synonym $cosineSimilarity")
        }
      })
      //val synonyms = sameModel.findSynonyms("research", 40)

//      for ((synonym, cosineSimilarity) <- synonyms) {
//        println(s"$synonym $cosineSimilarity")
//      }
    }
    else {
      val word2vec = new Word2Vec().setVectorSize(1000)

      val model = word2vec.fit(input)

      val synonyms = model.findSynonyms("therapies", 40)

      for ((synonym, cosineSimilarity) <- synonyms) {
        println(s"$synonym $cosineSimilarity")
      }

      model.getVectors.foreach(f => println(f._1 + ":" + f._2.length))

      // Save and load model
      model.save(sc, "myModelPath")

    }

  }
}
