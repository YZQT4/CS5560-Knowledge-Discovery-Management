Skip to content

Search or jump to…

Pull requests
  Issues
Marketplace
Explore
@YZQT4
Sign out
  Watch 0
Star 0  Fork 0 zzktb/CS5560-ZhaobinZhang-ICPSubmission
Code  Issues 0  Pull requests 0  Projects 0  Wiki  Insights
Branch: master Find file Copy path CS5560-ZhaobinZhang-ICPSubmission/ICP-5/source/TF_IDF.scala
2b5500f  an hour ago
@zzktb zzktb Add files via upload
  1 contributor
  RawBlameHistory
89 lines (65 sloc)  2.5 KB
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF}

import scala.collection.immutable.HashMap

/**
  * Created by Mayanka on 19-06-2017.
  */
object TF_IDF {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "D:\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    //Reading the Text File
    //    val documents = sc.textFile("data/Article.txt")
    //val documents = sc.textFile("D:\\umkc\\2018Fall\\Knowledge_discovery\\Tutorial-4-Intro-to-Sparking-Programming\\Spark WordCount\\inputFolder\\abs_1.txt")
    val input_folder = sc.wholeTextFiles("abstracts")
    val documents = input_folder.map(abs=>{
      abs._2
    })

    //Getting the Lemmatised form of the words in TextFile
    val documentseq = documents.map(f => {
      val lemmatised = CoreNLP.returnLemma(f)
      val splitString = f.split(" ")
      splitString.toSeq
    })
    //    val documentseq = documents.map(f=>{
    //  val splitString = f.split("")
    //  splitString.toSeq
    //})

    //Creating an object of HashingTF Class
    val hashingTF = new HashingTF()

    //Creating Term Frequency of the document
    val tf = hashingTF.transform(documentseq)
    tf.cache()


    val idf = new IDF().fit(tf)

    //Creating Inverse Document Frequency
    val tfidf = idf.transform(tf)

    val tfidfvalues = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val values = ff(2).replace("]", "").replace(")", "").split(",")
      values
    })

    val tfidfindex = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val indices = ff(1).replace("]", "").replace(")", "").split(",")
      indices
    })

    tfidf.foreach(f => println(f))

    val tfidfData = tfidfindex.zip(tfidfvalues)

    var hm = new HashMap[String, Double]

    tfidfData.collect().foreach(f => {
      hm += f._1 -> f._2.toDouble
    })

    val mapp = sc.broadcast(hm)

    val documentData = documentseq.flatMap(_.toList)
    val dd = documentData.map(f => {
      val i = hashingTF.indexOf(f)
      val h = mapp.value
      (f, h(i.toString))
    })

    val dd1 = dd.distinct().sortBy(_._2, false)
    dd1.take(20).foreach(f => {
      println(f)
    })

  }

}
© 2018 GitHub, Inc.
  Terms
Privacy
Security
Status
Help
Contact GitHub
  Pricing
API
Training
Blog
About
Press h to open a hovercard with more details.