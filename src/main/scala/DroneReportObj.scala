import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import fabricator._

object DroneReportObj {

  case class Position(lat: String, long: String)

  implicit val encodePosition: Encoder[Position] =
    Encoder.forProduct2("lat", "long")(pos => (pos.lat, pos.long))

  implicit val decodePosition : Decoder[Position] =
    Decoder.forProduct2("lat", "long")(Position.apply)

  val dateFormat = new SimpleDateFormat("y-M-d")

  case class DroneReport(id: Int, date: Date, pos: Position, citizens: Map[String, Int], words: List[String])

  implicit val encodeDroneReport: Encoder[DroneReport] =
    Encoder.forProduct5("id", "date", "pos", "citizens", "words")(drone => (drone.id, dateFormat.format(drone.date), drone.pos, drone.citizens, drone.words))

  implicit val decodeDroneReport: Decoder[DroneReport] =
    Decoder.forProduct5("id", "date", "pos", "citizens", "words")((id : Int, date : String, pos : Position, citizens : Map[String, Int], words : List[String]) => DroneReport(id, dateFormat.parse(date), pos, citizens, words))

  def GenerateReport(max_citizens: Int, max_words: Int): DroneReport = {
    val random = scala.util.Random
    val location = Location()
    val contact = Contact()
    val words = Words()

    val n_citizens = random.nextInt(max_citizens)

    DroneReport(
      random.nextInt(1000),
      Calendar.getInstance().getTime,
      Position(location.latitude, location.longitude),
      List.fill(n_citizens)((contact.fullName(setPrefix = false, setSuffix = false), random.nextInt(100))).toMap,
      List.fill(n_citizens)(words.sentence(random.nextInt(max_words)))
    )
  }

  def BuildReport(droneReport: DroneReport): String = droneReport.asJson.noSpaces

  def ProblemCitizens(droneReport: DroneReport, threshold: Int): (Position, Map[String, Int]) =
    (droneReport.pos, droneReport.citizens.filter(_._2 < threshold))
}