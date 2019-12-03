package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;
    private final OwnerService ownerService;

    public VisitController(VisitService visitService, PetService petService, OwnerService ownerService) {
        this.visitService = visitService;
        this.petService = petService;
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setDisallowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");

        webDataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
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

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        return owner;
    }

    @GetMapping("/visits/new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Model model) {
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/visits/new")
    public String processNewVisitForm(@PathVariable("ownerId") Long ownerId, @Valid Visit visit
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            visitService.save(visit);
            return "redirect:/owners/" + ownerId;
        }
    }
}
