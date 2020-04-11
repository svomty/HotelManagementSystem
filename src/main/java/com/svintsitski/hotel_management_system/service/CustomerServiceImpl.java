package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.CustomerDaoImpl;
import com.svintsitski.hotel_management_system.model.database.Customer;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDaoImpl customerDao;
    @Autowired
    private ForeignCustomerServiceImpl foreignCustomerService;

    @Override
    public ResultQuery findAll(int start, int total, String sort) throws Exception {
        return customerDao.findAll(start, total, sort);
    }

    @Override
    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    @Override
    public int add(Customer customer) {
        return customerDao.add(customer);
    }

    @Override
    public int update(Customer customer) {
        return customerDao.update(customer);
    }

    @Override
    public int delete(int id) {
        return customerDao.delete(id);
    }
}
