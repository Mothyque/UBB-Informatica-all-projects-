package Semina6.db;

import Semina6.db.domain.Movie;
import Semina6.db.repository.MovieDBRepository;
import Semina6.db.repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository<Long, Movie> repo = new MovieDBRepository(
                "jdbc:postgresql://localhost:5432/cinema",
                "postgres",
                "mathy"
        );
        repo.findAll().forEach(System.out::println);
    }
}