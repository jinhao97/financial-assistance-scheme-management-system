package sg.gov.financial.assistance.scheme.assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.gov.financial.assistance.scheme.assignment.constant.Status;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationResponse<T> {
    @JsonProperty(value = "status")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Status status = Status.SUCCESS;

    @JsonProperty(value = "data")
    T data;

    @JsonProperty(value = "message")
    List<String> message = null;

    public ApplicationResponse(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    public ApplicationResponse(Status status, T data, List<String> message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
