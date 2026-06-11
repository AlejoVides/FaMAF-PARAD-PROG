// =====================================================================
// Ejercicio 6: Integración del sistema completo
// =====================================================================

object Main {
  def main(args: Array[String]): Unit = {
    // ------------------------------------------------------------------
    // Paso 1: Cargar diccionarios
    // ------------------------------------------------------------------
    val dictionary: List[NamedEntity] = Dictionary.loadAll()

    println(s"Diccionario cargado: ${dictionary.size} entidades.\n")

    // ------------------------------------------------------------------
    // Paso 2: Descargar posts
    // ------------------------------------------------------------------
    val subscriptions = FileIO.readSubscriptions()

    val allPosts: List[(String, List[String])] = subscriptions.map { url =>
      println(s"Descargando posts de:")
      println(url)
      val json   = FileIO.downloadFeed(url)
      val titles = FileIO.extractPostTitles(json)
      (url, titles)
    }

    // ------------------------------------------------------------------
    // Paso 3: Detectar entidades y mostrar resultados por post
    // ------------------------------------------------------------------
    val resultsPerPost = allPosts.flatMap { case (_, titles) =>
      titles.map { title =>
        Formatters.formatNERResult(title, Analyzer.detectEntities(title, dictionary))
      }
    }
    println(resultsPerPost.mkString("\n", "\n\n", "\n"))

    // ------------------------------------------------------------------
    // Paso 4: Estadísticas globales
    // ------------------------------------------------------------------
    val allEntities = allPosts.flatMap { case (_, titles) =>
      titles.flatMap(title => Analyzer.detectEntities(title, dictionary))
    }
    println(Formatters.formatEntityStats(Analyzer.countByType(allEntities)))
  }
}
