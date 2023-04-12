package za.co.vodacom.cvm.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.web.rest.errors.ErrorConstants;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BatchException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;


    public BatchException(String defaultMessage) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage);
    }
    public BatchException(URI type, String defaultMessage) {
        super(type, defaultMessage, Status.UNPROCESSABLE_ENTITY, null, null, null, getAlertParameters(defaultMessage, defaultMessage));

    }
    public BatchException(String defaultMessage, Status status) {
        super(ErrorConstants.DEFAULT_TYPE, defaultMessage, status, null, null, null, getAlertParameters(defaultMessage, defaultMessage));
    }

    private static Map<String, Object> getAlertParameters(String path, String dateTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + dateTime);
        parameters.put("params", path);
        return parameters;
    }
}
