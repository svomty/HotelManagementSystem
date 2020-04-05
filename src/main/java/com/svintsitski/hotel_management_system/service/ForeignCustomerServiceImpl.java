package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ForeignCustomerDaoImpl;
import com.svintsitski.hotel_management_system.model.ForeignCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForeignCustomerServiceImpl implements ForeignCustomerService {
    @Autowired
    private ForeignCustomerDaoImpl customerDao;

    @Override
    public ForeignCustomer findById(int id) {
        return customerDao.findById(id);
    }

    @Override
    public void add(ForeignCustomer customer) {
        customerDao.add(customer);
    }

    @Override
    public void update(ForeignCustomer customer) {
        customerDao.update(customer);
    }

    @Override
    public void delete(int id) {
        customerDao.delete(id);
    }

    }
}
