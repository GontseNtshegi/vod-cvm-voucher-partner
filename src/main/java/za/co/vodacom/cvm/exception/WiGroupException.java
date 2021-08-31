package za.co.vodacom.cvm.exception;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.web.rest.errors.ErrorConstants;

public class WiGroupException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public WiGroupException(String defaultMessage) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage);
    }

    public WiGroupException(URI type, String defaultMessage) {
        super(type, defaultMessage, Status.UNPROCESSABLE_ENTITY, null, null, null, getAlertParameters(defaultMessage, defaultMessage));
    }

    public WiGroupException(String defaultMessage, Status status) {
        super(ErrorConstants.DEFAULT_TYPE, defaultMessage, status, null, null, null, getAlertParameters(defaultMessage, defaultMessage));
    }

    private static Map<String, Object> getAlertParameters(String path, String dateTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + dateTime);
        parameters.put("params", path);
        return parameters;
    }
}
