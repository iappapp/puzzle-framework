package cn.codependency.framework.puzzle.event.processor;

import cn.codependency.framework.puzzle.event.Event;
import cn.codependency.framework.puzzle.event.annotation.EventListener;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.*;

public class EventHandlerRegistry {

    public static ImmutableList<Method> getAnnotatedMethods(Class<?> clazz) {
        Set<? extends Class<?>> supertypes = TypeToken.of(clazz).getTypes().rawTypes();
        Map<MethodIdentifier, Method> identifiers = Maps.newHashMap();
        Iterator iterator = supertypes.iterator();

        while (iterator.hasNext()) {
            Class<?> supertype = (Class) iterator.next();
            Method[] methods = supertype.getDeclaredMethods();
            int size = methods.length;

            for (int i = 0; i < size; ++i) {
                Method method = methods[i];
                if (method.isAnnotationPresent(EventListener.class) && !method.isSynthetic()) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        continue;
                    }
                    if (Event.class.isAssignableFrom(parameterTypes[0])) {
                        EventHandlerRegistry.MethodIdentifier ident = new EventHandlerRegistry.MethodIdentifier(method);
                        if (!identifiers.containsKey(ident)) {
                            identifiers.put(ident, method);
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(identifiers.values());
    }

    public static final class MethodIdentifier {
        private final String name;
        private final List<Class<?>> parameterTypes;

        public MethodIdentifier(Method method) {
            this.name = method.getName();
            this.parameterTypes = Arrays.asList(method.getParameterTypes());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(new Object[]{this.name, this.parameterTypes});
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (!(o instanceof EventHandlerRegistry.MethodIdentifier)) {
                return false;
            } else {
                EventHandlerRegistry.MethodIdentifier ident = (EventHandlerRegistry.MethodIdentifier) o;
                return this.name.equals(ident.name) && this.parameterTypes.equals(ident.parameterTypes);
            }
        }
    }

}
