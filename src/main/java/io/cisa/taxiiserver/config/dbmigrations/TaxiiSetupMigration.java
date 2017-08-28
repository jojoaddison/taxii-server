package io.cisa.taxiiserver.config.dbmigrations;

import java.time.ZonedDateTime;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

import io.cisa.taxiiserver.config.Constants;
import io.cisa.taxiiserver.domain.ApiRoot;
import io.cisa.taxiiserver.domain.Collection;
import io.cisa.taxiiserver.domain.Discovery;
import io.cisa.taxiiserver.domain.Manifest;

@ChangeLog(order = "002")
public class TaxiiSetupMigration {

    @ChangeSet(order = "01", author = "02-initiator", id = "01-addApiRoot")
    public void addApiRoot(MongoTemplate mongoTemplate) {
    	
    	Manifest indicatorOne = new Manifest();
    	indicatorOne.setDateAdded(ZonedDateTime.now());
    	indicatorOne.setUrl("indicator-01");
    	indicatorOne.getMediaTypes().add(Constants.ACCEPT_STIX_HEADER);
    	indicatorOne.getVersions().add(1);
    	indicatorOne.getVersions().add(2);
    	indicatorOne.getVersions().add(4);
    	indicatorOne.getVersions().add(6); 
    	indicatorOne.setLastModified(ZonedDateTime.now());
    	
    	Manifest indicatorTwo = new Manifest();
    	indicatorTwo.setDateAdded(ZonedDateTime.now());
    	indicatorTwo.setUrl("indicator-02");
    	indicatorTwo.getMediaTypes().add(Constants.ACCEPT_STIX_HEADER);
    	indicatorTwo.getVersions().add(4);
    	indicatorTwo.setLastModified(ZonedDateTime.now());
    	
    	Collection sensor = new Collection();  
    	sensor.setId("sensor01");
    	sensor.setCanRead(true);
    	sensor.setCanWrite(true);
    	sensor.setDescription("CISA Automated Sensor Collection");
    	sensor.setDisplayName("CISA Sensor Collector");
    	sensor.setUrl("sensor");
    	sensor.getMediaTypes().add(Constants.ACCEPT_STIX_HEADER);
    	sensor.getManifest().add(indicatorOne);    	
    	mongoTemplate.save(sensor);
    	
    	Collection operation = new Collection();
    	operation.setId("operation01");
    	operation.setCanRead(true);
    	operation.setCanWrite(true);
    	operation.setDescription("CISA Automated Sensor Collection");
    	operation.setDisplayName("CISA Operation Collector");
    	operation.setUrl("operation");    	
    	operation.getMediaTypes().add(Constants.ACCEPT_STIX_HEADER);
    	operation.getManifest().add(indicatorTwo);
    	operation.getManifest().add(indicatorOne);
    	mongoTemplate.save(operation);

    	ApiRoot apiRoot = new ApiRoot();
    	apiRoot.setUrl("cisa");
    	apiRoot.setDisplayName("CISA TAXII Service v0.01");
    	apiRoot.setDescription("A Cyber Incidence Situation Awareness research working group setup conforming to TAXII v2.0.");
    	apiRoot.getVersions().add("taxii-2.0");
    	apiRoot.setLastModifiedDate(ZonedDateTime.now());
    	apiRoot.setMaxContentLength(50000000);
    	apiRoot.getCollections().add(sensor);
    	apiRoot.getCollections().add(operation);    	
    	mongoTemplate.save(apiRoot);
    	
    }


    @ChangeSet(order = "02", author = "02-initiator", id = "02-addDiscovery")
    public void addDiscovery(MongoTemplate mongoTemplate) {
    	Discovery taxii = new Discovery();
    	taxii.setDisplayName("CISA TAXII Server");
    	taxii.setDescription("CISA TAXII Server for sensor and operation data");
    	taxii.setContact("CISA Research working group.");
    	taxii.setCreatedDate(ZonedDateTime.now());
    	taxii.setLastModified(ZonedDateTime.now());
    	taxii.getApiRoots().add("cisa");
    	taxii.setDefaultURL("taxii");
    	mongoTemplate.save(taxii);   
    	
    	Discovery cisa = new Discovery();
    	cisa.setDisplayName("CISA TAXII Server");
    	cisa.setDescription("CISA TAXII Server for sensor and operation data");
    	cisa.setContact("CISA Research working group.");
    	cisa.setCreatedDate(ZonedDateTime.now());
    	cisa.setLastModified(ZonedDateTime.now());
    	cisa.setDefaultURL("cisa");
    	cisa.getApiRoots().add("cisa");
    	cisa.getApiRoots().add("taxii");
    	//mongoTemplate.save(cisa); // cisa is an api-root
    	
    }
}
