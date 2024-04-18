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


    @PostMapping("/slettAlle")
    public void slettAlle(){
        rep.slettAlleBilletter();
    }

    @PostMapping("/oppdater")
    public void oppdater(Billett billett){

    }

}

