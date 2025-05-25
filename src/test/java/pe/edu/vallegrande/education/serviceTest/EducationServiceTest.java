package pe.edu.vallegrande.education.serviceTest;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.edu.vallegrande.education.model.Education;
import pe.edu.vallegrande.education.repository.EducationRepository;
import pe.edu.vallegrande.education.service.EducationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

public class EducationServiceTest {

    @Mock
    private EducationRepository repository;

    @InjectMocks
    private EducationService service;

    private Education testEducation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testEducation = new Education();
        testEducation.setIdEducation(1);
        testEducation.setDegreeStudy("Secundaria");
        testEducation.setGradeBook("A");
        testEducation.setGradeAverage(18);
        testEducation.setFullNotebook("Sí");
        testEducation.setAssistance("Completa");
        testEducation.setTutorials("Ninguna");
        testEducation.setSchollName("Colegio Nacional");
        testEducation.setEntryDate(LocalDate.of(2020, 1, 1));
        testEducation.setPersonId(1001);
    }

    // LISTA TODOS LOS DATOS DE EDUCATION
    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(Flux.just(testEducation));

        StepVerifier.create(service.findAll())
                .expectNext(testEducation)
                .verifyComplete();
    }

    // LISTA POR ID LA EDUCATION
    @Test
    void testFindById() {
        when(repository.findById(1)).thenReturn(Mono.just(testEducation));

        StepVerifier.create(service.findById(1))
                .expectNext(testEducation)
                .verifyComplete();
    }

    //VERIFICA SI EXISTE EL ID DE LA EDUCATION
    @Test
    void testExistsById() {
        when(repository.existsById(1)).thenReturn(Mono.just(true));

        StepVerifier.create(service.existsById(1))
                .expectNext(true)
                .verifyComplete();
    }

    //LISTA (UNO O MUCHOS DATOS) DE EDUCATION DE UNA PERSONA
    @Test
    void testGetByPersonId() {
        when(repository.findByPersonId(1001)).thenReturn(Flux.just(testEducation));

        StepVerifier.create(service.getByPersonId(1001))
                .expectNextMatches(e -> e.getPersonId().equals(1001))
                .verifyComplete();
    }
}
