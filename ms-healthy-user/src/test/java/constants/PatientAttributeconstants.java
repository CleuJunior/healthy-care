package constants;

import br.com.cleonildo.entities.Address;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class PatientAttributeconstants {
    public static final ObjectId ID = new ObjectId("5fa4a5c3a2e74c2b6c8f50a1");
    public static final String FIRST_NAME = "Anna";
    public static final String LAST_NAME = "Vitoria";
    public static final LocalDate BIRTH_DATE = LocalDate.of(1998, 4, 28);
    public static final Integer AGE = Period.between(BIRTH_DATE, LocalDate.of(2023, 3, 12)).getYears();
    public static final Address ADDRESS = new Address("Rua do Magnolia", "Sao Paulo", "Sao Paulo", "24804399");
    public static final List<String> PHONES = List.of("(16) 92633-7053", "(54) 93858-3963");
    public static final List<String> SYMPTOMS = List.of("Náusea", "Formigamento nos pés");
}
