package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;

import java.util.List;
import java.util.Set;

public interface VisitService extends CrudService<Visit, Long> {
    Set<Visit> findVisitByPet(Pet pet);
}
