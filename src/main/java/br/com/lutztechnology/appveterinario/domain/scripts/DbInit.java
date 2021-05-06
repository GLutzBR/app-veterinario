package br.com.lutztechnology.appveterinario.domain.scripts;

import br.com.lutztechnology.appveterinario.domain.model.*;
import br.com.lutztechnology.appveterinario.domain.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final AnimalRepository animalRepository;
    private final CustomerRepository customerRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public DbInit(
            AnimalRepository animalRepository,
            CustomerRepository customerRepository,
            MedicalRecordRepository medicalRecordRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            VeterinarianRepository veterinarianRepository) {

        this.animalRepository = animalRepository;
        this.customerRepository = customerRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.veterinarianRepository = veterinarianRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        // User Authorization and Authentication Init
        List<Role> existentRoles = roleRepository.findAll();
        List<User> existentUsers = userRepository.findAll();
        List<Veterinarian> existentVeterinarians = veterinarianRepository.findAll();

        // App Data Init
        List<Animal> existentAnimals = animalRepository.findAll();
        List<Customer> existentCustomers = customerRepository.findAll();
        List<MedicalRecord> existentMedicalRecords = medicalRecordRepository.findAll();


        if (existentRoles.isEmpty() && existentUsers.isEmpty() && existentVeterinarians.isEmpty() &&
                existentAnimals.isEmpty() && existentCustomers.isEmpty() && existentMedicalRecords.isEmpty()) {

            Role userRole = new Role("USER", "Usuário");
            Role adminRole = new Role("ADMIN", "Administrador");
            Role vetRole = new Role("VET", "Médico Veterinário");

            List<Role> newRoles = Arrays.asList(userRole, vetRole, adminRole);

            Veterinarian vetAttributes = new Veterinarian(
                    "Cirurgia",
                    "CRMVSP",
                    "1234567890");

            List<Veterinarian> newVetAttributes = Collections.singletonList(vetAttributes);

            User commonUser = new User(
                    "guest_user",
                    encoder.encode("user123"),
                    "Usuário",
                    new HashSet<>(Collections.singleton(userRole)),
                    null);
            User admin = new User(
                    "admin",
                    encoder.encode("admin123"),
                    "Administrador",
                    new HashSet<>(Collections.singleton(adminRole)),
                    null);

            User vet = new User(
                    "vet",
                    encoder.encode("vet123"),
                    "Veterinário",
                    new HashSet<>(Collections.singleton(vetRole)),
                    vetAttributes);

            List<User> newUsers = Arrays.asList(commonUser, vet, admin);

            Customer owner = new Customer(
                    "Gustavo Lutz",
                    LocalDate.parse("1992-08-08"),
                    "Rua de teste, 123",
                    "11999999999");

            List<Customer> newCustomers = Collections.singletonList(owner);

            Animal pet = new Animal(
                    "Logan",
                    8,
                    "Schnauzer",
                    owner);

            List<Animal> newAnimals = Collections.singletonList(pet);

            MedicalRecord mr = new MedicalRecord(
                    LocalDate.parse("2021-03-01"),
                    "O pet está saudável",
                    pet,
                    vet);

            List<MedicalRecord> newMedicalRecords = Collections.singletonList(mr);

            this.veterinarianRepository.save(vetAttributes);
            this.roleRepository.saveAll(newRoles);
            this.userRepository.saveAll(newUsers);
            this.customerRepository.saveAll(newCustomers);
            this.animalRepository.saveAll(newAnimals);
            this.medicalRecordRepository.saveAll(newMedicalRecords);
        }
    }
}
