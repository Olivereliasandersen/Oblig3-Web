package com.example.oblig3webprogram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BillettLagring {
    @Autowired
    private BillettRepository rep;


    @PostMapping("/lagre")
    public void lagreBillett(Billett innBillett){
        rep.lagreBillett(innBillett);
    }

    @GetMapping("/hentAlle")
    public List<Billett> hentAlle(){
        return rep.hentAlleBilletter();
    }

    @GetMapping("/huskBillett")
    public Billett huskBillett(Billett billett){
        return rep.hentEnBillett(billett);
    }

    @DeleteMapping("/slettAlle")
    public void slettAlle(){
        rep.slettAlleBilletter();
    }

    @DeleteMapping("/slettEnBillett")
    public void slettEnBillett(Billett billett){
        rep.slettEnBillett(billett);
    }

    @PostMapping("/oppdater")
    public void oppdater(Billett billett){
        rep.oppdaterBillett(billett);
    }
}

