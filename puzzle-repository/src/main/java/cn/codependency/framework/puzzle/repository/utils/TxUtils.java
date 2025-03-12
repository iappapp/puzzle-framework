package cn.codependency.framework.puzzle.repository.utils;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public class TxUtils {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T newTx(Supplier<T> supplier) {
        return supplier.get();
    }

}
