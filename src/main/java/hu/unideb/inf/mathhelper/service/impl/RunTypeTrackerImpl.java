package hu.unideb.inf.mathhelper.service.impl;

import hu.unideb.inf.mathhelper.service.RunTypeTracker;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
public class RunTypeTrackerImpl implements RunTypeTracker {

    private boolean isRunByJar;

    @PostConstruct
    private void checkRunType() {
        String protocol = Objects.requireNonNull(this.getClass().getClassLoader().getResource("application.properties")).getProtocol();
        isRunByJar = protocol.equals("jar");
    }

    @Override
    public boolean isApplicationRunByJar() {
        return isRunByJar;
    }
}
