package factory;

import br.com.cleonildo.dto.PatientResponse;
import lombok.experimental.UtilityClass;

import static constants.PatientAttributeconstants.ADDRESS;
import static constants.PatientAttributeconstants.AGE;
import static constants.PatientAttributeconstants.FIRST_NAME;
import static constants.PatientAttributeconstants.ID;
import static constants.PatientAttributeconstants.LAST_NAME;
import static constants.PatientAttributeconstants.PHONES;
import static constants.PatientAttributeconstants.SYMPTOMS;

@UtilityClass
public class PatientResponseBuilder {

    public static PatientResponse buildPatientResponse() {
        return new PatientResponse(ID, FIRST_NAME,LAST_NAME, AGE, ADDRESS, PHONES, SYMPTOMS);
    }

}
