package guru.springframework.sfgpetclinic.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setDisallowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId) {
        Pet pet = petService.findById(petId);
        Visit newVisit = new Visit();
        pet.getVisits().add(newVisit);
        newVisit.setPet(pet);
        return newVisit;
    }

    @ModelAttribute("pet")
    public Pet findPet(@PathVariable("petId") Long petId) {
        Pet pet = petService.findById(petId);
        pet.setVisits(visitService.findVisitByPet(pet));
        return  pet;
    }

    @GetMapping("/visits/new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Model model) {
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            visitService.save(visit);
            return "redirect:/owners/";
        }
    }
}
