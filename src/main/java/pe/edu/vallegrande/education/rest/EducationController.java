package pe.edu.vallegrande.education.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.education.model.Education;
import pe.edu.vallegrande.education.service.EducationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/education")
public class EducationController {

    @Autowired
    private EducationService service;

    @GetMapping
    public Flux<Education> getAll() {
        return service.findAll();
    }

    @GetMapping("/person/{personId}")
    public Flux<Education> getEducationByPersonId(@PathVariable Integer personId) {
        return service.getByPersonId(personId);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Education>> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Education> create(@RequestBody Education education) {
        return service.save(education);
    }

    @PutMapping("/update-with-history/{id}")
    public Mono<ResponseEntity<Education>> updateWithHistory(@PathVariable Integer id, @RequestBody Education education) {
        return service.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        education.setIdEducation(id);

                        return service.updateEducationWithHistory(id, education)
                                .map(ResponseEntity::ok);
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Education>> updateWithoutHistory(@PathVariable Integer id, @RequestBody Education education) {
        return service.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        education.setIdEducation(id);

                        return service.updateEducationWithoutHistory(id, education)
                                .map(ResponseEntity::ok);
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return service.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return service.deleteById(id)
                                .thenReturn(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }
}
