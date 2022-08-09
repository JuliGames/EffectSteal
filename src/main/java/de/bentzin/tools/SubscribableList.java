package de.bentzin.tools;

import java.util.*;

//This class is copied from my Tookkit Project

/**
 * @author Ture Bentzin
 * @param <E>
 */
public class SubscribableList<E> extends ArrayList<E> {

    public SubscribableList(int initialCapacity) {
        super(initialCapacity);
        initTypeListMap();
    }

    public SubscribableList() {
        initTypeListMap();
    }

    private SubscribableList(Collection<E> eCollection) {
        super(eCollection);
        initTypeListMap();
    }


    private void initTypeListMap(){
        for (SubscriptionType value : SubscriptionType.values()) {
            typeListMap.put(value,new ArrayList<>());
        }
    }

    private final Map<SubscriptionType,List<Subscription<E>>> typeListMap = new HashMap<>();

    public boolean subscribe(Subscription<E> subscription, SubscriptionType subscriptionType) {
        List<Subscription<E>> subscriptionList = typeListMap.get(subscriptionType);
        if(subscriptionList == null) throw new IllegalStateException(subscriptionType.name() +" is not allowed in " + this.getClass().getSimpleName());
        return subscriptionList.add(subscription);
    }

    public boolean unsubscribe(Subscription<E> subscription, SubscriptionType subscriptionType) {
        List<Subscription<E>> subscriptionList = typeListMap.get(subscriptionType);
        if(subscriptionList == null) throw new IllegalStateException(subscriptionType.name() +" is not allowed in " + this.getClass().getSimpleName());
        return subscriptionList.remove(subscription);
    }

    private void addition(E element) {
        for (Subscription<E> eSubscription : typeListMap.get(SubscriptionType.ADD)) {
            eSubscription.subs(element, SubscriptionType.ADD);
        }
    }

    private void removal(E element) {
        for (Subscription<E> eSubscription : typeListMap.get(SubscriptionType.REMOVE)) {
            eSubscription.subs(element, SubscriptionType.REMOVE);
        }
    }


    @Override
    public void add(int index, E element) {
        super.add(index, element);
        addition(element);
    }

    @Override
    public boolean add(E e) {
        boolean add = super.add(e);
        addition(e);
        return add;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean addAll = super.addAll(c);
        for (E e : c) {
            addition(e);
        }
        return addAll;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean addAll = super.addAll(index, c);
        for (E e : c) {
            addition(e);
        }
        return addAll;
    }

    @Override
    public E remove(int index) {
        E remove = super.remove(index);
        removal(remove);
        return remove;
    }


    @Override
    public boolean remove(Object o) {
        boolean remove = super.remove(o);
        removal((E) o);
        return remove;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = super.removeAll(c);
        for (Object o : c) {
            removal((E) o);
        }
        return b;
    }

    public interface Subscription<E> {
        public void subs(E e, SubscriptionType subscriptionType);
    }


    public enum SubscriptionType {
        ADD(new ArrayList<>()),
        REMOVE(new ArrayList<>());

        final List<Subscription> subscriptions;
        SubscriptionType(List<Subscription> subscriptions) {
            this.subscriptions = subscriptions;
        }
    }
}

