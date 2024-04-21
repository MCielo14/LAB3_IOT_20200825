package com.example.lab3_20200825;


import com.google.gson.annotations.SerializedName;
import java.util.List;

    public class Pelicula {

        @SerializedName("Title")
        private String title;

        @SerializedName("Year")
        private String year;

        @SerializedName("Rated")
        private String rated;

        @SerializedName("Released")
        private String released;

        @SerializedName("Runtime")
        private String runtime;

        @SerializedName("Genre")
        private String genre;

        @SerializedName("Director")
        private String director;

        @SerializedName("Writer")
        private String writer;

        @SerializedName("Actors")
        private String actors;

        @SerializedName("Plot")
        private String plot;

        @SerializedName("Language")
        private String language;

        @SerializedName("Country")
        private String country;

        @SerializedName("Awards")
        private String awards;

        @SerializedName("Poster")
        private String poster;

        @SerializedName("Ratings")
        private List<Rating> ratings;

        @SerializedName("Metascore")
        private String metascore;

        @SerializedName("imdbRating")
        private String imdbRating;

        @SerializedName("imdbVotes")
        private String imdbVotes;

        @SerializedName("imdbID")
        private String imdbID;

        @SerializedName("Type")
        private String type;

        @SerializedName("DVD")
        private String dvd;

        @SerializedName("BoxOffice")
        private String boxOffice;

        @SerializedName("Production")
        private String production;

        @SerializedName("Website")
        private String website;

        @SerializedName("Response")
        private String response;

        // Getters
        public String getTitle() { return title; }
        public String getYear() { return year; }
        public String getRated() { return rated; }
        public String getReleased() { return released; }
        public String getRuntime() { return runtime; }
        public String getGenre() { return genre; }
        public String getDirector() { return director; }
        public String getWriter() { return writer; }
        public String getActors() { return actors; }
        public String getPlot() { return plot; }
        public String getLanguage() { return language; }
        public String getCountry() { return country; }
        public String getAwards() { return awards; }
        public String getPoster() { return poster; }
        public List<Rating> getRatings() { return ratings; }
        public String getMetascore() { return metascore; }
        public String getImdbRating() { return imdbRating; }
        public String getImdbVotes() { return imdbVotes; }
        public String getImdbID() { return imdbID; }
        public String getType() { return type; }
        public String getDvd() { return dvd; }
        public String getBoxOffice() { return boxOffice; }
        public String getProduction() { return production; }
        public String getWebsite() { return website; }
        public String getResponse() { return response; }

        // Setters
        public void setTitle(String title) { this.title = title; }
        public void setYear(String year) { this.year = year; }
        public void setRated(String rated) { this.rated = rated; }
        public void setReleased(String released) { this.released = released; }
        public void setRuntime(String runtime) { this.runtime = runtime; }
        public void setGenre(String genre) { this.genre = genre; }
        public void setDirector(String director) { this.director = director; }
        public void setWriter(String writer) { this.writer = writer; }
        public void setActors(String actors) { this.actors = actors; }
        public void setPlot(String plot) { this.plot = plot; }
        public void setLanguage(String language) { this.language = language; }
        public void setCountry(String country) { this.country = country; }
        public void setAwards(String awards) { this.awards = awards; }
        public void setPoster(String poster) { this.poster = poster; }
        public void setRatings(List<Rating> ratings) { this.ratings = ratings; }
        public void setMetascore(String metascore) { this.metascore = metascore; }
        public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }
        public void setImdbVotes(String imdbVotes) { this.imdbVotes = imdbVotes; }
        public void setImdbID(String imdbID) { this.imdbID = imdbID; }
        public void setType(String type) { this.type = type; }
        public void setDvd(String dvd) { this.dvd = dvd; }
        public void setBoxOffice(String boxOffice) { this.boxOffice = boxOffice; }
        public void setProduction(String production) { this.production = production; }
        public void setWebsite(String website) { this.website = website; }
        public void setResponse(String response) { this.response = response; }

        // Rating class
        public static class Rating {

            @SerializedName("Source")
            private String source;

            @SerializedName("Value")
            private String value;

            // Getters and Setters
            public String getSource() { return source; }
            public void setSource(String source) { this.source = source; }
            public String getValue() { return value; }
            public void setValue(String value) { this.value = value; }
        }
    }


