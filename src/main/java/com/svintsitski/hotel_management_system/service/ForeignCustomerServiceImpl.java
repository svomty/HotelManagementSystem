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
    public ResultQuery findAll(int start, int total, String sort, String surname_filter) throws Exception {

        start = start - 1;
        total += start;

        List<Customer> customerList = customerDao.findAll(sort);
        ArrayList<Customer> customerListNew = new ArrayList();

        for (Customer customer : customerList) {
            if (customer.getSurname().toLowerCase().lastIndexOf(surname_filter.toLowerCase()) != -1) {
                customerListNew.add(customer);
            }
        }

        int count = customerListNew.size();

        if (customerListNew.size() < total) {
            total = customerListNew.size();
        }

        List<ForeignCustomer> customerForeignList = new ArrayList<>();

        if (start <= total) {
            customerListNew = new ArrayList<>(customerListNew.subList(start, total));

            customerListNew.forEach(x -> customerForeignList.add(foreignCustomerDao.findById(x.getId())));
        }

        return new ResultQuery(count, Arrays.asList(customerListNew, customerForeignList));
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
