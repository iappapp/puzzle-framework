package cn.codependency.framework.puzzle.common;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;

public class DeepEquals {

    private EqualsOption equalsOption;

    public DeepEquals() {
        equalsOption = new EqualsDefaultOption();
    }

    public DeepEquals(EqualsOption deepEqualsDefaultOption) {
        equalsOption = deepEqualsDefaultOption;
    }

    public boolean isDeepEquals(Object a, Object b) {
        return doDeepEquals(a, b);
    }

    private boolean doDeepEquals(Object a, Object b) {
        DeepEqualsRecursiveObject deepEqualsRecursiveObject = new DeepEqualsRecursiveObject();
        deepEqualsRecursiveObject.push(new EqualsPair(a, b));

        return equalsRecursively(deepEqualsRecursiveObject);
    }

    private boolean equalsRecursively(DeepEqualsRecursiveObject deepEqualsRecursiveObject) {
        while (!deepEqualsRecursiveObject.isEmpty()) {
            EqualsPair equalsPair = deepEqualsRecursiveObject.pop();
            if (equalsPair.a == equalsPair.b) {
                continue;
            }

            if (!equalsPair.validateType()) {
                return false;
            }

            BiPredicate<EqualsPair, DeepEqualsRecursiveObject> compareFunction = getCompareFunction(equalsPair);
            if (compareFunction != null) {
                if (!compareFunction.test(equalsPair, deepEqualsRecursiveObject)) {
                    return false;
                }

                continue;
            }

            addFieldsToCompare(equalsPair, deepEqualsRecursiveObject);
        }

        return true;
    }

    private BiPredicate<EqualsPair, DeepEqualsRecursiveObject> getCompareFunction(EqualsPair equalsPair) {
        if (equalsPair.shouldUseEqualMethod()) {
            return this::compareByEquals;
        } else if (equalsPair.isContainer()) {
            return this::compareContainer;
        } else if (shouldUseCustomEquals(equalsPair)) {
            return this::compareByCustomEquals;
        } else if (shouldUseComparator(equalsPair)) {
            return this::compareByComparator;
        }

        return null;
    }

    private boolean compareByComparator(EqualsPair equalsPair, DeepEqualsRecursiveObject deepEqualsRecursiveObject) {
        Comparator comparator = equalsOption.getUseComparatorClasses().get(equalsPair.a.getClass());
        if (comparator == null) {
            throw new RuntimeException("Do not support comparator");
        }

        return comparator.compare(equalsPair.a, equalsPair.b) == 0;
    }

    private boolean shouldUseComparator(EqualsPair equalsPair) {
        Comparator comparator = equalsOption.getUseComparatorClasses().get(equalsPair.a.getClass());

        return comparator != null;
    }

    private boolean shouldUseCustomEquals(EqualsPair equalsPair) {
        return EqualsReflectionUtils.hasCustomEquals(equalsPair.a.getClass())
                && (equalsOption.getCustomEqualsClasses().contains(equalsPair.a.getClass())
                || !equalsOption.isIgnoreCustomEquals());
    }

    private boolean compareByCustomEquals(EqualsPair equalsPair, DeepEqualsRecursiveObject deepEqualsRecursiveObject) {
        return equalsPair.a.equals(equalsPair.b);
    }

    private boolean compareByEquals(EqualsPair equalsPair, DeepEqualsRecursiveObject deepEqualsRecursiveObject) {
        if (equalsPair.a == equalsPair.b) {
            return true;
        }

        if (equalsPair.a == null || equalsPair.b == null) {
            return false;
        }

        if (equalsPair.a instanceof Double || equalsPair.b instanceof Double) {
            return compareFloatingPointNumbers(equalsPair.a, equalsPair.b, equalsOption.getDoubleOffSet());
        }

        if (equalsPair.a instanceof Float || equalsPair.b instanceof Float) {
            return compareFloatingPointNumbers(equalsPair.a, equalsPair.b, equalsOption.getFloatOffSet());
        }

        return equalsPair.a.equals(equalsPair.b);
    }

    /**
     * Compare if two floating point numbers are within a given range
     */
    private boolean compareFloatingPointNumbers(Object a, Object b, double epsilon) {
        double a1 = a instanceof Double ? (Double)a : (Float)a;
        double b1 = b instanceof Double ? (Double)b : (Float)b;
        return nearlyEqual(a1, b1, epsilon);
    }

