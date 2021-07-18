import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import faker.{Address, Lorem, Name}
import io.circe.generic.auto._
import io.circe.syntax._

object DroneReportObj {

  case class Position(lat: String, long: String)

  val dateformat = new SimpleDateFormat("y-M-d")

  case class DroneReport(id: Int, date: Date, pos: Position, citizens: Map[String, Int], words: List[String])

  def GenerateReport(max_citizens: Int, max_words: Int): DroneReport = {
    val random = scala.util.Random
    DroneReport(
      random.nextInt(1000),
      Calendar.getInstance().getTime,
      Position(Address.latitude, Address.longitude),
      List.fill(random.nextInt(max_citizens))((Name.name, random.nextInt(100))).toMap,
      Lorem.words(random.nextInt(max_words))
    )
  }

  def BuildReport(droneReport: DroneReport): String = droneReport.asJson.noSpaces

  def ProblemCitizens(droneReport: DroneReport, threshold: Int): (Position, Map[String, Int])
  = (droneReport.pos, droneReport.citizens.filter(_._2 < threshold))
}