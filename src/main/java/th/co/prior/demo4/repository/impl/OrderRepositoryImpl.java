package th.co.prior.demo4.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import th.co.prior.demo4.model.BillModel;
import th.co.prior.demo4.model.OrderInquiryRequestModel;
import th.co.prior.demo4.model.OrderQueryResultModel;
import th.co.prior.demo4.model.RequestByChefModel;
import th.co.prior.demo4.repository.OrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderQueryResultModel> fetchOrderByCriteris(OrderInquiryRequestModel orderInquiryRequestModel) {
        List<Object>params=new ArrayList<>();

        String sb="SELECT * FROM employees.order where 1=1";

        if(!StringUtils.isEmpty(orderInquiryRequestModel.getOrderStatus())) {
            sb += " and order_status = ? ";
            params.add(orderInquiryRequestModel.getOrderStatus());
        }
        if(!StringUtils.isEmpty(orderInquiryRequestModel.getOrderMenu())) {
            sb += " and order_menu = ? ";
            params.add(orderInquiryRequestModel.getOrderMenu());
        }
        List<OrderQueryResultModel> result= this.jdbcTemplate.query(sb.toString(),
                new RowMapper<OrderQueryResultModel>() {
            @Override
            public OrderQueryResultModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrderQueryResultModel orderQueryResultModel=new OrderQueryResultModel();
                orderQueryResultModel.setOrderStatus(rs.getString("order_status"));
                orderQueryResultModel.setOrderBill(rs.getInt("order_bill"));
                orderQueryResultModel.setOrderMenu(rs.getInt("order_menu"));
                orderQueryResultModel.setOrderValue(rs.getInt("order_value"));
                return orderQueryResultModel;
            }

        }, params.toArray());
        return result;
    }




    @Override
    public int insertTableBill(BillModel billModel) {

        List<Object> paramList = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("  insert into bill  ");
        sb.append("  ( bill_table, bill_status, bill_waiter)   ");
        sb.append("  values   ");
        sb.append("  (?, ?, ?)  ");
        paramList.add(billModel.getBillTable());
        paramList.add(billModel.getBillStatus());
        paramList.add(billModel.getBillWaiter());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        int insertedRow = this.jdbcTemplate.update(
                con -> this.prepareForInsertTableBill(sb.toString(), con, paramList)
                , generatedKeyHolder);
        log.info("insertTableBill affect {} row", insertedRow);

        return generatedKeyHolder.getKey().intValue();
    }


    @Override
    public int insertTableOrder(OrderInquiryRequestModel orderInquiryRequestModel) {
        List<Object> paramList1 = new ArrayList<>();

        StringBuilder sb1 = new StringBuilder();
        sb1.append("INSERT INTO employees.order   ");
        sb1.append("  (order_status, order_bill, order_menu, order_value) ");
        sb1.append("  VALUES  ");
        sb1.append("  (?, ?, ?, ?)  ");
        paramList1.add(orderInquiryRequestModel.getOrderStatus());
        paramList1.add(orderInquiryRequestModel.getOrderBill());
        paramList1.add(orderInquiryRequestModel.getOrderMenu());
        paramList1.add(orderInquiryRequestModel.getOrderValue());


        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        int insertedRow1 = this.jdbcTemplate.update(
                con -> this.prepareForInsertTableBill(sb1.toString(), con, paramList1)
                , generatedKeyHolder);
        log.info("insertTableBill affect {} row", insertedRow1);

        return generatedKeyHolder.getKey().intValue();
    }


    private PreparedStatement prepareForInsertTableBill(String sql, Connection connection, List<Object> paramList) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < paramList.size(); i++) {
            int sqlI = i+1;
            preparedStatement.setObject(sqlI, paramList.get(i));
        }
        return preparedStatement;
    }



    @Override
    public Integer updateOrderStatus(OrderInquiryRequestModel orderInquiryRequestModel) {
        List<Object> paramList = new ArrayList<>();

        String sql = " UPDATE employees.order SET ";
        StringJoiner stringJoiner = new StringJoiner(",");

        if(!StringUtils.isEmpty(orderInquiryRequestModel.getOrderStatus())){
            stringJoiner.add(" order_status = ? ") ;
            paramList.add(orderInquiryRequestModel.getOrderStatus());
        }
        if(!StringUtils.isEmpty(orderInquiryRequestModel.getOrderBill())){
            stringJoiner.add(" order_bill = ? ") ;
            paramList.add(orderInquiryRequestModel.getOrderBill());
        }
        if(!StringUtils.isEmpty(orderInquiryRequestModel.getOrderMenu())){
            stringJoiner.add(" order_menu = ? ") ;
            paramList.add(orderInquiryRequestModel.getOrderMenu());
        }
        if(!StringUtils.isEmpty(orderInquiryRequestModel.getOrderValue())){
            stringJoiner.add(" order_value = ? ") ;
            paramList.add(orderInquiryRequestModel.getOrderValue());
        }


        int updatedRow = 0;
        if(paramList.size()>0 && null != orderInquiryRequestModel.getOrderId()){
            sql+= stringJoiner.toString();
            sql+= " WHERE order_id = ? ";
            paramList.add(orderInquiryRequestModel.getOrderId());
            updatedRow = this.jdbcTemplate.update(sql, paramList.toArray());
        }
        return updatedRow;
    }


}