    /**
     * Correctly handles floating point comparisions. <br>
     * source: http://floating-point-gui.de/errors/comparison/
     *
     * @param a
     *            first number
     * @param b
     *            second number
     * @param epsilon
     *            double tolerance value
     * @return true if a and b are close enough
     */
    private boolean nearlyEqual(double a, double b, double epsilon) {
        final double absA = Math.abs(a);
        final double absB = Math.abs(b);
        final double diff = Math.abs(a - b);

        if (a == b) { // shortcut, handles infinities
            return true;
        } else if (a == 0 || b == 0 || diff < Double.MIN_NORMAL) {
            // a or b is zero or both are extremely close to it
            // relative error is less meaningful here
            return diff < (epsilon * Double.MIN_NORMAL);
        } else { // use relative error
            return diff / (absA + absB) < epsilon;
        }
    }

    private boolean addFieldsToCompare(EqualsPair equalsPair, DeepEqualsRecursiveObject deepEqualsRecursiveObject) {
        Collection<Field> fields = EqualsReflectionUtils.getDeepDeclaredFields(equalsPair.a.getClass());

        Set<String> ignoredFieldNames = equalsOption.getIgnoreFieldNames().get(equalsPair.a.getClass());

        for (Field field : fields) {
            if (ignoredFieldNames != null && ignoredFieldNames.contains(field.getName())) {
                continue;
            }

            try {
                deepEqualsRecursiveObject.push(new EqualsPair(field.get(equalsPair.a), field.get(equalsPair.b)));
            } catch (Exception ignored) {
                throw new RuntimeException(ignored);
            }
        }

        return true;
    }

    private boolean compareContainer(EqualsPair equalsPair, DeepEqualsRecursiveObject deepEqualsRecursiveObject) {
        if (!equalsPair.isSameSizeOfContainer()) {
            return false;
        }

        if (equalsPair.isArrayContainer()) {
            return compareUnorderedCollection(array2List(equalsPair.a), array2List(equalsPair.b),
                    deepEqualsRecursiveObject);
        }

        if (equalsPair.isCollectionContainer()) {
            return compareUnorderedCollection((Collection)equalsPair.a, (Collection)equalsPair.b,
                    deepEqualsRecursiveObject);
        }

        if (equalsPair.isMapContainer()) {
            return compareUnorderedMap((Map)equalsPair.a, (Map)equalsPair.b, deepEqualsRecursiveObject);
        }

        return true;
    }

    private static List array2List(Object array) {
        List results = new ArrayList();

        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            results.add(Array.get(array, i));
        }

