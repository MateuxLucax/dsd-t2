package presentation.enums;

import domain.model.parallel.CustomMonitor;
import domain.model.parallel.CustomSemaphone;
import domain.model.parallel.Lockable;

public enum SelectedLockable {
    SEMAPHOR, MONITOR;

    public Class<? extends Lockable> toClass() {
        return switch (this) {
            case SEMAPHOR -> CustomSemaphone.class;
            case MONITOR -> CustomMonitor.class;
        };
    }
}
