package factory;

import br.com.cleonildo.dto.PatientRequest;
import lombok.experimental.UtilityClass;

import static constants.PatientAttributeconstants.ADDRESS;
import static constants.PatientAttributeconstants.BIRTH_DATE;
import static constants.PatientAttributeconstants.FIRST_NAME;
import static constants.PatientAttributeconstants.LAST_NAME;
import static constants.PatientAttributeconstants.PHONES;
import static constants.PatientAttributeconstants.SYMPTOMS;

@UtilityClass
public class PatientRequestFactory {
    public static PatientRequest buildPatientRequest() {
        return new PatientRequest(FIRST_NAME,LAST_NAME, BIRTH_DATE, ADDRESS, PHONES, SYMPTOMS);
    }

}
