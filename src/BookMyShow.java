import enums.City;
import enums.SeatCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookMyShow {
    MovieController movieController;
    TheatreController theatreController;

    BookMyShow(){
        movieController = new MovieController();
        theatreController = new TheatreController();
    }

    public static void main(String[] args){
        BookMyShow bookMyShow = new BookMyShow();
        bookMyShow.initialize();

        bookMyShow.createBooking(City.BANGALORE, "BAAHUBALI", 1);
        bookMyShow.createBooking(City.BANGALORE, "BAAHUBALI", 1);
    }

    private void createBooking(City userCity, String movieName, int theatreId){
        //1. search movie by my location
        List<Movie> movies = movieController.getMoviesByCity(userCity);

        //2. select the movie which you want to see. i want to see Baahubali
        Movie interestedMovie = null;
        for(Movie movie : movies){
            if(movie.getMovieName().equals(movieName)){
                interestedMovie = movie;
                break;
            }
        }

        //3. get all show of this movie in Bangalore location
        Map<Theatre, List<Show>> showTheatreWise = theatreController.getAllShow(interestedMovie, userCity);

        //4. select the particular show user is interested in
        Theatre theatre = theatreController.getTheatre(theatreId, userCity);
        Show interestedShow = theatre.getShows().get(0);

        //5. select the seat
        int seatNumber = 30;
        List<Integer> bookedSeats = interestedShow.getBookedSeatIds();
        if(!bookedSeats.contains(seatNumber)){
            bookedSeats.add(seatNumber);
            //start Payment
            Booking booking = new Booking();
            List<Seat> myBookedSeats = new ArrayList<>();

            for(Seat screenSeat : interestedShow.getScreen().getSeats()){
                if(screenSeat.seatId == seatNumber){
                    myBookedSeats.add(screenSeat);
                }
            }
            booking.setBookedSeats(myBookedSeats);
            booking.setShow(interestedShow);

            System.out.println("BOOKING SUCCESSFUL");
        } else {
            System.out.println("Seat " + seatNumber + " is already booked, Try again!!");
        }
    }

    private void initialize(){
        createMovies();
        createTheatre();
    }

    //create movies
    private void createMovies(){
        //create Movies1
        Movie avengers = new Movie();
        avengers.setMovieId(1);
        avengers.setMovieName("AVENGERS");
        avengers.setMovieDurationInMinutes(120);

        //create Movies2
        Movie baahubali = new Movie();
        baahubali.setMovieId(2);
        baahubali.setMovieName("BAAHUBALI");
        baahubali.setMovieDurationInMinutes(180);

        //add movies against the cities
        movieController.addMovie(avengers, City.BANGALORE);
        movieController.addMovie(avengers, City.DELHI);

        movieController.addMovie(baahubali, City.BANGALORE);
        movieController.addMovie(baahubali, City.DELHI);
    }

    //create theater with screens, seats and shows
    private void createTheatre(){
        Movie avenger = movieController.getMovieByName("AVENGERS");
        Movie baahubali = movieController.getMovieByName("BAAHUBALI");

        //Creating inox Theatre in Bangalore
        Theatre inoxTheatre = new Theatre();
        inoxTheatre.setTheatreId(1);
        inoxTheatre.setScreens(createScreen());
        inoxTheatre.setCity(City.BANGALORE);
        List<Show> inoxShows = new ArrayList<>();

        Show inoxMorningShow = createShows(
                1, inoxTheatre.getScreens().get(0), avenger, 10);
        Show inoxEveningShow = createShows(
                2, inoxTheatre.getScreens().get(0), baahubali, 16);

        inoxShows.add(inoxMorningShow);
        inoxShows.add(inoxEveningShow);
        inoxTheatre.setShows(inoxShows);

        //Creating pvr Theatre in Delhi
        Theatre pvrTheatre = new Theatre();
        pvrTheatre.setTheatreId(2);
        pvrTheatre.setScreens(createScreen());
        pvrTheatre.setCity(City.DELHI);
        List<Show> pvrShows = new ArrayList<>();

        Show pvrMorningShow = createShows(
                3, pvrTheatre.getScreens().get(0), avenger, 11);
        Show pvrEveningShow = createShows(
                4, pvrTheatre.getScreens().get(0), baahubali, 18);

        pvrShows.add(pvrMorningShow);
        pvrShows.add(pvrEveningShow);
        pvrTheatre.setShows(pvrShows);

        //Adding inox and pvr theatres to allTheatre list
        theatreController.addTheatre(inoxTheatre, City.BANGALORE);
        theatreController.addTheatre(pvrTheatre, City.DELHI);
    }

    private List<Screen> createScreen(){
        List<Screen> screens = new ArrayList<>();
        Screen screen1 = new Screen();
        screen1.setScreenId(1);
        screen1.setSeats(createSeats());
        screens.add(screen1);
        return screens;
    }

    private List<Seat> createSeats(){
        //creating 100 seats for testing purpose, this can be generalised
        List<Seat> seats = new ArrayList<>();

        //1 to 40 : SILVER
        for(int i=1; i<=40; i++){
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.SILVER);
            seats.add(seat);
        }

        //41 to 70 : GOLD
        for(int i=41; i<=70; i++){
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.GOLD);
            seats.add(seat);
        }

        //71 to 100 : PLATINUM
        for(int i=71; i<=100; i++){
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.PLATINUM);
            seats.add(seat);
        }

        return seats;
    }

    private Show createShows(int showId, Screen screen, Movie movie, int showStartTime){
        Show show = new Show();
        show.setShowId(showId);
        show.setScreen(screen);
        show.setMovie(movie);
        show.setShowStartTime(showStartTime);
        return show;
    }
}
