package th.co.prior.demo4.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import th.co.prior.demo4.model.OrderInquiryRequestModel;
import th.co.prior.demo4.model.OrderQueryResultModel;
import th.co.prior.demo4.repository.OrderRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderQueryResultModel> fetchOrderByCriteris(OrderInquiryRequestModel orderInquiryRequestModel) {

        List<Object>params=new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT * FROM employees.order where 1=1");
        String sb="SELECT * FROM employees.order where 1=1";

        if(!StringUtils.isEmpty(orderInquiryRequestModel.getOrderStatus())) {
            sb += " and order_status = ? ";
            params.add(orderInquiryRequestModel.getOrderStatus());
        }
//        sb += " order by order_id desc ";
//        order_bill,order_menu,order_status,order_value
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
}
