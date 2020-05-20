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

        start = start - 1;
        total += start;

        List<Customer> customerList = customerDao.findAll(sort);
        ArrayList<Customer> customerListNew = new ArrayList();

        for (Customer customer : customerList) {
            customerListNew.add(customer);
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
    public ResultQuery findAll(String sort) throws Exception {

        List<Customer> customerList = customerDao.findAll(sort);

        List<ForeignCustomer> customerForeignList = new ArrayList<>();

        customerList.forEach(x -> customerForeignList.add(foreignCustomerDao.findById(x.getId())));

        return new ResultQuery(customerList.size(), Arrays.asList(customerList, customerForeignList));
    }

    @Override
    public ResultQuery filter(int start, int total, String sort, String fio, String password) throws Exception {
        start -= 1;
        total += start;

        ResultQuery resultQuery = findAll(sort);

        List<Customer> customers = (List<Customer>) resultQuery.getList().get(0);
        List<ForeignCustomer> foreignCustomers = (List<ForeignCustomer>) resultQuery.getList().get(1);

        for (int k = 0; k < customers.size(); k++) {
            if (!fio.equals("")) {
                String fio2 = customers.get(k).getSurname() + " " + customers.get(k).getName() + " " +
                        customers.get(k).getPatronymic();
                if (fio2.toLowerCase().lastIndexOf(fio.toLowerCase()) == -1) {
                    customers.remove(k);
                    foreignCustomers.remove(k);
                    k--;
                    continue;
                }
            }
            if (!password.equals("")) {
                if (customers.get(k).getPassport_serial_number().toLowerCase().lastIndexOf(password.toLowerCase()) == -1) {
                    customers.remove(k);
                    foreignCustomers.remove(k);
                    k--;
                    continue;
                }
            }
        }

        int count = customers.size();

        if (customers.size() < total) {
            total = customers.size();
        }

        if (start <= total) {
            customers = new ArrayList<>(customers.subList(start, total));
            foreignCustomers = new ArrayList<>(foreignCustomers.subList(start, total));
        }

        return new ResultQuery(count, Arrays.asList(customers, foreignCustomers));
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
