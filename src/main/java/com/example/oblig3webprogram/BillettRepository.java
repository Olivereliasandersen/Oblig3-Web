package com.example.oblig3webprogram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BillettRepository {

    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(BillettRepository.class);

    class BillettRowMapper implements RowMapper< Billett > {
        @Override
        public Billett mapRow(ResultSet rs, int rowNum) throws SQLException {
            Billett billett = new Billett();
            billett.setId(rs.getLong("id"));
            billett.setFilm(rs.getString("film"));
            billett.setAntall(rs.getInt("antall"));
            billett.setFornavn(rs.getString("fornavn"));
            billett.setEtternavn(rs.getString("etternavn"));
            billett.setTelefon(rs.getString("telefon"));
            billett.setEpost(rs.getString("epost"));
            return billett;
        }

    }
    public void lagreBillett(Billett innBillett){
        String sql = "INSERT INTO billett (film, antall, fornavn, etternavn, telefon, epost) VALUES(?,?,?,?,?,?)";
        db.update(sql, innBillett.getFilm(), innBillett.getAntall(), innBillett.getFornavn(), innBillett.getEtternavn(),
                innBillett.getTelefon(), innBillett.getEpost());
    }
    public List<Billett> hentAlleBilletter(){
        String sql = "SELECT * FROM Billett ORDER BY etternavn";
        List<Billett> alleBilletter = db.query(sql, new BillettRowMapper());
        return alleBilletter;
    }

    public void slettAlleBilletter(){
        String sql = "DELETE FROM Billett";
        db.update(sql);
    }

    public boolean oppdaterBillett(Billett b){
        String sql = "UPDATE Billett SET film=?, antall=?, fornavn=?, etternavn=?, telefon=?, epost=?, id=?";
        try{
            db.update(sql, b.getFilm(),b.getAntall(),b.getFornavn(),b.getEtternavn(),b.getTelefon(),b.getEpost(),b.getId());
            return true;
        }
        catch(Exception e){
            logger.error("Feil i oppdater en billett"+e);
            return false;
        }
    }
}
