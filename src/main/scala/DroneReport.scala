import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import faker.{Address, Lorem, Name}

case class Position (lat : String, long : String)

object DroneReport {

  case class DroneReport(_id: Int, _date: Date, _pos : Position, _citizens: Map[String, Int], _words: List[String]) {
    val id: Int = _id
    val date: Date = _date
    val pos: Position = _pos
    val citizens: Map[String, Int] = _citizens
    val words: List[String] = _words
  }

  val dateformat = new SimpleDateFormat("y-M-d")

  def PrintReport(report: DroneReport): Unit = {
    val str = "id = " + report._id.toString + "; date = " + dateformat.format(report._date) + "; lat = " + report._pos.lat + "; long = " + report._pos.long + "; citizens = " + report._citizens.toString + "; words = " + report._words.toString + ";"
    println(str)
  }

  def GenerateReport(max_citizens : Int, max_words : Int): Unit = {
    val random = scala.util.Random
    DroneReport(
      random.nextInt(1000),
      Calendar.getInstance().getTime,
      Position(Address.latitude, Address.longitude),
      List.fill(random.nextInt(max_citizens))((Name.name, random.nextInt(100))).toMap,
      Lorem.words(random.nextInt(max_words))
    )
  }
}
