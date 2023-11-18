package factory;

import br.com.cleonildo.entities.Patient;
import lombok.experimental.UtilityClass;

import static constants.PatientAttributeconstants.ADDRESS;
import static constants.PatientAttributeconstants.BIRTH_DATE;
import static constants.PatientAttributeconstants.FIRST_NAME;
import static constants.PatientAttributeconstants.ID;
import static constants.PatientAttributeconstants.LAST_NAME;
import static constants.PatientAttributeconstants.PHONES;
import static constants.PatientAttributeconstants.SYMPTOMS;

@UtilityClass
public class PatientFactory {

    public static Patient buildPatient() {
        return Patient
                .builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthdate(BIRTH_DATE)
                .address(ADDRESS)
                .phones(PHONES)
                .symptoms(SYMPTOMS)
                .build();
    }

}
