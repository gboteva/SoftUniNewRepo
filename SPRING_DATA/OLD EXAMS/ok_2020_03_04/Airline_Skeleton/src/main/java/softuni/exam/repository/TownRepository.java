package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    boolean existsByName(String name);

    Town findByName(String town);
}
