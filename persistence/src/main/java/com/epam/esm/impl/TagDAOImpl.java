package com.epam.esm.impl;

import com.epam.esm.data.Tag;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.TagDAO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.DAOConstants.TAG_TABLE;

@Repository
public class TagDAOImpl implements TagDAO {

    private RowMapper<Tag> rowMapper;
    private JdbcTemplate jdbcTemplate;

    public TagDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        setRowMapper();
    }

    @Override
    public Optional<Tag> findByName(String name) {

        String query = String.format("select * from `%s` where name=?", TAG_TABLE);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, rowMapper, name));
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll() {
        String query = String.format("select * from `%s`", TAG_TABLE);
        return new ArrayList<>(jdbcTemplate.query(query, rowMapper));
    }

    @Override
    public Long createEntity(Tag entity) throws DAOException {
        String query = "insert into " + TAG_TABLE + " (name) values(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            int i = 0;
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(++i, entity.getName());
            return ps;
        }, keyHolder);

        Optional<Number> optionalKey = Optional.ofNullable(keyHolder.getKey());
        long generatedId = optionalKey.orElseThrow(() -> {
            return new DAOException("ID hasn't generated in database");
        }).longValue();
        entity.setId(generatedId);
        return entity.getId();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        String query = String.format("select * from `%s` where id=?", TAG_TABLE);
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, rowMapper, id));
    }

    /**
     * not using
     */
    @Override
    public void updateCertificate(Tag entity) {
    }


    @Override
    public void deleteCertificate(Tag entity) throws DAOException {
        String query = String.format("delete from `%s` where id=?", TAG_TABLE);
        int rowsAffected = jdbcTemplate.update(query, entity.getId());
        if (rowsAffected == 0) {
            throw new DAOException(String.format("certificate with id=`%s` wasn't delete", entity.getId()));
        }
    }

    @Override
    public List<Tag> findAllCertificateTags(Long certificateId) {
        String query = "select a.* from tag a \n" +
                "                join gift_certificate_has_tag gt \n" +
                "                on gt.tag_id = a.id \n" +
                "                where gt.gift_certificate_id = ?";

        return new ArrayList<>(jdbcTemplate.query(query, rowMapper, certificateId));
    }

    private void setRowMapper() {

        rowMapper = (rs, rowNum) -> {
            int i = 0;
            return new Tag(
                    rs.getLong(++i),
                    rs.getString(++i));
        };
    }

}
