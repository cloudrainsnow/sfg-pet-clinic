package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface VisitRepository extends CrudRepository<Visit, Long> {
    public Set<Visit> findVisitsByPet(Pet pet);
}
