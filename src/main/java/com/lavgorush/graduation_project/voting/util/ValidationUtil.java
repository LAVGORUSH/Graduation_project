package com.lavgorush.graduation_project.voting.util;

import com.lavgorush.graduation_project.voting.HasId;
import com.lavgorush.graduation_project.voting.exception.IllegalRequestDataException;
import com.lavgorush.graduation_project.voting.exception.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new id=" + id);
        }
    }

    public static <T> T checkNotFoundWithId(Optional<T> optional, int id) {
        return checkNotFoundWithId(optional, "Entity with id=" + id + " not found");
    }

    public static <T> T checkNotFoundWithId(Optional<T> optional, String msg) {
        return optional.orElseThrow(() -> new NotFoundException(msg));
    }

    public static void checkSingleModification(int count, String msg) {
        if (count != 1) {
            throw new NotFoundException(msg);
        }
    }
}
