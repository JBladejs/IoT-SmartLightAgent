import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object FileHandler {
    fun getLinesArrayFromFile(fileName: String) : List<String> = File(fileName).useLines { it.toList() }
}