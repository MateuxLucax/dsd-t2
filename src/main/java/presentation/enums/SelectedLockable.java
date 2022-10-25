package presentation.enums;

import domain.model.parallel.CustomMonitor;
import domain.model.parallel.CustomSemaphone;
import domain.model.parallel.Lockable;

public enum SelectedLockable {
    SEMAPHOR, MONITOR;

    public Lockable toLockable() {
        return switch (this) {
            case SEMAPHOR -> new CustomSemaphone();
            case MONITOR -> new CustomMonitor();
        };
    }
}
