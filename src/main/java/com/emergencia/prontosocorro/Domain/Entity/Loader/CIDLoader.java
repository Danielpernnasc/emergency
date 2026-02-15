package com.emergencia.prontosocorro.Domain.Entity.Loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;

@Component
public class CIDLoader implements CommandLineRunner {
	 private final RepositoryCID repositoryCID;


	public CIDLoader(RepositoryCID repositoryCID) {
		this.repositoryCID = repositoryCID;
	}
	 @Override
	public void run(String... args) throws IOException {
		System.out.println("🔥 Loader executando");

		if(repositoryCID.count() > 0) return;

		ClassPathResource resource = new ClassPathResource("CID10CSV/CID-10-CAPITULOS.csv");

		try (BufferedReader reader = new BufferedReader(
			new InputStreamReader(resource.getInputStream(), 
				StandardCharsets.ISO_8859_1))){

				reader.lines()
				  .skip(1)
				  .map(line -> line.split(","))
				  .forEach(data -> {
					if (data.length < 4) return;

					
					CID cid = new CID(data[0].trim(), data[3].trim(), null, null);
					cid.setCode(data[0].trim());
					cid.setDescription(data[3].trim());
					repositoryCID.save(cid);
				  });
			
	
				
			}

		
    }
}

