package pe.edu.vallegrande.education.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.education.model.Education;
import reactor.core.publisher.Flux;

public interface EducationRepository extends ReactiveCrudRepository<Education, Integer> {
    Flux<Education> findByPersonId(Integer personId);


}
