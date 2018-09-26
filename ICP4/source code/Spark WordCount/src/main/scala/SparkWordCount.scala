

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Mayanka on 09-Sep-15.
 */
object SparkWordCount {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","D:\\winutils");

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    //val input=sc.textFile("input")

    val inputf = sc.wholeTextFiles(path = "abstract", minPartitions=10)

    val input = inputf.map(abs=>{
      abs._2
    })

//    val wcf = input.map(abs=>{abs._2.split(" ")}).map(word=>(word,1)).cache()
//   // inputf.map(abs=>{
//   //    abs._1//file path
//   //    abs._2//file content
//   //   })

    val wc=input.flatMap(line=>{line.split(" ")}).map(word=>(word,1)).cache()

    val output=wc.reduceByKey(_+_)

    output.saveAsTextFile("output")

    val o=output.collect()

    var s:String="Words:Count \n"
    o.foreach{case(word,count)=>{

      s+=word+" : "+count+"\n"

    }}

  }

}