        return results;
    }

    /**
     * Deeply compare the two sets referenced by dualKey. This method attempts to quickly determine inequality by
     * length, then if lengths match, it places one collection into a temporary Map by deepHashCode(), so that it can
     * walk the other collection and look for each item in the map, which runs in O(N) time, rather than an O(N^2)
     * lookup that would occur if each item from collection one was scanned for in collection two.
     *
     * @param collectionA
     *            First collection of items to compare
     * @param collectionB
     *            Second collection of items to compare
     * @param deepEqualsRecursiveObject
     * @return boolean false if the Collections are for certain not equals. A value of 'true' indicates that the
     *         Collections may be equal, and the sets items will be added to the Stack for further comparison.
     */
    private boolean compareUnorderedCollection(Collection collectionA, Collection collectionB,
                                               DeepEqualsRecursiveObject deepEqualsRecursiveObject) {
        Map<Integer, Collection> map = collection2Map(collectionB);

        for (Object item : collectionA) {
            Collection other = map.get(EqualsReflectionUtils.deepHashCode(item));
            // fail fast: item not even found in other Collection, no need to continue.
            if (other == null || other.isEmpty()) {
                return false;
            }

            // no hash collision, items must be equivalent or isDeepEquals is false
            if (other.size() == 1) {
                deepEqualsRecursiveObject.push(new EqualsPair(item, other.iterator().next()));
            } else {
                // hash collision: try all collided items against the current item (if 1 equals, we are good - remove it
                // from collision list, making further comparisons faster)
                if (!isContained(item, other)) {
                    return false;
                }
            }
        }

        return true;
    }

    private Map<Integer, Collection> collection2Map(Collection collection) {
        Map<Integer, Collection> map = new HashMap<>();
        for (Object item : collection) {
            int hash = EqualsReflectionUtils.deepHashCode(item);
            Collection items = map.get(hash);
            if (items == null) {
                items = new ArrayList();
                map.put(hash, items);
            }
            items.add(item);
        }
        return map;
    }

    private boolean compareUnorderedMap(Map mapA, Map mapB, DeepEqualsRecursiveObject deepEqualsRecursiveObject) {
        Map<Integer, Collection<Map.Entry>> mapEntryB = initMapByHashCode(mapB);

        for (Map.Entry entryA : (Set<Map.Entry>)mapA.entrySet()) {
            Collection<Map.Entry> other = mapEntryB.get(EqualsReflectionUtils.deepHashCode(entryA.getKey()));
            if (other == null || other.isEmpty()) {
                return false;
            }

            if (other.size() == 1) {
                Map.Entry entryB = other.iterator().next();

                deepEqualsRecursiveObject.push(new EqualsPair(entryA.getKey(), entryB.getKey()));
                deepEqualsRecursiveObject.push(new EqualsPair(entryA.getValue(), entryB.getValue()));
            } else {
                // hash collision: try all collided items against the current item (if 1 equals, we are good - remove it
                // from collision list, making further comparisons faster)
                if (!isContained(new AbstractMap.SimpleEntry(entryA.getKey(), entryA.getValue()), other)) {
                    return false;
                }
            }
        }

        return true;
    }

    private Map<Integer, Collection<Map.Entry>> initMapByHashCode(Map map2) {
        Map<Integer, Collection<Map.Entry>> fastLookup = new HashMap<>();

        for (Map.Entry entry : (Set<Map.Entry>)map2.entrySet()) {
            int hash = EqualsReflectionUtils.deepHashCode(entry.getKey());
            Collection items = fastLookup.get(hash);
            if (items == null) {
                items = new ArrayList();
                fastLookup.put(hash, items);
            }

            // Use only key and value, not specific Map.Entry type for equality check.
            // This ensures that Maps that might use different Map.Entry types still compare correctly.
            items.add(new AbstractMap.SimpleEntry(entry.getKey(), entry.getValue()));
        }
        return fastLookup;
    }

    private boolean isContained(Object o, Collection other) {
        Iterator i = other.iterator();
        while (i.hasNext()) {
            Object x = i.next();
            if (isDeepEquals(o, x)) {
                // can only be used successfully once - remove from list
                i.remove();
                return true;
            }
        }
        return false;
    }

    public static Collection<Field> getDeepDeclaredFields(Class<?> c) {
        return EqualsReflectionUtils.getDeepDeclaredFields(c);
    }

    public static <T> Set<String> getChangedFields(T old, T current, String... ignoredFields) {
        Set<String> results = new HashSet();
        Set<String> ignoreFieldSet = new HashSet(Arrays.asList(ignoredFields));
        Collection<Field> fields = DeepEquals.getDeepDeclaredFields(current.getClass());
        Iterator fieldIterator = fields.iterator();

        while (fieldIterator.hasNext()) {
            Field field = (Field)fieldIterator.next();
            if (!ignoreFieldSet.contains(field.getName())) {
                try {
                    if (!(new DeepEquals()).isDeepEquals(field.get(old), field.get(current))) {
                        results.add(field.getName());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return results;
    }

    static class EqualsPair {
        public final Object a;
        public final Object b;

        private static final Set<Class> classUseEquals = new HashSet<>();

        static {
            classUseEquals.add(Byte.class);
            classUseEquals.add(Integer.class);
            classUseEquals.add(Long.class);
            classUseEquals.add(Double.class);
            classUseEquals.add(Character.class);
            classUseEquals.add(Float.class);
            classUseEquals.add(Boolean.class);
            classUseEquals.add(Short.class);
            classUseEquals.add(Date.class);
            classUseEquals.add(String.class);

            classUseEquals.add(Class.class);
        }

        public EqualsPair(Object a, Object b) {
            this.a = a;
            this.b = b;
        }

        public boolean validateType() {
            if (a == null || b == null) {
                return false;
            }

            if (!isTypeComparable()) {
                return false;
            }

            return true;
        }

        private <T> boolean collectionOrMapTypeIsMatch(Class<T> type) {
            if (type.isInstance(a)) {
                return type.isInstance(b);
            }

            if (type.isInstance(b)) {
                return type.isInstance(a);
            }

            return true;
        }

        public boolean isTypeComparable() {
            if (a.getClass().equals(b.getClass())) {
                return true;
            }

            if (!isContainerType(a) || !isContainerType(b)) {
                return false;
            }

            Class[] classes = new Class[] {Collection.class, SortedSet.class, SortedMap.class, Map.class};
            for (Class aClass : classes) {
                if (!collectionOrMapTypeIsMatch(aClass)) {
                    return false;
                }
            }

            return true;
        }

        private boolean isContainerType(Object o) {
            return o instanceof Collection || o instanceof Map;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof EqualsPair)) {
                return false;
            }

            EqualsPair that = (EqualsPair)other;
            return a == that.a && b == that.b;
        }

        @Override
        public int hashCode() {
            int h1 = a != null ? a.hashCode() : 0;
            int h2 = b != null ? b.hashCode() : 0;
            return h1 + h2;
        }

        public boolean shouldUseEqualMethod() {
            Class key1Class = a.getClass();

            return key1Class.isPrimitive() || classUseEquals.contains(key1Class);
        }

        public boolean isContainer() {
            return isArrayContainer() || isCollectionContainer() || isMapContainer();
        }

        public boolean isArrayContainer() {
            return a.getClass().isArray();
        }

        public boolean isCollectionContainer() {
            return a instanceof Collection;
        }

        public boolean isMapContainer() {
            return a instanceof Map;
        }

        public boolean isSameSizeOfContainer() {
            if (a.getClass().isArray()) {
                return Array.getLength(a) == Array.getLength(b);
            }

            if (a instanceof Collection) {
                return ((Collection)a).size() == ((Collection)b).size();
            }

            if (a instanceof Map) {
                return ((Map)a).size() == ((Map)b).size();
            }

            throw new RuntimeException("It's not a container, can't use this method.");
        }
    }

    static public class EqualsOption {
        private boolean ignoreCustomEquals = true;
        private final Set<Class> useCustomEqualsClasses = new HashSet<>();
        private final Map<Class, Comparator> useComparatorClasses = new HashMap<>();
        private final Map<Class, Set<String>> ignoreFieldNames = new HashMap<>();
        private double doubleOffSet = 1e-15;
        private double floatOffSet = 1e-6;

        public EqualsOption() {}

        /**
         * When compare two object, if ignoreCustomEquals is true, DeepEquals will ignore custom equals method (unless
         * it was included in the customEqualsClasses), and compare them field by field.
         *
         * @return true if ignore custom equals method. false if use custom equals method to compare two objects.
         */
        public boolean isIgnoreCustomEquals() {
            return ignoreCustomEquals;
        }

        /**
         * Using equals method to compare
         *
         * @return Classes using equals method to compare with another object.
         */
        public Set<Class> getCustomEqualsClasses() {
            return useCustomEqualsClasses;
        }

        /**
         * Using comparator to compare
         *
         * @return Classes and their comparator.
         */
        public Map<Class, Comparator> getUseComparatorClasses() {
            return useComparatorClasses;
        }

        /**
         * set whether ignore custom equals method when compare two objects.
         *
         * @param ignoreCustomEquals
         *            ignoreCustomEquals
         */
        public void setIgnoreCustomEquals(boolean ignoreCustomEquals) {
            this.ignoreCustomEquals = ignoreCustomEquals;
        }

        /**
         * When compare two double number, doubleOffSet is used to decide whether they are equals. For example, 1.001 is
         * equals to 1.00101 if offset is equal or larger then 0.00001
         *
         * @return the offset
         */
        public double getDoubleOffSet() {
            return doubleOffSet;
        }

        /**
         * Set the double offset.
         *
         * When compare two double number, doubleOffSet is used to decide whether they are equals. For example, 1.001 is
         * equals to 1.00101 if offset is equal or larger then 0.00001
         *
         * @param doubleOffSet
         *            doubleOffSet
         */
        public void setDoubleOffSet(double doubleOffSet) {
            this.doubleOffSet = doubleOffSet;
        }

        /**
         * When compare two float number, floatOffSet is used to decide whether they are equals. For example, 1.001 is
         * equals to 1.00101 if offset is equal or larger then 0.00001
         *
         * @return the offset
         */
        public double getFloatOffSet() {
            return floatOffSet;
        }

        /**
         * Set the float offset.
         *
         * When compare two float number, floatOffSet is used to decide whether they are equals. For example, 1.001 is
         * equals to 1.00101 if offset is equal or larger then 0.00001
         *
         * @param floatOffSet
         *            floatOffSet
         */
        public void setFloatOffSet(double floatOffSet) {
            this.floatOffSet = floatOffSet;
        }

        /**
         * Specify the field names to ignore when compare the Class instance. For example, to compare Person.class, we
         * can specify the ignoreFieldNames include : age and children
         *
         * @return a map
         */
        public Map<Class, Set<String>> getIgnoreFieldNames() {
            return ignoreFieldNames;
        }
    }

    class EqualsDefaultOption extends EqualsOption {
        /**
         * The offset of BigDecimal. If the diff of two BigDecimal value less than the offset, regard them as equal.
         */
        private BigDecimal bigDecimalOffset = new BigDecimal("0.000001");

        public EqualsDefaultOption() {
            getUseComparatorClasses().put(BigDecimal.class, getBigDecimalComparator());
        }

        private Comparator getBigDecimalComparator() {
            return (o1, o2) -> {
                BigDecimal value1 = (BigDecimal)o1;
                BigDecimal value2 = (BigDecimal)o2;

                return (value1.subtract(value2, MathContext.DECIMAL128).abs()
                        .subtract(bigDecimalOffset, MathContext.DECIMAL128).stripTrailingZeros()
                        .compareTo(BigDecimal.ZERO) <= 0) ? 0 : 1;
            };
        }

        public BigDecimal getBigDecimalOffset() {
            return bigDecimalOffset;
        }

        public void setBigDecimalOffset(BigDecimal bigDecimalOffset) {
            this.bigDecimalOffset = bigDecimalOffset;
        }
    }

    static class DeepEqualsRecursiveObject {
        private Set<DeepEquals.EqualsPair> visited = new HashSet<>();
        private Stack<EqualsPair> stack = new Stack<>();

        private void addVisited(DeepEquals.EqualsPair object) {
            visited.add(object);
        }

        public void push(DeepEquals.EqualsPair dk) {
            if (!visited.contains(dk)) {
                stack.push(dk);
            }
        }

        public boolean isEmpty() {
            return stack.isEmpty();
        }

        public DeepEquals.EqualsPair pop() {
            DeepEquals.EqualsPair object = stack.pop();
            addVisited(object);

            return object;
        }
    }

    private static final class EqualsReflectionUtils {
        private static final Map<Class, Collection<Field>> _reflectedFields = new ConcurrentHashMap<>();
        private static final Map<Class, Boolean> _customEquals = new ConcurrentHashMap<>();
        private static final Map<Class, Boolean> _customHash = new ConcurrentHashMap<>();

        private EqualsReflectionUtils() {
            super();
        }

        /**
         * Get all non static, non transient, fields of the passed in class, including private fields. Note, the special
         * this$ field is also not returned. The result is cached in a static ConcurrentHashMap to benefit execution
         * performance.
         *
         * @param c
         *            Class instance
         * @return Collection of only the fields in the passed in class that would need further processing (reference
         *         fields). This makes field traversal on a class faster as it does not need to continually process
         *         known fields like primitives.
         */
        public static Collection<Field> getDeepDeclaredFields(Class<?> c) {
            if (_reflectedFields.containsKey(c)) {
                return _reflectedFields.get(c);
            }
            Collection<Field> fields = new ArrayList<>();
            Class curr = c;

            while (curr != null) {
                getDeclaredFields(curr, fields);
                curr = curr.getSuperclass();
            }
            _reflectedFields.put(c, fields);
            return fields;
        }

        /**
         * Get all non static, non transient, fields of the passed in class, including private fields. Note, the special
         * this$ field is also not returned. The resulting fields are stored in a Collection.
         *
         * @param c
         *            Class instance that would need further processing (reference fields). This makes field traversal
         *            on a class faster as it does not need to continually process known fields like primitives.
         * @param fields
         *            fields
         */
        public static void getDeclaredFields(Class<?> c, Collection<Field> fields) {
            try {
                Field[] local = c.getDeclaredFields();

                for (Field field : local) {
                    try {
                        field.setAccessible(true);
                    } catch (Exception ignored) {
                    }

                    int modifiers = field.getModifiers();
                    if (!Modifier.isStatic(modifiers) && !field.getName().startsWith("this$")
                            && !Modifier.isTransient(modifiers)) { // speed up: do not count static fields, do not go back
                        // up to
                        // enclosing object in nested case, do not consider
                        // transients
                        fields.add(field);
                    }
                }
            } catch (Throwable ignored) {
                throw new RuntimeException(ignored);
            }

        }

        /**
         * Return the name of the class on the object, or "null" if the object is null.
         *
         * @param o
         *            Object to get the class name.
         * @return String name of the class or "null"
         */
        public static String getClassName(Object o) {
            return o == null ? "null" : o.getClass().getName();
        }

        /**
         * Determine if the passed in class has a non-Object.equals() method. This method caches its results in static
         * ConcurrentHashMap to benefit execution performance.
         *
         * @param c
         *            Class to check.
         * @return true, if the passed in Class has a .equals() method somewhere between itself and just below Object in
         *         it's inheritance.
         */
        public static boolean hasCustomEquals(Class<?> c) {
            Class origClass = c;
            if (_customEquals.containsKey(c)) {
                return _customEquals.get(c);
            }

            while (!Object.class.equals(c)) {
                try {
                    c.getDeclaredMethod("equals", Object.class);
                    _customEquals.put(origClass, true);
                    return true;
                } catch (Exception ignored) {
                }
                c = c.getSuperclass();
            }
            _customEquals.put(origClass, false);
            return false;
        }

        /**
         * Get a deterministic hashCode (int) value for an Object, regardless of when it was created or where it was
         * loaded into memory. The problem with java.lang.Object.hashCode() is that it essentially relies on memory
         * location of an object (what identity it was assigned), whereas this method will produce the same hashCode for
         * any object graph, regardless of how many times it is created.<br>
         * <br>
         * <p>
         * This method will handle cycles correctly (A-&gt;B-&gt;C-&gt;A). In this case, Starting with object A, B, or C
         * would yield the same hashCode. If an object encountered (root, suboject, etc.) has a hashCode() method on it
         * (that is not Object.hashCode()), that hashCode() method will be called and it will stop traversal on that
         * branch.
         *
         * @param obj
         *            Object who hashCode is desired.
         * @return the 'deep' hashCode value for the passed in object.
         */
        public static int deepHashCode(Object obj) {
            Set<Object> visited = new HashSet<>();
            LinkedList<Object> stack = new LinkedList<>();
            stack.addFirst(obj);
            int hash = 0;

            while (!stack.isEmpty()) {
                obj = stack.removeFirst();
                if (obj == null || visited.contains(obj)) {
                    continue;
                }

                visited.add(obj);

                if (obj.getClass().isArray()) {
                    int len = Array.getLength(obj);
                    for (int i = 0; i < len; i++) {
                        stack.addFirst(Array.get(obj, i));
                    }
                    continue;
                }

                if (obj instanceof Collection) {
                    stack.addAll(0, (Collection)obj);
                    continue;
                }

                if (obj instanceof Map) {
                    stack.addAll(0, ((Map)obj).keySet());
                    stack.addAll(0, ((Map)obj).values());
                    continue;
                }

                if (obj instanceof Double || obj instanceof Float) {
                    // just take the integral value for hashcode
                    // equality tests things more comprehensively
                    stack.add(Math.round(((Number)obj).doubleValue()));
                    continue;
                }

                if (hasCustomHashCode(obj.getClass())) { // A real hashCode() method exists, call it.
                    hash += obj.hashCode();
                    continue;
                }

                Collection<Field> fields = getDeepDeclaredFields(obj.getClass());
                for (Field field : fields) {
                    try {
                        stack.addFirst(field.get(obj));
                    } catch (Exception ignored) {
                    }
                }
            }
            return hash;
        }

        /**
         * Determine if the passed in class has a non-Object.hashCode() method. This method caches its results in static
         * ConcurrentHashMap to benefit execution performance.
         *
         * @param c
         *            Class to check.
         * @return true, if the passed in Class has a .hashCode() method somewhere between itself and just below Object
         *         in it's inheritance.
         */
        public static boolean hasCustomHashCode(Class<?> c) {
            Class origClass = c;
            if (_customHash.containsKey(c)) {
                return _customHash.get(c);
            }

            while (!Object.class.equals(c)) {
                try {
                    c.getDeclaredMethod("hashCode");
                    _customHash.put(origClass, true);
                    return true;
                } catch (Exception ignored) {
                }
                c = c.getSuperclass();
            }
            _customHash.put(origClass, false);
            return false;
        }
    }
}
