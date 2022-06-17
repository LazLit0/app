package de.freerider.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.freerider.datamodel.Customer;

@Component
public class CustomerRepository implements CrudRepository<Customer, Long> {
    private HashMap<Long, Customer> customers;
    public CustomerRepository(){
        this.customers = new HashMap<Long, Customer>();
    }
    @Override
    public <S extends Customer> S save(S entity) {
        if(entity == null){
            throw new IllegalArgumentException("entity is null");
        }
        Customer customer = (Customer) entity;
        if( this.customers.put(customer.getId(), entity) != null){
            return (S) this.customers.put(customer.getId(), entity);
        } else {
            return entity;
        }
    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
        for (Customer c : entities) {
            if(c != null){
                customers.put(c.getId(), c);
            } else {
                throw new IllegalArgumentException("Entity is null");
            }
        }
        return entities;
    }

    @Override
    public boolean existsById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("id is Null");
        }
        if(this.customers.containsKey(id)){
            return true;
        }
        return false;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("id is Null");
        }
        if(this.customers.containsKey(id)){
            return Optional.of(this.customers.get(id));
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Customer> findAll() {
        return this.customers.values();
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<Long> ids) {
        List<Customer> list=new ArrayList<Customer>();  
        for (Long id : ids) {
            if(id != null){
            list.add(this.customers.get(id));
            } else {
                throw new IllegalArgumentException("ENtity is null");
            }
        }
        return null;
    }

    @Override
    public long count() {
        return this.customers.size();
    }

    @Override
    public void deleteById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id is Null");
        }
        this.customers.remove(id);
        
    }

    @Override
    public void delete(Customer entity) {
        if(entity == null){
            throw new IllegalArgumentException("Id is Null");
        } 
        this.customers.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        for (Long id : ids) {
            if(id == null){
                throw new IllegalArgumentException("Id is Null");
            } 
            this.customers.remove(id);
        }
        
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
      if(entities == null){
        throw new IllegalArgumentException("Iterable is Null");
      }
       for (Customer customer : entities) {
        this.customers.remove(customer.getId());
       } 
    }

    @Override
    public void deleteAll() {
        this.customers.clear();
        
    }
    
}
