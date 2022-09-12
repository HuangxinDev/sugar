package com.factory.smart.http;

import com.factory.smart.http.constants.GrsDomain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author huangxin
 * @date 2022/9/11
 */
public class HostsManger {
    private static final HostsManger HOSTS_MANGER = new HostsManger();

    private static final HashSet<String> DOMAINS = new HashSet<>();

    private final HashMap<String, String> urls = new HashMap<>();
    static {
        DOMAINS.add(GrsDomain.H5_URL);
        DOMAINS.add(GrsDomain.BIZ_URL);
        DOMAINS.add(GrsDomain.VER_URL);
    }
    private HostsManger() {
    }

    static HostsManger getInstance() {
        return HOSTS_MANGER;
    }

    void loadHosts(HashMap<String, String> configs) {
        for (Map.Entry<String, String> entry : configs.entrySet()) {
            if (DOMAINS.contains(entry.getKey())) {
                urls.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public String getH5Url() {
        return urls.get(GrsDomain.H5_URL);
    }

    public String getHostUrl() {
        return urls.get(GrsDomain.BIZ_URL);
    }

    public String getVerUrl() {
        return urls.get(GrsDomain.VER_URL);
    }
}
