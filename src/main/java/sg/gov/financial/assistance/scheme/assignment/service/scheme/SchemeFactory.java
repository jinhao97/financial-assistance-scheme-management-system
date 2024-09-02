package sg.gov.financial.assistance.scheme.assignment.service.scheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import sg.gov.financial.assistance.scheme.assignment.exception.ApplicationException;

import java.util.Map;

@Component
public class SchemeFactory {

    private final Map<String, FinancialScheme> financialSchemeMap;

    @Autowired
    public SchemeFactory(Map<String, FinancialScheme> financialSchemeMap) {
        this.financialSchemeMap = financialSchemeMap;
    }

    public FinancialScheme getScheme(String schemeName) {
        FinancialScheme scheme = financialSchemeMap.get(schemeName);
        if (scheme == null) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Scheme " + schemeName + " not found");
        }
        return scheme;
    }
}
