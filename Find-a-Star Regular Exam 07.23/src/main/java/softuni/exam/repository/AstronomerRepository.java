package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;

import java.util.List;
import java.util.Optional;

// TODO:
@Repository
public interface AstronomerRepository extends JpaRepository<Astronomer, Long> {

    Optional<Astronomer> findFirstByFirstNameAndLastName(String firstName, String lastName);



}
