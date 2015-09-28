package com.solu4b.nrafalow.popularmoviesstage1;

/**
 * Created by nrafalow on 14-09-2015.
 */
public class Movie {

    private String originalTitle;
    private String posterPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    public void setOriginalTitle(String n){
        originalTitle = n;
    }

    public String getOriginalTitle(){
        return originalTitle;
    }

    public void setPosterPath(String n){
        posterPath = n;
    }

    public String getPosterPath(){
        return posterPath;
    }

    public void setOverview(String n){
        overview = n;
    }

    public String getOverview(){
        return overview;
    }

    public void setVoteAverage(String n){
        voteAverage = n;
    }

    public String getVoteAverage(){
        return voteAverage;
    }

    public void setReleaseDate(String n){
        releaseDate = n;
    }

    public String getReleaseDate(){
        return releaseDate;
    }
}
