
/**
  * Created by Mayanka on 19-06-2017.
  */
object NGRAM {

  def main(args: Array[String]): Unit = {
    val text = "the bee is the bee of the bees. It's a busy bee."
    val input = CoreNLP.returnLemma(text)
    val a = getNGrams(input,2)
    a.foreach(f=>println(f.mkString(" ")))
  }

  def getNGrams(sentence: String, n:Int): Array[Array[String]] = {
    val words = sentence
    val ngrams = words.split(' ').sliding(n)
    val result= ngrams.toArray
    //result.foreach(f=>println(f.mkString(" ")))
    result

  }

}


