package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.CustomerDaoImpl;
import com.svintsitski.hotel_management_system.dao.ForeignCustomerDaoImpl;
import com.svintsitski.hotel_management_system.model.database.Customer;
import com.svintsitski.hotel_management_system.model.database.ForeignCustomer;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
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
    public int add(ForeignCustomer customer) {
        return foreignCustomerDao.add(customer);
    }

    @Override
    public int update(ForeignCustomer customer) {
        return foreignCustomerDao.update(customer);
    }

    @Override
    public int delete(int id) {
        return foreignCustomerDao.delete(id);
    }

}
