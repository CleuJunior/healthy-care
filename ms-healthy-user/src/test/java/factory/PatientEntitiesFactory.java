package factory;

import br.com.cleonildo.dto.PatientRequest;
import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.entities.Address;
import br.com.cleonildo.entities.Patient;
import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.Period;

@UtilityClass
public final class PatientEntitiesFactory {

    private static final ObjectId ID = new ObjectId("5fa4a5c3a2e74c2b6c8f50a1");
    private static final String FIRST_NAME = "Anna";
    private static final String LAST_NAME = "Vitoria";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1998, 4, 28);
    private static final Integer AGE = Period.between(BIRTH_DATE, LocalDate.now()).getYears();
    private static final Address ADDRESS = new Address("Rua do Magnolia", "Sao Paulo", "Sao Paulo", "24804399");
    private static final String PHONE = "(16) 92633-7053";
    private static final String SYMPTOMS = "Náusea e formigamento nos pés";

    // Update Patient
    private static final String FIRST_NAME_UPDATE = "Flavia";
    private static final String LAST_NAME_UPDATE = "Guimaraes";
    private static final LocalDate BIRTH_DATE_UPDATE = LocalDate.of(1997, 9, 22);
    private static final Integer AGE_UPDATE = Period.between(BIRTH_DATE_UPDATE, LocalDate.now()).getYears();
    private static final Address ADDRESS_UPDATE = new Address("Rua do Sodre", "Rio de Janeiro", "Rio de Janeior", "21307174");
    private static final String PHONE_UPDATE = "(21) 95122-7053";
    private static final String SYMPTOMS_UPDATE = "Náusea, dor de barriga e formigamento nos pés";

    public static Patient buildPatient() {
        return Patient.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthdate(BIRTH_DATE)
                .address(ADDRESS)
                .phone(PHONE)
                .symptoms(SYMPTOMS)
                .build();
    }

    public static Patient buildPatientUpdate() {
        return new Patient(
                ID,
                FIRST_NAME_UPDATE,
                LAST_NAME_UPDATE,
                BIRTH_DATE_UPDATE,
                ADDRESS_UPDATE,
                PHONE_UPDATE,
                SYMPTOMS_UPDATE
        );
    }

    public static PatientRequest buildPatientRequest() {
        return new PatientRequest(
                FIRST_NAME,
                LAST_NAME,
                BIRTH_DATE,
                ADDRESS,
                PHONE,
                SYMPTOMS
        );
    }

    public static PatientRequest buildPatientRequestUpdate() {
        return new PatientRequest(
                FIRST_NAME_UPDATE,
                LAST_NAME_UPDATE,
                BIRTH_DATE_UPDATE,
                ADDRESS_UPDATE,
                PHONE_UPDATE,
                SYMPTOMS_UPDATE
        );
    }

    public static PatientResponse buildPatientResponse() {
        return new PatientResponse(
                ID,
                FIRST_NAME,
                LAST_NAME,
                AGE,
                ADDRESS,
                PHONE,
                SYMPTOMS
        );
    }

    public static PatientResponse buildPatientResponseUpdate() {
        return new PatientResponse(
                ID,
                FIRST_NAME_UPDATE,
                LAST_NAME_UPDATE,
                AGE_UPDATE,
                ADDRESS_UPDATE,
                PHONE_UPDATE,
                SYMPTOMS_UPDATE
        );
    }

}
