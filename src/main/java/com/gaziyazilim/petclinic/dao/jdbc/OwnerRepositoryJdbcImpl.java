package com.gaziyazilim.petclinic.dao.jdbc;

import com.gaziyazilim.petclinic.dao.OwnerRepository;
import com.gaziyazilim.petclinic.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
//@Repository("ownerRepository")
public class OwnerRepositoryJdbcImpl implements OwnerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Owner> rowMapper = new RowMapper<Owner>() {
        @Override
        public Owner mapRow(ResultSet resultSet, int i) throws SQLException {
            Owner owner = new Owner();
            owner.setId(resultSet.getLong("id"));
            owner.setFirstName(resultSet.getString("first_name"));
            owner.setLastName(resultSet.getString("last_name"));

            return owner;
        }
    };

    @Override
    public List<Owner> findAll() {
         String sql = "select id,first_name,last_name from t_owner";
         return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public Owner findById(Long id) {
        String sql = "select * from t_owner where id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql,rowMapper, id));
    }

    @Override
    public List<Owner> findByLastName(String lastName) {
        String sql = "select * from t_owner where last_name like ?";
        return jdbcTemplate.query(sql,rowMapper,"%"+lastName+"%");
    }

    @Override
    public void create(Owner owner) {

    }

    @Override
    public Owner update(Owner owner) {
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from t_owner where id = ?";
        jdbcTemplate.update(sql,id);
    }
}
