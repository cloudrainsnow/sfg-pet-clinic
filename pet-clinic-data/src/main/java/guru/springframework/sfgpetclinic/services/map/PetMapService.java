package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {

    private final VisitService visitService;

    @Autowired
    public PetMapService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pet object) {
        super.delete(object);
    }

    @Override
    public Pet save(Pet object) {
        if (object != null) {
            if (object.getVisits() != null) {
                object.getVisits().forEach(visit -> {
                    if (visit.getId() == null) {
                        Visit savedVisit = visitService.save(visit);
                        visit.setId(savedVisit.getId());
                    }
                });
            }
            return super.save(object);
        } else {
            return null;
        }
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
