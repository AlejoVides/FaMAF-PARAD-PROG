// =====================================================================
// Ejercicio 2: Cargar diccionarios de entidades
// =====================================================================

/**
 * Responsable de cargar colecciones de entidades nombradas desde archivos.
 *
 * Un diccionario es un archivo de texto plano donde cada línea contiene
 * el nombre de una entidad conocida del mismo tipo.
 *
 * Ejemplo — data/people.txt:
 *   Martin Odersky
 *   Alan Turing
 *   Ada Lovelace
 *
 * Ejemplo — data/languages.txt:
 *   Scala
 *   Python
 *   Haskell
 */

import scala.io.Source
object Dictionary {

  /**
   * Lee un archivo de diccionario y crea una lista de entidades del tipo indicado.
   *
   * @param filePath   ruta al archivo de diccionario (ej: "data/people.txt")
   * @param entityType tipo de entidad: "Person", "University", "ProgrammingLanguage", etc.
   * @return lista de NamedEntity del tipo correspondiente
   *
   * TODO (Ejercicio 2): Implementar este método.
   *
   *   Pasos sugeridos:
   *     1. Leer las líneas del archivo
   *     2. Para cada línea, crear la instancia de la clase correcta
   *     3. Retornar la lista de entidades creadas
   *
   *   Para crear la clase correcta según el tipo se puede usar match:
   *
   */
  def loadFromFile(filePath: String, entityType: String): List[NamedEntity] = {
    FileIO.readLines(filePath).map { line =>
        entityType match {
          case "Person"              => new Person(line)
          case "University"          => new University(line)
          case "ProgrammingLanguage" => new ProgrammingLanguage(line)
          case "Place"               => new Place(line)
          case "Organization"        => new Organization(line)
          case "Technology"          => new Technology(line)
          case _                     => throw new IllegalArgumentException(s"Tipo de entidad desconocido: $entityType") 
        }
      }
  }

  /**
   * Carga todos los diccionarios disponibles y combina sus entidades.
   *
   * @return lista con todas las entidades de todos los diccionarios
   *
   * TODO (Ejercicio 2): Implementar este método.
   *
   */
  def loadAll(): List[NamedEntity] = {
    val people = loadFromFile("data/people.txt", "Person")
    val universities = loadFromFile("data/universities.txt", "University")
    val languages = loadFromFile("data/languages.txt", "ProgrammingLanguage")
    val places = loadFromFile("data/places.txt", "Place")
    val organizations = loadFromFile("data/organizations.txt", "Organization")

    people ++ universities ++ languages ++ places ++ organizations
  }
}
