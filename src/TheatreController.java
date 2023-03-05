import enums.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatreController {
    Map<City, List<Theatre>> cityVsTheatre;
    List<Theatre> allTheatre;

    TheatreController(){
        cityVsTheatre = new HashMap<>();
        allTheatre = new ArrayList<>();
    }

    void addTheatre(Theatre theatre, City city){
        allTheatre.add(theatre);

        List<Theatre> theatres = cityVsTheatre.getOrDefault(city, new ArrayList<>());
        theatres.add(theatre);
        cityVsTheatre.put(city, theatres);
    }

    Map<Theatre, List<Show>> getAllShow(Movie movie, City city){
        Map<Theatre, List<Show>> theatreVsShow = new HashMap<>();
        //get all the theater of this city
        List<Theatre> theatres = cityVsTheatre.get(city);
        //filter the theatres which run this movie
        for(Theatre theatre : theatres){
            List<Show> givenMovieShows = new ArrayList<>();
            List<Show> shows = theatre.getShows();

            for(Show show : shows){
                if(show.movie.getMovieId() == movie.getMovieId()){
                    givenMovieShows.add(show);
                }
            }

            if(!givenMovieShows.isEmpty()){
                theatreVsShow.put(theatre, givenMovieShows);
            }
        }

        return theatreVsShow;
    }

    Theatre getTheatre(int theatreId, City city){
        List<Theatre> theatreList = cityVsTheatre.get(city);

        for(Theatre theatre : theatreList){
            if(theatre.getTheatreId() == theatreId){
                return theatre;
            }
        }
        System.out.println("Theatre with id:" + theatreId + " does not exist in city " + city);
        return null;
    }
}
