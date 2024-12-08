package com.example.demo.dao;

import com.example.demo.entities.Officer;
import com.example.demo.entities.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class JdbcOfficerDAO implements OfficerDAO {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insertOfficer;

    private final RowMapper<Officer> officerMapper =
            (ResultSet rs, int rowNum) -> new Officer(rs.getInt("id"),
                    Rank.valueOf(rs.getString("rank")),
                    rs.getString("first_name"),
                    rs.getString("last_name"));

    @Autowired
    public JdbcOfficerDAO(JdbcTemplate template, SimpleJdbcInsert insertOfficer) {
        this.template = template;
        this.insertOfficer = new SimpleJdbcInsert(template)
                .withTableName("officers")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Officer save(Officer officer) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(officer);
        Number newId = insertOfficer.executeAndReturnKey(parameters);
        return new Officer(newId.intValue(),
                officer.rank(),
                officer.firstName(),
                officer.lastName());
    }

    @Override
    public Optional<Officer> findById(Integer id) {
        try (Stream<Officer> stream =
                     template.queryForStream(
                             "Select * from officers where id=?",
                             officerMapper,
                             id)) {
            return stream.findAny();
        }

    }

    @Override
    public List<Officer> findAll() {
        return template.query("Select * from officers", officerMapper);
    }

    @Override
    public long count() {
        return template.queryForObject("Select count(*) from officers", Long.class);
    }

    @Override
    public void delete(Officer officer) {
        template.update("Delete from officers where id=?", officer.id());
    }

    @Override
    public boolean existsById(Integer id) {
        return template.queryForObject(
                "Select exists(select 1 from officers where id=?)", Boolean.class, id);
    }
}
