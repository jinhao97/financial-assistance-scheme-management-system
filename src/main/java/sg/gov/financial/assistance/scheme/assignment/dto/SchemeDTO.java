package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemeDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "displayName")
    private String displayName;

    @JsonProperty(value = "startDate")
    private LocalDate startDate;

    @JsonProperty(value = "endDate")
    private LocalDate endDate;

    @JsonProperty("criteria")
    private Map<String, String> criteria = new HashMap<>();

    public SchemeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SchemeDTO(Long id, String name, String description, String displayName, LocalDate startDate, LocalDate endDate, Map<String, String> criteria) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.displayName = displayName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.criteria = criteria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Map<String, String> getCriteria() {
        return criteria;
    }

    public void setCriteria(Map<String, String> criteria) {
        this.criteria = criteria;
    }

}
