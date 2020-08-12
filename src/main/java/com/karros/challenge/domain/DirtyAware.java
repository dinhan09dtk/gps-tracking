package com.karros.challenge.domain;

import java.util.Set;

public interface DirtyAware {

    Set<String> getDirtyProperties();

    void clearDirtyProperties();
}
