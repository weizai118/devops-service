package io.choerodon.devops.api.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.api.dto.DevopsServiceReqDTO;

/**
 * Created by Zenger on 2018/4/26.
 */
public class DevopsServiceValidator {

    //仅支持ip4地址
    private static final String IP_PATTERN = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    //service name
    private static final String NAME_PATTERN = "[a-z]([-a-z0-9]*[a-z0-9])?";

    private DevopsServiceValidator() {
    }

    /**
     * 参数校验
     */
    public static void checkService(DevopsServiceReqDTO devopsServiceReqDTO) {
        Long targetPort = devopsServiceReqDTO.getTargetPort();
        Long port = devopsServiceReqDTO.getPort();
        if (targetPort == null) {
            throw new CommonException("error.targetPort.notPresent");
        }
        if (port == null) {
            throw new CommonException("error.port.notPresent");
        }
        if (!checkPort(targetPort)) {
            throw new CommonException("error.targetPort.illegal");
        }
        if (!checkPort(port)) {
            throw new CommonException("error.port.illegal");
        }
        if (!Pattern.matches(NAME_PATTERN, devopsServiceReqDTO.getName())) {
            throw new CommonException("error.network.name.notMatch");
        }
        if (!StringUtils.isEmpty(devopsServiceReqDTO.getExternalIp())
                && !Pattern.matches(IP_PATTERN, devopsServiceReqDTO.getExternalIp())) {
            throw new CommonException("error.externalIp.notMatch");

        }
    }

    private static Boolean checkPort(Long port) {
        return port >= 1 && port <= 65535;
    }
}
