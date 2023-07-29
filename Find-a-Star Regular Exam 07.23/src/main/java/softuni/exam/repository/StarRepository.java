package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.StarExportDto;
import softuni.exam.models.entity.Star;

import java.util.List;
import java.util.Optional;


@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    Optional<Star> findFirstByName(String name);

    @Query("select new softuni.exam.models.dto.StarExportDto(s.name, s.lightYears, s.description, s.constellation.name) " +
            "from Star s " +
            "where s.starType='RED_GIANT' " +
            "and not exists (select a from Astronomer a where a.observingStar = s) " +
            "order by s.lightYears")
    List<StarExportDto> findAllRedGiantsThatHaveNeverBeenObservedOrderByLightYears();
}
