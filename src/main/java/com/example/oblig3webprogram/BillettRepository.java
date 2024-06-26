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
    //Lagrer billetten i databasen
    public void lagreBillett(Billett innBillett){
        String sql = "INSERT INTO billett (film, antall, fornavn, etternavn, telefon, epost) VALUES(?,?,?,?,?,?)";
        db.update(sql, innBillett.getFilm(), innBillett.getAntall(), innBillett.getFornavn(), innBillett.getEtternavn(),
                innBillett.getTelefon(), innBillett.getEpost());
    }
    //Viser billetten i databasen
    public List<Billett> hentAlleBilletter(){
        String sql = "SELECT * FROM Billett ORDER BY etternavn";
        List<Billett> alleBilletter = db.query(sql, new BillettRowMapper());
        return alleBilletter;
    }
//Sletter billetter i databasen
    public void slettAlleBilletter(){
        String sql = "DELETE FROM Billett";
        db.update(sql);
    }
//oppdaterer billetten i databasen
    public boolean oppdaterBillett(Billett b){
        String sql = "UPDATE Billett SET film=?, antall=?, fornavn=?, etternavn=?, telefon=?, epost=? WHERE id=?";
        try{
            db.update(sql, b.getFilm(),b.getAntall(),b.getFornavn(),b.getEtternavn(),b.getTelefon(),b.getEpost(),b.getId());
            return true;
        }
        catch(Exception e){
            logger.error("Feil i oppdater en billett"+e);
            return false;
        }
    }
//sletter 1 billett i databasen
    public void slettEnBillett(Billett b){
        String sql = "DELETE FROM Billett WHERE id=?";
        db.update(sql, b.getId());
    }
//endrer på 1 billett i databasen
    public Billett hentEnBillett(Billett b){
        System.out.println(b.getId());
        String sql = "SELECT * FROM Billett WHERE id=?";
        List<Billett> enBillett = db.query(sql, new BillettRowMapper(), b.getId());
        return enBillett.get(0);
    }
}
