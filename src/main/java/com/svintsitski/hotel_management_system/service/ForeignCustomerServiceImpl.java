package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.CustomerDaoImpl;
import com.svintsitski.hotel_management_system.dao.ForeignCustomerDaoImpl;
import com.svintsitski.hotel_management_system.model.Customer;
import com.svintsitski.hotel_management_system.model.ForeignCustomer;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ForeignCustomerServiceImpl implements ForeignCustomerService {
    @Autowired
    private ForeignCustomerDaoImpl foreignCustomerDao;
    @Autowired
    private CustomerDaoImpl customerDao;

    @Override
    public ResultQuery findAll(int start, int total, String sort) throws Exception {
        List<Customer> customerList = customerDao.findAll(start, total, sort).getList();
        List<ForeignCustomer> customerForeignList = new ArrayList<>();
        customerList.forEach(x -> customerForeignList.add(foreignCustomerDao.findById(x.getId())));
        return new ResultQuery(customerDao.findAll(start, total, sort).getCount(),
                Arrays.asList(customerList, customerForeignList));
    }

    @Override
    public ForeignCustomer findById(int id) {
        return foreignCustomerDao.findById(id);
    }

    @Override
    public void add(ForeignCustomer customer) {
        foreignCustomerDao.add(customer);
    }

    @Override
    public void update(ForeignCustomer customer) {
        foreignCustomerDao.update(customer);
    }

    @Override
    public void delete(int id) {
        foreignCustomerDao.delete(id);
    }

}
