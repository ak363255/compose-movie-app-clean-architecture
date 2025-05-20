package com.example.domain.models

data class MovieActor(
    val name: String,
    val image: String
)
data class MovieDetail(
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val language: String,
    val country: String,
    val awards: String,
    val poster: String,
    val ratings: ArrayList<MovieRatingData> = arrayListOf(),
    val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    val type: String,
    val dvd: String,
    val boxOffice: String,
    val production: String,
    val website: String,
    val response: String,
    val chips : List<String> = mutableListOf("Action","Drama","History"),
    val fakeActors : List<MovieActor> = listOf(
        MovieActor(
            "Jaoquin Phoenix",
            "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
        ),
        MovieActor(
            "Robert De Niro",
            "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
        ),
        MovieActor(
            "Zazie Beetz",
            "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
        ),
        MovieActor(
            "Jaoquin Phoenix",
            "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
        ),
        MovieActor(
            "Robert De Niro",
            "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
        ),
        MovieActor(
            "Zazie Beetz",
            "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
        )
    ),
)
